package com.example.bakcend_test.controller;
import com.example.bakcend_test.service.EmailSenderService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dpi")
public class EmailSenderController {

    @Autowired
    private EmailSenderService emailSenderService;

    @GetMapping("/send-email/{userEmail}")
    public String sendTestEmailToUser(@PathVariable String userEmail) {
        try {
            // Example subject and body
            System.out.println(userEmail);
            String subject = "Test Assignment: Sample Test";
            String body = "Hello, you have been assigned a test.\n\nPlease click the following link to access the test:\n[Link to Test]";

            // Send email to the provided email address
            emailSenderService.sendEmail(userEmail, subject, body);
            return "Email sent successfully to " + userEmail;
        } catch (MessagingException e) {
            return "Error sending email: " + e.getMessage();
        }
    }
}
