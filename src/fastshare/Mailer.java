package fastshare;

import java.util.Properties;
 
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
/**
 * Class responsible for E-mail delivery
 */
public class Mailer {

    /**
     * Sends E-mail notification about new sharing to it's receivers
     * @param _to   receiver's e-mail
     * @param _url  sharing's URL
     */
	public static void sendNotification(String _to, String _url) {
		final String username = fastshare.Settings.getUsername();
		final String password = fastshare.Settings.getPassword();
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", fastshare.Settings.getAuth());
		props.put("mail.smtp.host", fastshare.Settings.getSmtphost());
		props.put("mail.smtp.port", fastshare.Settings.getSmtpport());
                props.put("mail.smtp.starttls.enable",fastshare.Settings.getStarttls());
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
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