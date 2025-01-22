package com.example.bakcend_test.service;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);  // Setting the body as HTML (set to false for plain text)

        mailSender.send(message);
        System.out.println("Email sent successfully!");
    }

    // Method to send OTP email
    public void sendOtpEmail(String to, String otp) throws MessagingException {
        String subject = "Your OTP for Authentication";
        String body = "Your OTP is: <b>" + otp + "</b>. It is valid for 5 minutes.";

        sendEmail(to, subject, body);
    }
}
