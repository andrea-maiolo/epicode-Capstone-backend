package andreamaiolo.backend.configurations;

import andreamaiolo.backend.entities.Booking;
import andreamaiolo.backend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class GmailMailSender {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public void sendRegistrationEmail(User recipient) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(senderEmail);
            message.setTo(recipient.getEmail());
            message.setSubject("Registration successfull!");
            message.setText("Thank you " + recipient.getName() + " for registering");
            javaMailSender.send(message);
        } catch (Exception ex) {
            System.out.println("error sending email");
            ex.printStackTrace();
            throw new RuntimeException("Email sending failed", ex);
        }
    }

    public void sendBookingConfirmation(User recipient, Booking bookingInfo) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(senderEmail);
            message.setTo(recipient.getEmail());
            message.setSubject("Booking confirmation !");
            message.setText("Thanks for booking with us. Booking info: "
                    + "check in " + bookingInfo.getCheckin() + " check out " + bookingInfo.getCheckout()
                    + " number of guests " + bookingInfo.getGuests() + " room description " + bookingInfo.getRoom().getDescription());
            javaMailSender.send(message);
        } catch (Exception ex) {
            System.out.println("error in booking confirmation");
            ex.printStackTrace();
            throw new RuntimeException("booking confirmation failed", ex);
        }
    }
}
