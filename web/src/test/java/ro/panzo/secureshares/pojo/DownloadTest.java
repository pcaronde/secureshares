package ro.panzo.secureshares.pojo;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
//import sun.util.resources.cldr.cgg.CalendarData_cgg_UG;

import java.util.Calendar;
import java.util.Date;


public class DownloadTest {
    private File file;
    private DownloadType downloadType;
    //@InjectMocks annotation is used to create and inject the mock object

    //Download(long id, File file, DownloadType downloadType, Calendar date, int count)
    @InjectMocks
    Download myDownload = new Download(1, file, downloadType ,Calendar.getInstance(), 1);

    @Test
    public void testGetId() throws Exception {
        Assert.assertEquals("The Id should be 1",myDownload.getId(),1);
    }

    @Test
    public void testGetFile() throws Exception {

    }

    @Test
    public void testGetDownloadType() throws Exception {

    }


    @Test
    public void testGetDate() throws Exception {

    }

    @Test
    public void testGetCount() throws Exception {
        Assert.assertEquals("The count should be 1",myDownload.getCount(),1);
    }
}