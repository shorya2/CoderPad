package com.example.bakcend_test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OTPService {

    private static final int OTP_EXPIRATION_TIME = 5 * 60 * 1000; // 5 minutes expiration time
    private static final ConcurrentHashMap<String, OTPDetails> otpStore = new ConcurrentHashMap<>();

    @Autowired
    private EmailSenderService emailSenderService;

    // Generate OTP and send it via email
    public String generateAndSendOTP(String email) {
        String otp = generateOTP();
        try {
            emailSenderService.sendOtpEmail(email, otp);
        } catch (Exception e) {
            System.out.println("Error sending OTP email: " + e.getMessage());
        }

        // Store OTP with expiration time
        otpStore.put(email, new OTPDetails(otp, System.currentTimeMillis()));
        return otp;
    }

    // Validate OTP
    public boolean validateOTP(String email, String otp) {
        OTPDetails otpDetails = otpStore.get(email);

        // Check if OTP is present and not expired
        if (otpDetails != null && otpDetails.getOtp().equals(otp)) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - otpDetails.getTimestamp() < OTP_EXPIRATION_TIME) {
                return true;
            } else {
                otpStore.remove(email); // Expired OTP, remove it
            }
        }

        return false;
    }

    // Generate a 6-digit OTP
    private String generateOTP() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000)); // Generate 6-digit OTP
    }

    // OTPDetails class to store OTP and timestamp
    private static class OTPDetails {
        private String otp;
        private long timestamp;

        public OTPDetails(String otp, long timestamp) {
            this.otp = otp;
            this.timestamp = timestamp;
        }

        public String getOtp() {
            return otp;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }
}
