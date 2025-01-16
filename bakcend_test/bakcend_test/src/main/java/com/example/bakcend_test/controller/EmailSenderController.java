package com.example.bakcend_test.controller;
import com.example.bakcend_test.service.EmailSenderService;
import com.example.bakcend_test.service.OTPService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EmailSenderController {

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private OTPService otpService;

    @GetMapping("/send-email")
    public String sendTestEmailToUser(@RequestParam String userEmail) {
        try {
            // Example subject and body
            String otp = otpService.generateAndSendOTP(userEmail);
            System.out.println(userEmail);
            String subject = "Test Assignment: Sample Test";
            String body = "Hello, you have been assigned a test.\n\nPlease click the following link to access the test:\n[Link to Test]" + "\nYour OTP is: "+ otp;

            // Send email to the provided email address
            emailSenderService.sendEmail(userEmail, subject, body);
            return "Email sent successfully to " + userEmail;
        } catch (MessagingException e) {
            return "Error sending email: " + e.getMessage();
        }
    }
}
