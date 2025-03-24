package elearning.project.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import elearning.project.models.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl {

    @Autowired 
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") 
    private String sender;

    public String sendMailWithAttachment(User user) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
               try {
            MimeMessageHelper mimeMessageHelper  = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(user.getEmail());

            // Logging the email details
            System.out.println("Sending email to: " + user.getEmail());
            System.out.println("From: " + sender);

            // Hardcoded message body and subject
            String msgBody = "Dear " + user.getUsername() + ",\n\n" +
                    "Congratulations! You have successfully registered Team C on our e-learning platform. " +
                    "We are excited to have you on board and look forward to supporting your learning journey.\n\n" +
                    "Best regards,\n" +
                    "The E-Learning Team";
   String subject = "Welcome to the E-Learning Platform";
            mimeMessageHelper.setText(msgBody);
            mimeMessageHelper.setSubject(subject);

            // Logging the message body and subject
            System.out.println("Message Body: " + msgBody);
            System.out.println("Subject: " + subject);

            // Sending the mail
            javaMailSender.send(mimeMessage);
            System.out.println("Mail sent successfully");
            return "Mail sent Successfully";
        } catch (MessagingException e) {
            e.printStackTrace(); // Log the exception
            System.out.println("Error while sending mail: " + e.getMessage());
            return "Error while sending mail!!!";
        }
    }
}