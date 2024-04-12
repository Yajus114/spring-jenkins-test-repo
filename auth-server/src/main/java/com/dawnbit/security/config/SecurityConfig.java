package com.dawnbit.security.config;

import com.dawnbit.security.config.jwtConfig.JwtTokenUtils;
import com.dawnbit.security.filter.JwtAccessTokenFilter;
import com.dawnbit.security.filter.JwtRefreshTokenFilter;
import com.dawnbit.security.repository.RefreshTokenRepo;
import com.dawnbit.security.service.LogoutHandlerService;
import com.dawnbit.security.service.UserInfoService;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class SecurityConfig {


    private final UserInfoService userInfoService;

    private final RSAKeyRecord rsaKeyRecord;

    private final JwtTokenUtils jwtTokenUtils;

    private final RefreshTokenRepo refreshTokenRepo;

    private final LogoutHandlerService logoutHandlerService;

    @Autowired
    public SecurityConfig(@Lazy UserInfoService userInfoService,
                          RSAKeyRecord rsaKeyRecord,
                          JwtTokenUtils jwtTokenUtils,
                          RefreshTokenRepo refreshTokenRepo,
                          LogoutHandlerService logoutHandlerService) {
        this.userInfoService = userInfoService;
        this.rsaKeyRecord = rsaKeyRecord;
        this.jwtTokenUtils = jwtTokenUtils;
        this.refreshTokenRepo = refreshTokenRepo;
        this.logoutHandlerService = logoutHandlerService;
    }

    /**
     * A SecurityFilterChain for API requests.
     *
     * @param httpSecurity the HttpSecurity object
     * @return the SecurityFilterChain for API requests
     */

    @Order(1)
    @Bean
    public SecurityFilterChain logInSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityMatcher(new AntPathRequestMatcher("/auth/login/**"))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .userDetailsService(userInfoService)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> {
                    ex.authenticationEntryPoint((request, response, authException) ->
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage()));
                })
                .httpBasic(withDefaults())
                .build();
    }

    /**
     * A SecurityFilterChain for API requests.
     *
     * @param httpSecurity the HttpSecurity object
     * @return the SecurityFilterChain for API requests
     */
    @Order(2)
    @Bean
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityMatcher(new AntPathRequestMatcher("/api/**"))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtAccessTokenFilter(rsaKeyRecord, jwtTokenUtils), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> {
                    log.error("[SecurityConfig:apiSecurityFilterChain] Exception due to :{}", ex);
                    ex.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint());
                    ex.accessDeniedHandler(new BearerTokenAccessDeniedHandler());
                })
                .httpBasic(withDefaults())
                .build();
    }

    /**
     * Configures a security filter chain for handling refresh token requests.
     *
     * @param httpSecurity the HttpSecurity object for configuring security
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Order(3)
    @Bean
    public SecurityFilterChain refreshTokenSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityMatcher(new AntPathRequestMatcher("/auth/refreshToken/**"))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtRefreshTokenFilter(rsaKeyRecord, jwtTokenUtils, refreshTokenRepo), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> {
                    log.error("[SecurityConfig:refreshTokenSecurityFilterChain] Exception due to :{}", ex);
                    ex.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint());
                    ex.accessDeniedHandler(new BearerTokenAccessDeniedHandler());
                })
                .httpBasic(withDefaults())
                .build();
    }

    /**
     * Configures the security filter chain for logout functionality.
     *
     * @param httpSecurity the HttpSecurity object
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs while configuring the security filter chain
     */
    @Order(4)
    @Bean
    public SecurityFilterChain logoutSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityMatcher(new AntPathRequestMatcher("/logout/**")) // match the "/logout/**" pattern
                .csrf(AbstractHttpConfigurer::disable) // disable CSRF protection
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated()) // authorize any request that is authenticated
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults())) // configure OAuth2 resource server with default JWT configuration
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // set session creation policy to stateless
                .addFilterBefore(new JwtAccessTokenFilter(rsaKeyRecord, jwtTokenUtils), UsernamePasswordAuthenticationFilter.class) // add JwtAccessTokenFilter before UsernamePasswordAuthenticationFilter
                .logout(logout -> logout
                        .logoutUrl("/logout") // set the logout URL to "/logout"
                        .addLogoutHandler(logoutHandlerService) // add the specified logout handler
                        .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext())) // clear the security context upon successful logout
                )
                .exceptionHandling(ex -> {
                    log.error("[SecurityConfig:logoutSecurityFilterChain] Exception due to :{}", ex); // log the exception
                    ex.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint()); // set the authentication entry point for bearer token
                    ex.accessDeniedHandler(new BearerTokenAccessDeniedHandler()); // set the access denied handler for bearer token
                })
                .build(); // build and return the configured SecurityFilterChain
    }
    /*

    for sign-up purpose But we are not using sign-up in this application
                @Order(5)
                @Bean
                public SecurityFilterChain registerSecurityFilterChain(HttpSecurity httpSecurity) throws Exception{
                    return httpSecurity
                            .securityMatcher(new AntPathRequestMatcher("/sign-up/**"))
                            .csrf(AbstractHttpConfigurer::disable)
                            .authorizeHttpRequests(auth ->
                                    auth.anyRequest().permitAll())
                            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                            .build();
                }


             */

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeyRecord.rsaPublicKey()).build();
    }

    /**
     * Bean for creating a JWT encoder using RSA algorithm
     *
     * @return JwtEncoder instance
     */
    @Bean
    JwtEncoder jwtEncoder() {
        // Create an RSA key using the public and private keys from rsaKeyRecord
        JWK jwk = new RSAKey.Builder(rsaKeyRecord.rsaPublicKey()).privateKey(rsaKeyRecord.rsaPrivateKey()).build();
        // Create a JWK source using the JWK set
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        // Return a new NimbusJwtEncoder using the JWK source
        return new NimbusJwtEncoder(jwkSource);
    }

}
