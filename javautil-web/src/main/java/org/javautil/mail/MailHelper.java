package org.javautil.mail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Easily send email.
 * 
 * @author bcm
 * 
 */
// TODO this should me moved to core 
public class MailHelper {
	private String smtpHost;

	private String fromEmailAddress;

	private String replyToAddress;

	private List<String> recipients = new ArrayList<String>();

	private List<String> ccRecipients = new ArrayList<String>();

	private List<String> bccRecipients = new ArrayList<String>();

	private HashMap<String, String> emailToNameMap = new HashMap<String, String>();

	private String subject;

	private String message;

	private ArrayList<File> attachments = new ArrayList<File>();

	/**
	 * Validates that this email is indeed ready to be sent.
	 */
	private void validateReadyForSend() {
		errorCondition("SMTP server has not been set.", smtpHost == null);
		errorCondition("From address has not been set.",
				fromEmailAddress == null);
		errorCondition("No recipients have been added.", recipients.size() == 0);
		errorCondition("Email subject line has not been set.", subject == null);
		errorCondition("Email message body has not been set.", message == null);

		for (File file : attachments) {
			errorCondition(file.getAbsolutePath()
					+ " does not exist or cannot be read.",
					!(file.exists() && file.canRead()));
		}
	}

	/**
	 * Assertion method, if the boolean is false, an IllegalStateException will
	 * be thrown.
	 * 
	 * @param string
	 * @param errorCondition
	 */
	private void errorCondition(String string, boolean errorCondition) {
		if (errorCondition == true) {
			throw new IllegalStateException(string);
		}
	}

	/**
	 * Action method for sending the email
	 * 
	 * @throws MessagingException
	 */
	public void sendEmail() throws MessagingException {
		validateReadyForSend();

		Properties javaMailProps = new Properties();
		javaMailProps.put("mail.smtp.host", smtpHost);
		Session javaMailSession = Session.getDefaultInstance(javaMailProps,
				null);

		MimeMessage mimeMessage = new MimeMessage(javaMailSession);

		mimeMessage.setFrom(createInternetAddress(fromEmailAddress));
		mimeMessage.setSubject(subject);
		if (replyToAddress != null) {
			mimeMessage.setReplyTo(createAddressArray(replyToAddress));
		}
		addRecipients(mimeMessage, Message.RecipientType.TO, recipients);
		addRecipients(mimeMessage, Message.RecipientType.CC, ccRecipients);
		addRecipients(mimeMessage, Message.RecipientType.BCC, bccRecipients);

		Multipart envelope = new MimeMultipart();

		MimeBodyPart body = new MimeBodyPart();
		body.setText(message);
		envelope.addBodyPart(body);

		addAttachments(envelope, attachments);

		mimeMessage.setContent(envelope);

		Transport.send(mimeMessage);
	}

	/**
	 * Adds the recipients to the message for the given recipient type
	 * 
	 * @param message
	 * @param recipType
	 * @param recipients
	 * @throws MessagingException
	 */
	private void addRecipients(MimeMessage message, RecipientType recipType,
			List<String> recipients) throws MessagingException {
		if (recipients.size() > 0) {
			InternetAddress[] emailAddresses = createAddressArray(recipients);
			message.setRecipients(recipType, emailAddresses);
		}
	}

	/**
	 * Adds the files to the message
	 * 
	 * @param envelope
	 * @param fileAttachments
	 * @throws MessagingException
	 */
	private void addAttachments(Multipart envelope, List<File> fileAttachments)
			throws MessagingException {
		for (File file : fileAttachments) {
			MimeBodyPart attachment = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(file.getAbsolutePath());
			attachment.setDataHandler(new DataHandler(fds));
			attachment.setFileName(fds.getName());
			envelope.addBodyPart(attachment);
		}
	}

	/**
	 * Factory method for creating an InternetAddress
	 * 
	 * @param emailAddress
	 * @return
	 * @throws AddressException
	 * @throws UnsupportedEncodingException
	 */
	private InternetAddress createInternetAddress(String emailAddress)
			throws AddressException {
		try {
			InternetAddress address = new InternetAddress(emailAddress);
			String name = emailToNameMap.get(emailAddress);
			if (name != null) {
				address.setPersonal(name);
			}
			return address;
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Factory method for creating an array of InternetAddress'es from a
	 * collection of String's
	 * 
	 * @param addresses
	 * @return
	 * @throws AddressException
	 */
	private InternetAddress[] createAddressArray(Collection<String> addresses)
			throws AddressException {
		return createAddressArray(addresses.toArray(new String[0]));
	}

	/**
	 * Factory method for creating an array of InternetAddress'es from an array
	 * of String's
	 * 
	 * @param addresses
	 * @return
	 * @throws AddressException
	 * 
	 * todo jjs this seems like a really weird place to have this 
	 */
	private InternetAddress[] createAddressArray(String... addresses)
			throws AddressException {
		InternetAddress[] addressArray = new InternetAddress[addresses.length];
		for (int index = 0; index < addresses.length; index++) {
			addressArray[index] = createInternetAddress(addresses[index]);
		}
		return addressArray;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public String getFromEmailAddress() {
		return fromEmailAddress;
	}

	public List<String> getRecipients() {
		return recipients;
	}

	public List<String> getCcRecipients() {
		return ccRecipients;
	}

	public List<String> getBccRecipients() {
		return bccRecipients;
	}

	public String getSubject() {
		return subject;
	}

	public String getMessage() {
		return message;
	}

	public List<File> getAttachments() {
		return attachments;
	}

	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setFromEmailAddress(String fromEmailAddress) {
		this.fromEmailAddress = fromEmailAddress;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	/**
	 * Map a personal name to an email address.
	 * 
	 * @return
	 */
	public HashMap<String, String> getEmailToNameMap() {
		return emailToNameMap;
	}

}
