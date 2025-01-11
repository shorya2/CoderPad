package com.example.bakcend_test.controller;

import com.example.bakcend_test.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/otp")
public class OTPController {

    @Autowired
    private OTPService otpService;

    // Endpoint to generate and send OTP to user's email
    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam String email) {
        try {
            String otp = otpService.generateAndSendOTP(email);
            return "OTP sent to " + email + ". Please check your inbox.";
        } catch (Exception e) {
            return "Error sending OTP: " + e.getMessage();
        }
    }

    // Endpoint to verify the OTP entered by the user
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String email, @RequestParam String otp) {
        boolean isValid = otpService.validateOTP(email, otp);
        if (isValid) {
            return "OTP is valid. Authentication successful.";
        } else {
            return "Invalid or expired OTP.";
        }
    }
}
