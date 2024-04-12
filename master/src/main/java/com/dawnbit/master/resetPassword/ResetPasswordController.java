package com.dawnbit.master.resetPassword;

import com.dawnbit.common.exception.CustomException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/reset")
@Slf4j
public class ResetPasswordController {
    @Autowired
    ResetPasswordService resetPasswordService;

    @GetMapping("/checkIfUserNameExists")
    public ResponseEntity<Map<String, Object>> checkIfUserNameExists(@RequestParam final String name) {
        final Map<String, Object> map = new HashMap<>();
        boolean exists = this.resetPasswordService.checkIfUserNameExists(name);
        map.put("exists", exists);
        System.out.println("-------------User exist------------------ " + map + "___________________________________");
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @PutMapping("/generateOtp")
    public ResponseEntity<Map<String, Object>> generateOtp(@RequestParam final String name, final HttpServletResponse response) throws CustomException {
//        log.info("# generateOtp");
        final Map<String, Object> map = new HashMap<>();
        map.put("ForgetPassword", this.resetPasswordService.generateOtp(name));
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @GetMapping("/checkOtp")
    public ResponseEntity<Map<String, Object>> checkOtp(@RequestParam final String name, @RequestParam final String otp,
                                                        final HttpServletResponse response) throws CustomException {
        final Map<String, Object> map = new HashMap<>();
        map.put("FORGET", this.resetPasswordService.checkOtp(name, otp));
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @PutMapping("/resetPassword")
    public ResponseEntity<Map<String, Object>> resetPassword(@RequestParam final String name, @RequestParam final String otp,
                                                             @RequestParam final String newPassword, final HttpServletResponse response) throws CustomException {
//        log.info("# resetPassword");
        final Map<String, Object> map = new HashMap<>();
        map.put("FORGETP", this.resetPasswordService.resetPassword(name, otp, newPassword));
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<Map<String, Object>> changePassword(@RequestParam final String user,
                                                              @RequestParam final String old, @RequestParam final String current) throws CustomException {
        final Date startDate = new Date();
        final Map<String, Object> map = new HashMap<>();
        map.put("USER", this.resetPasswordService.changePassword(user, new String(Base64.getDecoder().decode(old)),
                new String(Base64.getDecoder().decode(current))));
        if (log.isInfoEnabled()) {
//            log.info("Rest url /changePassword from : ( " + startDate + to + new Date() + ")");
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("/changePasswordByAdmin")
    public ResponseEntity<Map<String, Object>> changePasswordByAdmin(@RequestParam final Long userid,
                                                                     @RequestParam final String current) throws CustomException {
        final Date startDate = new Date();
        final Map<String, Object> map = new HashMap<>();
        map.put("USER", this.resetPasswordService.changePassword1(userid, new String(Base64.getDecoder().decode(current))));
        if (log.isInfoEnabled()) {
//            log.info("Rest url /changePassword from : ( " + startDate + to + new Date() + ")");
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
