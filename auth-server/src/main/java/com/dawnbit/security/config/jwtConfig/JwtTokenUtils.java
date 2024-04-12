package com.dawnbit.security.config.jwtConfig;

import com.dawnbit.security.repository.UserRepository;
import com.dawnbit.security.service.UserInfoDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtTokenUtils {

    private final UserRepository userRepository;

    public String getUserName(Jwt jwtToken) {
        return jwtToken.getSubject();
    }

    /**
     * Checks if the provided JWT token is valid for the given user details.
     *
     * @param jwtToken    the JWT token to be validated
     * @param userDetails the user details to validate the token against
     * @return true if the token is valid for the user, false otherwise
     */
    public boolean isTokenValid(Jwt jwtToken, UserDetails userDetails) {
        final String userName = getUserName(jwtToken);
        boolean isTokenExpired = getIfTokenIsExpired(jwtToken);
        boolean isTokenUserSameAsDatabase = userName.equals(userDetails.getUsername());
        return !isTokenExpired && isTokenUserSameAsDatabase;

    }

    /**
     * Checks if the given Jwt token is expired.
     *
     * @param jwtToken the Jwt token to check for expiration
     * @return true if the Jwt token is expired, false otherwise
     */
    private boolean getIfTokenIsExpired(Jwt jwtToken) {
        return Objects.requireNonNull(jwtToken.getExpiresAt()).isBefore(Instant.now());
    }

    /**
     * Retrieves user details by name.
     *
     * @param name the name of the user
     * @return the user details if found, otherwise throws an exception
     */
    public UserDetails userDetails(String name) {
        return userRepository
                .findByName(name)
                .map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User: " + name + " does not exist"));
    }
}
