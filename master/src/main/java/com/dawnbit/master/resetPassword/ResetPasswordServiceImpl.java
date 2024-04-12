package com.dawnbit.master.resetPassword;
//

import com.dawnbit.common.exception.CustomException;
import com.dawnbit.common.utils.ConstantUtils;
import com.dawnbit.common.utils.Crypt;
import com.dawnbit.entity.master.User;
import com.dawnbit.entity.master.UserDetails;
import com.dawnbit.master.Mail.EmailService;
import com.dawnbit.master.userdetails.UserDetailsRepo;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class ResetPasswordServiceImpl implements ResetPasswordService {

    @Autowired
    UserDetailsRepo userDetailsRepo;
    @Autowired
    ResetPasswordRepository resetPasswordRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    PasswordEncoder passwordEncoder;

    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void sendotp(User user) {
        final String keys = ConstantUtils.EMAIL_APPLICATION_ID + "," + ConstantUtils.EMAIL_USER_ID + ","
                + ConstantUtils.EMAIL_APPLICATION_AUTHORIZATION;

        final long id = user.getUserId();

        String emailId = "";
        String userName = "";
        final User user1 = this.resetPasswordRepository.getUserDetails(id);
        final UserDetails userDetail = this.userDetailsRepo.findByIds(id);

        emailId = userDetail.getEmailId();
        userName = userDetail.getUserName();

        // Construct email content
        String subject = "Forgot Password - OTP";
        String emailContent = "Dear " + userName + ",\n\n"
                + "You have requested to reset your password. Please use the following OTP to proceed:\n\n"
                + "OTP: " + user.getRecoverPasswordOtp() + "\n\n"
                + "This OTP is valid for 10 minutes. If you did not request this, please ignore this email.\n\n"
                + "Regards,\n"
                + "Dawnbit ";

        // Send email
        if (emailId != null && !emailId.isBlank()) {
            emailService.sendEmail(emailId, subject, emailContent);
        }
    }


    @Override
    public Boolean checkIfUserNameExists(String name) {
        final Long count = this.resetPasswordRepository.findExistingName(name);
        return ((count != null) && (count > 0));
    }

    @Override
    public boolean generateOtp(String name) throws CustomException {
        final User user = this.resetPasswordRepository.getUserByName(name);
        System.out.println("Username is : " + user);
        if (user != null) {
            user.setRecoverPasswordOtp(RandomStringUtils.randomNumeric(4));
            final Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.MINUTE, 10);
            user.setOtpExpiryDatetime(cal.getTime());
            this.resetPasswordRepository.save(user);
            this.sendotp(user);
//
            return true;
        } else {
            throw new CustomException("No user found!");
        }
    }


    @Override
    public boolean checkOtp(String name, String otp) throws CustomException {
        final User user = this.resetPasswordRepository.getUserByName(name);
        if (user != null) {
            final String otpFromDB = user.getRecoverPasswordOtp();
            if (otpFromDB.equals(otp)) {
                final Date otpExpiryTime = user.getOtpExpiryDatetime();
                final Calendar currentDateTime = Calendar.getInstance();
                currentDateTime.setTime(new Date());
                final Calendar otpExpiryDateTime = Calendar.getInstance();
                otpExpiryDateTime.setTime(otpExpiryTime);
                if (currentDateTime.getTime().compareTo(otpExpiryDateTime.getTime()) > 0) {
                    throw new CustomException("OTP expired.");
                } else {
                    return true;
                }
            } else {
                throw new CustomException("OTP didn't matched. Please try again.");
            }
        } else {
            throw new CustomException("No user found!");
        }
    }

    @Override
    public boolean resetPassword(String name, String otp, String newPassword) throws CustomException {
        final User user = this.resetPasswordRepository.getUserByName(name);
        if (user != null) {
            final Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            final Calendar cal1 = Calendar.getInstance();
            cal1.setTime(user.getOtpExpiryDatetime());
            if (cal1.getTime().compareTo(cal.getTime()) < 0) {
                throw new CustomException("Entered OTP is expired.");
            }
            if (!user.getRecoverPasswordOtp().equalsIgnoreCase(otp)) {
                throw new CustomException("Entered OTP is wrong.");
            }
            String pwd = Crypt.decrypt(newPassword);
            final Boolean match = this.getPasswordEncoder().matches(pwd, user.getPassword());
            if (match) {
                throw new CustomException("Password already used before. Please Try again.");
            } else {
                user.setRecoverPasswordOtp(null);
                user.setOtpExpiryDatetime(null);
                user.setPassword(this.getPasswordEncoder().encode(pwd));
                this.resetPasswordRepository.save(user);
//                this.userRepository.save(user);
                this.resetPassword(user);
                return true;
            }
        } else {
            throw new CustomException("No user found!");
        }
    }

    @Override
    public void resetPassword(User user1)
            throws CustomException {

    }

    @Override
    public boolean changePassword(String name, String old, String current) throws CustomException {
        final User user = this.resetPasswordRepository.getUserByName(name);
        if (user != null) {
            final Boolean match = this.passwordEncoder.matches(old, user.getPassword());
            if (match) {
                final Boolean match1 = this.passwordEncoder.matches(current, user.getPassword());
                if (match1) {
                    throw new CustomException("New password entered already exists.");
                } else {
                    user.setPassword(this.passwordEncoder.encode(current));
                    // Set flag indicating it's not the first time the user logged in
                    user.setNotFirstTimeLoggedIn(true);
                    this.resetPasswordRepository.save(user);

                    // Send email notification about password change
                    sendPasswordChangedEmail(user);

                    return true;
                }
            } else {
                throw new CustomException("The current password entered does not match.");
            }
        }
        throw new CustomException("User does not exist.");
    }

    @Override
    public boolean changePassword1(Long userid, String current) throws CustomException {
        final User user = this.resetPasswordRepository.getUserDetails(userid);
        user.setPassword(this.passwordEncoder.encode(current));
        user.setNotFirstTimeLoggedIn(true);
        this.resetPasswordRepository.save(user);
//                    sendPasswordChangedEmail(user);
        return true;
    }

    private void sendPasswordChangedEmail(User user) {
        final long id = user.getUserId();
        final UserDetails userDetail = this.userDetailsRepo.findByIds(id);
        String subject = "Password Changed Successfully";
        String emailContent = "Dear " + user.getUsername() + ",\n\n"
                + "Your password has been changed successfully.\n\n"
                + "If you did not initiate this change, please contact us immediately.\n\n"
                + "Regards,\n"
                + "Dawnbit";

        // Send email
        emailService.sendEmail(userDetail.getEmailId(), subject, emailContent);
    }

}
