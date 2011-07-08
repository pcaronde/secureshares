package ro.panzo.secureshares.util;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.*;

public class Util {
    private static Util ourInstance = new Util();
    private Logger log = Logger.getLogger(Util.class);

    public static Util getInstance() {
        return ourInstance;
    }

    private Util() {
    }

    public String getEnviromentValue(String env)
    {
        String rez = null;
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:/comp/env");
            rez = (String) envCtx.lookup(env);
        } catch (NamingException ex) {
            log.error(ex.getMessage(), ex);
        }
        return rez;
    }

    public void sendUploadNotifyMail(final String[] toEmailAddress, final String subject, final String text){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String mailServer = Util.getInstance().getEnviromentValue("OUTGOING_MAIL_SERVER");
                String mailBoxUser = Util.getInstance().getEnviromentValue("OUTGOING_MAIL_FROM");
                String mailBoxPassword = Util.getInstance().getEnviromentValue("OUTGOING_MAIL_PASSWORD");
                String from = Util.getInstance().getEnviromentValue("OUTGOING_MAIL_FROM");
                Util.getInstance().sendMail(mailServer, mailBoxUser, mailBoxPassword, subject, from, toEmailAddress, text, "text/html; charset=utf-8");
            }
        });
        t.start();
    }

    private boolean sendMail(String smtpHost, String mailboxUser, String mailboxPassword, String subject, String from,
                            String[] to, String text, String contentType)
    {
        boolean rez = false;
        if(subject == null) {
            subject = " ";
        }
        if(text == null) {
            text = " ";
        }
        if (smtpHost != null && smtpHost.trim().length() > 0 &&
                from != null && from.trim().length() > 0 &&
                text != null && text.trim().length() > 0 &&
                to != null && to.length > 0)
        {
            Properties props = new Properties();
            props.put("mail.smtp.host", smtpHost);
            props.put("mail.smtp.auth", "true");
            try
            {
                Session session = Session.getInstance(props, new SMTPAuthenticator(mailboxUser, mailboxPassword));
                //session.setDebug(true);
                //session.setDebugOut(new PrintStream(new FileOutputStream("/opt/tomcat6/logs/ewops_smtp.log")));
                MimeMessage msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress(from));
                msg.setRecipients(Message.RecipientType.TO, this.getInternetAddresses(to));
                msg.setSubject(subject);
                msg.setSentDate(new Date());

                MimeMultipart mixedPart = new MimeMultipart("mixed");
                BodyPart alternativeBodyPart = new MimeBodyPart();
                MimeMultipart alternativeMultiPart = new MimeMultipart("alternative");

                BodyPart htmlPart = new MimeBodyPart();
                htmlPart.setContent(text, contentType);
                alternativeMultiPart.addBodyPart(htmlPart);

                alternativeBodyPart.setContent(alternativeMultiPart);

                mixedPart.addBodyPart(alternativeBodyPart);
                msg.setContent(mixedPart);
                Transport.send(msg);
                rez = true;
            }
            catch (MessagingException mex){
                log.error(mex.getMessage(), mex);
                Exception ex = mex.getNextException();
                if (ex != null){
                    log.error(ex.getMessage(), ex);
                }
            }
            catch(SecurityException ex){
                log.error(ex.getMessage(), ex);
            }
        }
        log.debug("send mail to: " + Arrays.asList(to).toString() + " from: " + from + " : " + (rez ? "SUCCESS" : "FAILURE"));
        return rez;
    }

    private InternetAddress[] getInternetAddresses(String[] src) throws AddressException {
        List<InternetAddress> rez = new LinkedList<InternetAddress>();
        if(src != null)
        {
            for(String address: src){
                rez.add(new InternetAddress(address.trim()));
            }
        }
        return rez.toArray(new InternetAddress[]{});
    }
}
