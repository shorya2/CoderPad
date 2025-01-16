package com.example.bakcend_test.controller;

import com.example.bakcend_test.model.OtpRequest;
import com.example.bakcend_test.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/otp")
public class OTPController {

    @Autowired
    private OTPService otpService;

    // Endpoint to verify the OTP entered by the user
    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String, String>> verifyOtp(@RequestBody OtpRequest otpRequest) {
        String email =otpRequest.getEmail();
        String otp = otpRequest.getOtp();
        System.out.println(email + otp);
        boolean isValid = otpService.validateOTP(email, otp);
        if (isValid) {
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("message", "OTP verified successfully.");
            return ResponseEntity.ok(successResponse);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Invalid OTP!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
