package observer;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class Email implements Observer<Long> {
    private final String username = "username";
    private final String password = "password";
    private final String recipient;
    private Long lastModified;
    private boolean firstRun = true;
    private Disposable disposable;

    public Email(String recipient) {
        this.recipient = recipient;
    }

    public void sendEmail() {
        Date date = new Date(lastModified);
        String subject = "Change Detected";
        String text = "Date: " + date.toString();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Address from = new InternetAddress(username);
            Address to = new InternetAddress(recipient);
            Message message = new MimeMessage(session);
            message.setFrom(from);
            message.setRecipient(Message.RecipientType.TO, to);
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
            System.out.println("Email was sent.");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        this.disposable = disposable;
    }

    @Override
    public void onNext(Long lastModified) {
        if (firstRun) {
            this.lastModified = lastModified;
            this.firstRun = false;
        } else if (this.lastModified < lastModified) {
            this.lastModified = lastModified;
            sendEmail();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        this.disposable.dispose();
    }


    @Override
    public int hashCode() {
        return recipient.hashCode();
    }
}