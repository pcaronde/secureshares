package ro.panzo.secureshares.pojo;

import org.junit.Test;
import org.mockito.InjectMocks;

import static org.junit.Assert.*;

/**
 * Created by petercaron on 06.08.18.
 *
 * This class tests the class and methods in DownloadType.java
 */
public class DownloadTypeTest {

    @InjectMocks
    DownloadType downloadType = new DownloadType(1, "Peter Caron", 1, 1);

    @Test
    public void testGetId() throws Exception {
        assertEquals(1, downloadType.getId());
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("Peter Caron", downloadType.getName());
    }

    @Test
    public void testGetCount() throws Exception {
        assertEquals(1, downloadType.getCount());
    }

    @Test
    public void testGetValidity() throws Exception {
        assertEquals(1, downloadType.getValidity());
    }
}