package ro.panzo.secureshares.pojo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;

import static org.mockito.Mockito.*;

/**
 * Created by petercaron on 29.05.18.
 */
public class FileTest {
    @Mock
    User user;
    @Mock
    Calendar date;
    @InjectMocks
    File file = new File(1, user, "filename.txt", date, "some_field");

    /**@Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
*/
    @Test
    public void testGetFilename() throws Exception {

        Assert.assertEquals(file.getFilename(), "filename.txt");
    }

    @Test
    public void testGetField() throws Exception {

        Assert.assertEquals("Mongo field id or name?", file.getMongoFileId(), "some_field");
    }
}
