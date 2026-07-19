package com.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    @Async
    public void sendApprovalMail(String customerFirstName, String userName, String tempPassword, String recipientEmail, String ccEmail) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("ali.java00@gmail.com");
        mail.setTo(recipientEmail);
        if (ccEmail != null) {
            mail.setCc(ccEmail);
        }
        mail.setSubject("Account Approval - Canara Bank");

        mail.setText(
                "Hi " + customerFirstName + ",\n\n" +
                        "Congratulations! Your account has been successfully created.\n\n" +
                        "Please refer to the details below to login:\n" +
                        "User ID: " + userName + "\n" +
                        "Temporary Password: " + tempPassword + "\n\n" +
                        "For security reasons, please change your password after your first login.\n\n" +
                        "Regards,\n" +
                        "Canara Bank Team"
        );
        try {
            javaMailSender.send(mail);
            System.out.println("Mail sent successfully!");
        } catch (Exception e) {
            System.err.println("Error while sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

