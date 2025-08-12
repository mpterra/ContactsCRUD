package conexaoEmail;

import model.Pessoa;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.*;
import javax.swing.JOptionPane;

import control.AtualizarContato;

import java.security.cert.X509Certificate;
import java.util.Properties;

public class EnviarEmail {

	private Pessoa pessoa;
	private final String username = "email address"; // Seu email
	private final String password = "api key"; // Sua senha

	public EnviarEmail(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@Override
	public String toString() {
		return "Dear Mr(s) " + pessoa.getNome() + ",\n" + "Thank you for your contact and interest.\n"
				+ "Our International Relation Office will contact you to develop our partnership.\n"
				+ "Yours sincerely,\n\n" + "PhD. Milton L. Asmus\n" + "Head of International Affairs\n"
				+ "Federal University of Rio Grande - FURG";
	}

	public void enviar() {
		// Ignorar a verificação de certificado SSL (uso apenas para desenvolvimento)
		try {
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			} };

			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			HostnameVerifier allHostsValid = (hostname, session) -> true;
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Configurações do servidor de email
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", "*");

		// Criação da sessão com autenticação
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Criação da mensagem
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(pessoa.getEmail()));
			message.setSubject("Thank you for your contact with FURG!");
			message.setText(this.toString());

			// Envio da mensagem
			Transport.send(message);

			JOptionPane.showMessageDialog(null, "Email Enviado com Sucesso!", "Sucesso",
					JOptionPane.INFORMATION_MESSAGE);

			atualizaStatusEmailEnviado(pessoa.getEmail());

		} catch (AuthenticationFailedException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Falha na autenticação!", "Falha no envio", JOptionPane.ERROR_MESSAGE);
			atualizaStatusEmailNaoEnviado(pessoa.getEmail());
		} catch (SendFailedException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Endereço de email incorreto!", "Falha no envio", JOptionPane.ERROR_MESSAGE);
			atualizaStatusEmailNaoEnviado(pessoa.getEmail());
		} catch (MessagingException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Falha no envio!", "Falha no envio", JOptionPane.ERROR_MESSAGE);
			atualizaStatusEmailNaoEnviado(pessoa.getEmail());
		}
	}

	private void atualizaStatusEmailEnviado(String email) {

		AtualizarContato atualizar = new AtualizarContato();
		atualizar.atualizarEmailEnviado(email);

	}

	private void atualizaStatusEmailNaoEnviado(String email) {

		AtualizarContato atualizar = new AtualizarContato();
		atualizar.atualizarEmailNaoEnviado(email);

	}
}
