package servlets;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * This servlet takes care of all the email sending in this web application
 * it takes the following parameters: 
 * String EmailType
 * List<String> Recipients
 * 
 * EmailType = "verification" Send the verification Email
 * EmailType = "notification" Send the invitation Email
 */

class SMTPAuthenticator extends Authenticator {
    private static final String SMTP_AUTH_USER = "democsci201@gmail.com";
    private static final String SMTP_AUTH_PASSWORD = "~a1b2c3d4";

    public PasswordAuthentication getPasswordAuthentication () {
        String username = SMTP_AUTH_USER;
        String password = SMTP_AUTH_PASSWORD;

        return new PasswordAuthentication( username,  password );
    }
}

class EmailSender extends Thread{
    private String from;
    private String to;
    private String subject;
    private String messageBody;
    private String host;

    private Properties properties;

    private MimeMessage message;
    private BodyPart messageBodyPart;
    private Multipart multipart;

    private Authenticator authenticator;

    public EmailSender (String from, String to, String emailType) throws FileNotFoundException {
        this.from = from;
        this.to = to;
        
        /*this part reads the html file that will be the email content*/
        FileReader fr = null;
        BufferedReader br;
        
        if(emailType.equals("verification")) {
        	subject = "Room Reservation Verification";
        	fr = new FileReader("verificationEmailContent.html");
        }else if(emailType.equals("notification")) {
        	subject = "Your friend has just reserved a room!";
        	fr = new FileReader("notificationEmailContent.html");
        }
        
        if(fr != null) {
        	br = new BufferedReader(fr);
        }else {
        	return;
        }
        
        try {
        	String line;
        	do {
        		line = br.readLine();
        		messageBody += line;
        	}while(br.readLine() != null);
        }catch(IOException e) {
        	System.out.println("Error in reading the file in emailsender.emailsender() " + e.getMessage());
        }
        
        /*email setting initialization*/
        host = "smtp.gmail.com";
        authenticator = new SMTPAuthenticator ();
        properties = System.getProperties ();
        properties.put ( "mail.smtp.host", host );
        properties.put ( "mail.smtp.starttls.enable", "true" );
        properties.put ( "mail.smtp.port", "587" );
        properties.put ( "mail.smtp.auth", "true" );
    }
    
    public void sendEmail() {
        try {
            Session session = Session.getDefaultInstance ( properties, authenticator );
            message = new MimeMessage ( session );
            message.setFrom ( new InternetAddress ( from ) );
            message.addRecipient ( Message.RecipientType.TO,
                                new InternetAddress ( to ) );
            message.setSubject ( subject );

            multipart = new MimeMultipart();
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent ( messageBody, "text/html" );
            multipart.addBodyPart ( messageBodyPart );
            message.setContent ( multipart );

            Transport.send ( message );
            System.out.println("Message send successfully....");
        } catch ( Exception e ) {
        	System.out.println("Message sending failed! in EmailSender.send()");
            e.printStackTrace ();
        }
    }
    
    public void run() {
    	sendEmail();
    }
}

@WebServlet("/EmailSenderProcessor")
public class EmailSenderProcessor extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArrayList<String> recipients = new ArrayList<String> ();
	private ExecutorService executor = Executors.newCachedThreadPool();
	
	private void SendToAllRecipients(String EmailType) throws FileNotFoundException {
		for(String st : recipients) {
			EmailSender emailSender = new EmailSender("democsci201@gmail.com", st, EmailType);
			executor.execute(emailSender);
		}
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {		
		try {
			String capacity_string = request.getParameter("Room Capacity");
			int capacity = Integer.parseInt(capacity_string);
			
			for(int i=0; i<capacity; i++) {
				String recipient = request.getParameter("recipient"+i);
				recipients.add(recipient);
			}
			
			String EmailType = request.getParameter("EmailType");
			if(EmailType.equals("verification")) {
				SendToAllRecipients("verification");
			}else if(EmailType.equals("notification")) {
				SendToAllRecipients("notification");
			}else {
	        	System.out.println("unknown email type in emailsender.emailsender()");
	        	return;
			}
			
		}catch(IOException e) {
			System.out.println("ioe in sending EmailSenderProcessor.SendToAllRecipients(): " + e.getMessage());
		}catch(NumberFormatException e) {
			System.out.println("nfe in sending EmailSenderProcessor.SendToAllRecipients(): " + e.getMessage());
		}catch(Exception e) {
			System.out.println("exception in sending EmailSenderProcessor.SendToAllRecipients(): " + e.getMessage());
		}
	}
}
