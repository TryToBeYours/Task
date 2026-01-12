package org.example.service;

import org.springframework.stereotype.Service;

@Service
public class EmailServices {

    public void sendEmail(String to, String subject, String body) {
        System.out.println("📧 EMAIL SENT");
        System.out.println("To: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
    }
}
