package app.commands;

import app.Email;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "send-email", description = "Send an email")
public class SendEmailCommand implements Runnable {

	@Option(names = { "-t", "--to" }, required = true, description = "Recipient email address")
	private String to;

	@Option(names = { "-s", "--subject" }, required = true, description = "Email subject")
	private String subject;

	@Option(names = { "-m", "--message" }, required = true, description = "Email message")
	private String message;

	public void run() {
		Email email = new Email();
		email.sendEmail(to, subject, message);
	}
}
