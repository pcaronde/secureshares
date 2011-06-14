package ro.panzo.secureshares.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends Authenticator {
    private String username;
    private String password;

    public SMTPAuthenticator(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication(username, password);
    }
}
