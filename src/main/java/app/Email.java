package app;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {

	public void sendEmail(String to, String subject, String message) {

		final String from = "cleaning444service@gmail.com";
		String host = "smtp.gmail.com";
		final String password = "coijcknknwnjmnkc";

		// Set properties
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		// Get session object
		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});

		try {
			MimeMessage message1 = new MimeMessage(session);

			message1.setFrom(new InternetAddress(from));
			message1.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message1.setSubject(subject);
			message1.setText(message);

			Transport.send(message1);
			System.out.println("Email Sent successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

}
