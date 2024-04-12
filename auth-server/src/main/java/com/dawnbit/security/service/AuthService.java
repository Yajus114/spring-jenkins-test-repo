package com.dawnbit.security.service;

import com.dawnbit.entity.master.RefreshToken;
import com.dawnbit.entity.master.User;
import com.dawnbit.security.config.jwtConfig.JwtTokenGenerator;
import com.dawnbit.security.dto.AuthResponseDto;
import com.dawnbit.security.dto.TokenType;
import com.dawnbit.security.repository.RefreshTokenRepo;
import com.dawnbit.security.repository.UserRepository;
import com.dawnbit.security.user.UserDetailsRepo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userInfoRepo;

    private final RefreshTokenRepo refreshTokenRepo;
    @Lazy
    private final JwtTokenGenerator jwtTokenGenerator;

    private final UserDetailsRepo userDetailsRepo;

    /**
     * Create an authentication object using the provided user details.
     *
     * @param user the user object containing the user details
     * @return the authentication object created from the user details
     */
    private static Authentication createAuthenticationObject(User user) {
        // Extract user details from UserDetailsEntity
        String username = user.getName();
        String password = user.getPassword();
        String roles = user.getRoles();

        // Extract authorities from roles (comma-separated)
        String[] roleArray = roles.split(",");
        GrantedAuthority[] authorities = Arrays.stream(roleArray)
                .map(role -> (GrantedAuthority) role::trim)
                .toArray(GrantedAuthority[]::new);

        return new UsernamePasswordAuthenticationToken(username, password, Arrays.asList(authorities));
    }

    /**
     * Authenticates the user and generates JWT tokens.
     *
     * @param authentication the user's authentication details
     * @param response       the HTTP response
     * @return the authentication response DTO containing access and refresh tokens
     * @author DB-CPU009 - Ravi Kumar
     */
    public AuthResponseDto getJwtTokensAfterAuthentication(Authentication authentication,
                                                           HttpServletResponse response) {
        try {
            var userEntity = userInfoRepo.findByName(authentication.getName())
                    .orElseThrow(() -> {
                        log.error("[AuthService:userSignInAuth] User :{} not found", authentication.getName());
                        return new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND ");
                    });
            var userDetails = userDetailsRepo.findById(userEntity.getUserId()).orElse(null);
            if (userDetails != null) {
                var userOrgStatus = userDetails.getOrganisation().getStatus();
                if (userDetails.getStatus().equalsIgnoreCase("INACTIVE") ||
                        userOrgStatus.equalsIgnoreCase("INACTIVE")) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is Inactive");
                }
            }

            String accessToken = jwtTokenGenerator.generateAccessToken(authentication);
            String refreshToken = jwtTokenGenerator.generateRefreshToken(authentication);

            //Let's save the refreshToken as well
            saveUserRefreshToken(userEntity, refreshToken);

            creatRefreshTokenCookie(response, refreshToken);

            log.info("[AuthService:userSignInAuth] Access token for user:{}, has been generated", userEntity.getUsername());
            return AuthResponseDto.builder()
                    .accessToken(accessToken)
                    .accessTokenExpiry(60 * 60)
                    .userName(userEntity.getUsername())
                    .tokenType(TokenType.Bearer)
                    .refreshToken(refreshToken)
                    .build();


        } catch (Exception e) {
            log.error("[AuthService:userSignInAuth]Exception while authenticating the user due to :" + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please Try Again");
        }
    }

    private void saveUserRefreshToken(User user, String refreshToken) {
        var refreshTokenEntity = RefreshToken.builder()
                .user(user)
                .refreshToken(refreshToken)
                .revoked(false)
                .build();
        refreshTokenRepo.save(refreshTokenEntity);
    }

    private Cookie creatRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge(15 * 24 * 60 * 60); // in seconds
        response.addCookie(refreshTokenCookie);
        return refreshTokenCookie;
    }

    /**
     * Retrieves an access token using a refresh token from the provided authorization header.
     *
     * @param authorizationHeader the authorization header containing the refresh token
     * @return an AuthResponseDto containing the new access token, its expiry, user name, and token type
     */

    public Object getAccessTokenUsingRefreshToken(String authorizationHeader) {

        if (!authorizationHeader.startsWith(TokenType.Bearer.name())) {
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please verify your token type");
        }

        final String refreshToken = authorizationHeader.substring(7);

        //Find refreshToken from database and should not be revoked : Same thing can be done through filter.
        var refreshTokenEntity = refreshTokenRepo.findByRefreshToken(refreshToken)
                .filter(tokens -> !tokens.isRevoked())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Refresh token revoked"));

        User user = refreshTokenEntity.getUser();

        //Now create the Authentication object
        Authentication authentication = createAuthenticationObject(user);

        //Use the authentication object to generate new accessToken as the Authentication object that we will have may not contain correct role.
        String accessToken = jwtTokenGenerator.generateAccessToken(authentication);

        return AuthResponseDto.builder()
                .accessToken(accessToken)
                .accessTokenExpiry(30 * 60)
                .userName(user.getName())
                .tokenType(TokenType.Bearer)
                .build();
    }
}
