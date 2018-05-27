package ro.panzo.secureshares.util;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.mail.internet.InternetAddress;

import java.lang.String;

import static org.mockito.Mockito.*;

/**
 * Created by petercaron on 27.05.18.
 */
public class UtilTest {
    @Mock
    Util ourInstance;
    @Mock
    Logger log;
    @InjectMocks
    Util util;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetEnviromentValue() throws Exception {
        try {
            String result = util.getEnviromentValue("env");
            //Assert.assertEquals("replaceMeWithExpectedResult", result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/**
    @Test
    public void testSendUploadNotifyMail() throws Exception {
        when(ourInstance.getInstance()).thenReturn(null);
        when(ourInstance.getEnviromentValue("OUTGOING_MAIL_SERVER")).thenReturn("smtp.pcconsultants.de");

        String mailServer = Util.getInstance().getEnviromentValue("OUTGOING_MAIL_SERVER");
        String smtpPort = Util.getInstance().getEnviromentValue("OUTGOING_MAIL_PORT");
        String mailBoxUser = Util.getInstance().getEnviromentValue("OUTGOING_MAIL_USER");
        String mailBoxPassword = Util.getInstance().getEnviromentValue("OUTGOING_MAIL_PASSWORD");
        String from = Util.getInstance().getEnviromentValue("OUTGOING_MAIL_FROM");

        //Util.getInstance().sendMail(mailServer, smtpPort, mailBoxUser, mailBoxPassword, subject, from, toEmailAddress, text, "text/html; charset=utf-8");
        //when(ourInstance.sendMail(mailServer, smtpPort, mailBoxUser, mailBoxPassword, subject, from, toEmailAddress, text, "text/h);
       when(ourInstance.sendMail(mailServer, smtpPort, mailBoxUser, mailBoxPassword, "subject", from, toEmailAddress, "Here is your mail", "text/html; charset=utf-8"));
    // when(ourInstance.sendMail(any(), any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(true);
        //when(ourInstance.getInternetAddresses(any())).thenReturn(new InternetAddress[]{null});

        //util.sendUploadNotifyMail(new String[]{"toEmailAddress"}, "subject", "text");
    }
 */
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme