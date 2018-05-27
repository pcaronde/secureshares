package ro.panzo.secureshares.servlet.services;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SendDownloadLinkServiceTest {

    SendDownloadLinkService downloadLink = new SendDownloadLinkService();

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testExecute() throws Exception {
        Assert.assertEquals("Should be ...", downloadLink.execute(HttpServletRequest, HttpServletResponse), "200 OK");
    }
}