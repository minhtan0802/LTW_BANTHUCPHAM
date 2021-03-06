package ptithcm.bean;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service("mailer")
public class Mailer {

		@Autowired
		JavaMailSender mailer;
		
		@Autowired
		@Qualifier("attachfile")
		UploadFile baseAttachFile;
		
		public void send(String from, String to, String subject, String body, MultipartFile attachments) {
			try {
				MimeMessage mail = mailer.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mail, true, "utf-8");
				
				helper.setFrom("banthucphamtkt@gmail.com", from);
				helper.setTo(to);
				helper.setReplyTo("banthucphamtkt@gmail.com");
				helper.setSubject(subject);
				helper.setText(body, true);
				
				if(!attachments.isEmpty())
				{
					String baseUploadFile = baseAttachFile.getBasePath() + File.separator + attachments.getOriginalFilename();
					attachments.transferTo(new File(baseUploadFile));
					
					FileSystemResource file1 = new FileSystemResource(new File(baseUploadFile));
					helper.addAttachment(attachments.getOriginalFilename(),file1);
				}
				
				mailer.send(mail);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public void sendPassword(String newPass, String email) {
			try {
				MimeMessage mail = mailer.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mail, true, "utf-8");
				helper.setFrom("banthucphamtkt@gmail.com", "THỰC PHẨM TKT");
				helper.setTo(email);
				helper.setReplyTo("banthucphamtkt@gmail.com");
				helper.setSubject("THỰC PHẨM TKT - Mật khẩu mới cho tài khoản");
				helper.setText("<p>Đây là mật khẩu mới cho tài khoản của bạn: <b>" + newPass + "</b></p><p>Bạn có thể đổi mật khẩu sau khi đăng nhập. Thân gửi!</p>", true);
				mailer.send(mail);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public void sendVerifyCode(String code, String email) {
			try {
				MimeMessage mail = mailer.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mail, true, "utf-8");
				helper.setFrom("banthucphamtkt@gmail.com", "THỰC PHẨM TKT");
				helper.setTo(email);
				helper.setReplyTo("banthucphamtkt@gmail.com");
				helper.setSubject("THỰC PHẨM TKT - Xác thực tài khoản");
				helper.setText("<p>Đây là mã xác thực cho tài khoản của bạn: <b>" + code + "</b></p>", true);
				mailer.send(mail);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		public void sendThanks(String email,String name) {
			try {
				String text="<p>Cảm ơn bạn đã ghé THỰC PHẨM TKT. Chúng tôi rất vui vì bạn đã tìm thấy món đồ ưng ý </p>"
						+ "<p>THỰC PHẨM TKT xin cảm ơn bạn vì đã mua hàng tại đây.</p>";
				
				MimeMessage mail = mailer.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mail, true, "utf-8");
				helper.setFrom("banthucphamtkt@gmail.com", "THỰC PHẨM TKT");
				helper.setTo(email);
				helper.setReplyTo("banthucphamtkt@gmail.com");
				helper.setSubject("THỰC PHẨM TKT - Lời cảm ơn");
				helper.setText("<p>"+name+" thân mến!"+"</p>"+text, true);
				mailer.send(mail);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
	

}
