package sendemail;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class SendEmail {
	/**
	 * Hàm gửi email qua api javamail
	 * @param to địa chỉ email nhận
	 * @param sub tiêu đề email
	 * @param msg nội dung email
	 */
	public static void send(String to, String sub, String msg) {
		//Email và password nơi gửi email
		String user = "smartworldemail@gmail.com";
		String password = "rvoewvayodaekwxg";
		//Cấu hình gửi email
		Properties pro = new Properties();
		pro.put("mail.smtp.host","smtp.gmail.com");
		pro.put("mail.smtp.port","587");
		pro.put("mail.smtp.auth","true");
		pro.put("mail.smtp.starttls.enable","true");
		pro.put("mail.smtp.ssl.protocols", "TLSv1.2");
		
		Session session = Session.getDefaultInstance(pro, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(sub);
			message.setContent(msg, "text/html");
			
			Transport.send(message);
		} catch (MessagingException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
