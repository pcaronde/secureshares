package ro.panzo.secureshares.servlet.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import ro.panzo.secureshares.db.DBManager;
import ro.panzo.secureshares.mongo.MongoDB;
import ro.panzo.secureshares.pojo.Company;
import ro.panzo.secureshares.pojo.Download;
import ro.panzo.secureshares.pojo.DownloadType;
import ro.panzo.secureshares.pojo.File;
import ro.panzo.secureshares.pojo.User;
import ro.panzo.secureshares.util.EncryptionException;
import ro.panzo.secureshares.util.EncryptionUtil;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * @author Ciprian Ticu
 */
@PowerMockIgnore({"javax.management.*", "javax.crypto.*", "java.security.*"})
@PrepareForTest(value = {DownloadServlet.class, DBManager.class, MongoDB.class})
@RunWith(PowerMockRunner.class)
public class DownloadServletTest {

    @Mock
    private DBManager dbManager;

    @Mock
    private MongoDB mongoDB;

    private DownloadServlet servlet;

    @Before
    public void setup() {
        //we spy the tested clas to be able to verify if some of the private methods are called while testing parent method: service
        servlet = spy(new DownloadServlet());
        //both MongoDB and DBmanager need to be prepared for test so you will find them in the
        // @PrepateForTest annotation for the test class, together with the class under test (we what to spy it)
        //spy is equivelent of partial mock
        PowerMockito.mockStatic(DBManager.class);
        PowerMockito.mockStatic(MongoDB.class);
        when(DBManager.getInstance()).thenReturn(dbManager);
        when(MongoDB.getInstance()).thenReturn(mongoDB);
    }

    @Test
    public void testGetDownloadId() throws Exception {
        long id = 123L;
        String testValue = id + "^testvalue";
        EncryptionUtil util = EncryptionUtil.getInstance();
        long downloadId = Whitebox.invokeMethod(servlet, "getDownloadId", util.encryptDES(testValue));
        assertEquals(id, downloadId);
    }

    @Test
    public void testIsDownloadValid() throws Exception {
        boolean result = Whitebox.invokeMethod(servlet, "isDownloadValid", (Download)null);
        assertFalse(result);
        DownloadType downloadType = new DownloadType(1, "type", 1, 1);
        Download download = new Download(123L, this.getFileForTest(Calendar.getInstance()), downloadType, Calendar.getInstance(), 0);
        result = Whitebox.invokeMethod(servlet, "isDownloadValid", download);
        assertTrue(result);
        download = new Download(123L, this.getFileForTest(Calendar.getInstance()), downloadType, Calendar.getInstance(), 1);
        result = Whitebox.invokeMethod(servlet, "isDownloadValid", download);
        assertFalse(result);

        downloadType = new DownloadType(2, "type", 1, 1);
        Calendar downloadDate = Calendar.getInstance();
        downloadDate.add(Calendar.MINUTE, -59);
        download = new Download(123L, this.getFileForTest(Calendar.getInstance()), downloadType, downloadDate, 0);
        result = Whitebox.invokeMethod(servlet, "isDownloadValid", download);
        assertTrue(result);

        downloadType = new DownloadType(3, "type", 1, 1);
        downloadDate = Calendar.getInstance();
        downloadDate.add(Calendar.MINUTE, -59);
        download = new Download(123L, this.getFileForTest(Calendar.getInstance()), downloadType, downloadDate, 0);
        result = Whitebox.invokeMethod(servlet, "isDownloadValid", download);
        assertTrue(result);

        downloadType = new DownloadType(4, "type", 1, 1);
        downloadDate = Calendar.getInstance();
        downloadDate.add(Calendar.MINUTE, -59);
        download = new Download(123L, this.getFileForTest(Calendar.getInstance()), downloadType, downloadDate, 0);
        result = Whitebox.invokeMethod(servlet, "isDownloadValid", download);
        assertTrue(result);

        downloadType = new DownloadType(2, "type", 1, 1);
        downloadDate = Calendar.getInstance();
        downloadDate.add(Calendar.MINUTE, -61);
        download = new Download(123L, this.getFileForTest(Calendar.getInstance()), downloadType, downloadDate, 0);
        result = Whitebox.invokeMethod(servlet, "isDownloadValid", download);
        assertFalse(result);

        downloadType = new DownloadType(3, "type", 1, 1);
        downloadDate = Calendar.getInstance();
        downloadDate.add(Calendar.MINUTE, -61);
        download = new Download(123L, this.getFileForTest(Calendar.getInstance()), downloadType, downloadDate, 0);
        result = Whitebox.invokeMethod(servlet, "isDownloadValid", download);
        assertFalse(result);

        downloadType = new DownloadType(4, "type", 1, 1);
        downloadDate = Calendar.getInstance();
        downloadDate.add(Calendar.MINUTE, -61);
        download = new Download(123L, this.getFileForTest(Calendar.getInstance()), downloadType, downloadDate, 0);
        result = Whitebox.invokeMethod(servlet, "isDownloadValid", download);
        assertFalse(result);

    }

    @Test
    public void testService() throws ServletException, IOException, EncryptionException, SQLException, NamingException {
        long id = 123L;
        String testValue = id + "^testvalue";
        EncryptionUtil util = EncryptionUtil.getInstance();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletOutputStream out = mock(ServletOutputStream.class);
        when(request.getParameter("t")).thenReturn(util.encryptDES(testValue));
        when(response.getOutputStream()).thenReturn(out);
        DownloadType downloadType = new DownloadType(1, "type", 1, 1);
        //I choose to use download type = 1 to avoid date sync, we dont really care here becasue the isDownloadValid has its own test
        Download download = new Download(id, this.getFileForTest(Calendar.getInstance()), downloadType, Calendar.getInstance(), 0);
        when(dbManager.getDownloadById(id)).thenReturn(download);

        servlet.service(request, response);
        verify(response).setContentType("application/x-unknown");
        verify(response).setHeader("Content-Disposition", "attachment; filename=" + download.getFile().getFilename());
        verify(mongoDB).writeToOutputStream(download.getFile().getUser().getCompany().getId(), download.getFile().getMongoFileId(), out);
        verify(dbManager).updateDownloadCount(download.getId(), download.getCount() + 1);
    }

    @Test
    public void testServiceNotFound() throws ServletException, IOException, EncryptionException, SQLException, NamingException {
        long id = 123L;
        String testValue = id + "^testvalue";
        EncryptionUtil util = EncryptionUtil.getInstance();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("t")).thenReturn(util.encryptDES(testValue));
        DownloadType downloadType = new DownloadType(1, "type", 1, 1);
        //I choose to use download type = 1 to avoid date sync, we dont really care here becasue the isDownloadValid has its own test
        Download download = new Download(id, this.getFileForTest(Calendar.getInstance()), downloadType, Calendar.getInstance(), 1);
        when(dbManager.getDownloadById(id)).thenReturn(download);

        servlet.service(request, response);
        verify(response).sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    public void testServiceBadRequest() throws ServletException, IOException, EncryptionException, SQLException, NamingException {
        long id = 123L;
        String testValue = id + "^testvalue";
        EncryptionUtil util = EncryptionUtil.getInstance();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("t")).thenReturn(util.encryptDES(testValue));
        when(dbManager.getDownloadById(id)).thenThrow(new SQLException("test"));

        servlet.service(request, response);
        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST);
    }


    private File getFileForTest(Calendar calendar){
        Company company = new Company(432L, "company", "subrepository");
        User user = new User(147L, "username", "user", company);
        return new File(345L, user, "filename", calendar, "mongofield");
    }
}
