package com.dawnbit.security.controller;

import com.dawnbit.security.repository.UserLoginHistoryRepository;
import com.dawnbit.security.user.UserDTO;
import com.dawnbit.security.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserLoginHistoryRepository userLoginHistoryRepository;

    /**
     * @param principal
     * @param request
     * @return
     * @description to get logged-in user details
     */
    @RequestMapping(value = {"/getLoggedInUser"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<UserDTO> user(Principal principal, HttpServletRequest request) {
        UserDTO user = userService.getLoggedInUser(principal, request);
        saveUserIP(request);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    private void saveUserIP(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-FORWARDED-FOR");
        if (remoteAddr == null || remoteAddr.isEmpty()) {
            remoteAddr = request.getRemoteAddr();
        }
        remoteAddr = remoteAddr.split(",")[0];
        if (userLoginHistoryRepository.existsByIpWithinLast10Seconds(remoteAddr) != 1L) {
            userService.saveUserLoginHistory(remoteAddr);
        }
    }
}
