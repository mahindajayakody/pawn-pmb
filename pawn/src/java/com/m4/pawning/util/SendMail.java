package com.m4.pawning.util;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;


public class SendMail{

	private static final String SMTP_HOST_NAME = "10.2.2.246";
	private static final String SMTP_PORT = "25";
	private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	private static final String PROTOCOL = "smtp" ;
	private static final String AUTH_EMAIL = "sysinfo@pmb.lk";
	private static final String AUTH_PSWD = "fgd@#24132f";
	private static final String EMAIL_FROM = "sysinfo@pmb.lk";
	private static final Logger logger = Logger.getLogger(SendMail.class);
	private static final String defaultRecipients  = "mahinda@modular4.com";

	public boolean sendMessage(String recipients, String subject,String message, String from) throws MessagingException {

		Properties props = new Properties();

		props.put("mail.transport.protocol", PROTOCOL);
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.smtp.auth", "false");
		props.put("mail.debug", "true");
		props.put("mail.smtp.port", SMTP_PORT);

		Session session = Session.getInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(AUTH_EMAIL,AUTH_PSWD);
			}
		});

		session.setDebug(true);

		Message msg = new MimeMessage(session);
		InternetAddress addressFrom = new InternetAddress(EMAIL_FROM);
		msg.setFrom(addressFrom);
//		if (recipients=="" || recipients==null)
//			recipients = defaultRecipients;
//		else
//			recipients += "," + defaultRecipients ;
		
		String[] emailAddresses = recipients.split(",");

		InternetAddress[] addressTo = new InternetAddress[emailAddresses.length];
		int i = 0;
		for(String email : emailAddresses){
			addressTo[i] = new InternetAddress(email.trim());
			i++;
		}
		msg.setRecipients(Message.RecipientType.TO, addressTo);

		// Setting the Subject and Content Type
		msg.setSubject(subject);
		msg.setContent(message, "text/plain");
		Transport.send(msg);
		return true;
	}
	public boolean sendMessage(String recipients, String subject,String message, String from,String attachment) throws MessagingException {

		Properties props = new Properties();

		props.put("mail.transport.protocol", PROTOCOL);
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.smtp.auth", "false");
		props.put("mail.debug", "true");
		props.put("mail.smtp.port", SMTP_PORT);

		Session session = Session.getInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(AUTH_EMAIL,AUTH_PSWD);
			}
		});

		session.setDebug(true);

		Message msg = new MimeMessage(session);
		InternetAddress addressFrom = new InternetAddress(EMAIL_FROM);
		msg.setFrom(addressFrom);

		String[] emailAddresses = recipients.split(",");

		InternetAddress[] addressTo = new InternetAddress[emailAddresses.length];
		int i = 0;
		for(String email : emailAddresses){
			addressTo[i] = new InternetAddress(email.trim());
			i++;
		}
		msg.setRecipients(Message.RecipientType.TO, addressTo);
		logger.info("Mail Recipients List: "+ addressTo.toString());

		// Setting the Subject and Content Type
		msg.setSubject(subject);
		msg.setContent(message, "text/plain");
		
		//Modify By Mahinda to Send Attachement
		  // create the message part 
	    MimeBodyPart messageBodyPart = 
	      new MimeBodyPart();

	    //fill message
	    messageBodyPart.setText("Hi \n This is System Generated Mail \n Do not Reply");

	    Multipart multipart = new MimeMultipart();
	    multipart.addBodyPart(messageBodyPart);

	    // Part two is attachment
	    messageBodyPart = new MimeBodyPart();
	    DataSource source = new FileDataSource(attachment);
	    messageBodyPart.setDataHandler(new DataHandler(source));
	    messageBodyPart.setFileName(attachment);
	    multipart.addBodyPart(messageBodyPart);

	    // Put parts in message
	    msg.setContent(multipart);
		//End Of Modification
	    logger.info("Mail Message : "+ msg.toString());
		Transport.send(msg);
		return true;
	}

//	public static void main(String[] args) {
//		try {
//			new SendMail().sendMessage("mahinda@modular4.com, mahindajayakody@gmail.com", "hello", "test", "");
//		} catch (MessagingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}