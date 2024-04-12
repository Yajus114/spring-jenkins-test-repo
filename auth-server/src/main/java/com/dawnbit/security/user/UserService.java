package com.dawnbit.security.user;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public interface UserService {
    UserDTO getLoggedInUser(Principal principal, HttpServletRequest request);

    void saveUserLoginHistory(String ip);

}
