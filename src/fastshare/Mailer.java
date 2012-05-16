package fastshare;

import java.util.Properties;
 
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class Mailer {

	public static void sendNotification(String _to, String _url) {
		final String username = "fastshare123@gmail.com";
		final String password = "fastshare123";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
                props.put("mail.smtp.starttls.enable","true");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("fastshare@o2.pl"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(_to));
			message.setSubject("(FastShare) - Sharing invitation !");
			message.setText("Hello ! \n\n"
                                + "You have just been invited to FastShare's sharing.\n"
                                + "This sharing's URL is: "+_url+"\n\n\n\n"
                                + "_______________________________\n"
                                + "Greetings from FastShare's Team !");
			Transport.send(message);
		} catch (MessagingException e) {
			//throw new RuntimeException(e);
                        e.printStackTrace();
		}
	}
}