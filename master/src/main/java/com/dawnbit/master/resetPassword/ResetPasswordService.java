package com.dawnbit.master.resetPassword;

import com.dawnbit.common.exception.CustomException;
import com.dawnbit.entity.master.User;

public interface ResetPasswordService {
    Boolean checkIfUserNameExists(String name);

    boolean generateOtp(String name) throws CustomException;

    boolean checkOtp(String name, String otp) throws CustomException;

    boolean resetPassword(String name, String otp, String newPassword) throws CustomException;

    void resetPassword(User user1) throws CustomException;

    boolean changePassword(String user, String s, String s1) throws CustomException;

    boolean changePassword1(Long userid, String s) throws CustomException;
}
