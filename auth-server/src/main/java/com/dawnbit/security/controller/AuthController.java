package com.dawnbit.security.controller;

import com.dawnbit.security.service.AuthService;
import com.dawnbit.security.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    /**
     * authenticateUser method to authenticate the user.
     *
     * @param authentication the authentication object
     * @param response       the HTTP servlet response
     * @return ResponseEntity with JWT tokens after authentication
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(Authentication authentication, HttpServletResponse response) {

//        boolean result =this.userService.checkFirstTimeLoggedInUser(authentication);

        return ResponseEntity.ok(authService.getJwtTokensAfterAuthentication(authentication, response));
    }

    /**
     * Get access token using refresh token.
     *
     * @param authorizationHeader the authorization header
     * @return the response entity with access token and refresh token
     */
    @PostMapping("/refreshToken")
    public ResponseEntity<?> getAccessToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        return ResponseEntity.ok(authService.getAccessTokenUsingRefreshToken(authorizationHeader));
    }


}