package lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.RmiUtils;

public class ActMailingServiceImpl extends UnicastRemoteObject implements ActMailingService {

	private static final long serialVersionUID = -920693707627878169L;
	private static ActMailingServiceImpl instance = null;

	private ActMailingServiceImpl() throws RemoteException {
		super(RmiUtils.getInstance().getPort());
	}

	@Override
	public PtBoolean ieSendMail(PtString address, PtString title, PtString content) throws RemoteException {
		final String username = "icrash2017g06@gmail.com";
		final String password = "";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("icrash2017g06@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address.getValue()));
			message.setSubject(title.getValue());
			message.setText(content.getValue());

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
			return new PtBoolean(false);
		}
		return new PtBoolean(true);
	}

	public static ActMailingServiceImpl getInstance() throws RemoteException {
		if (instance == null) {
			instance = new ActMailingServiceImpl();
		}
		return instance;
	}

}
