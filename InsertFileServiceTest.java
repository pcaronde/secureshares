package ro.panzo.secureshares.servlet.services;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
//import sun.jvm.hotspot.utilities.Assert;

public class InsertFileServiceTest {

    @Test
    public void testExecute() throws Exception {

    }

    //@InjectMocks annotation is used to create and inject the mock object
    @InjectMocks
    InsertFileService fileService = new InsertFileService();

//    @Mock
//    InsertUserService insertUser;

    @Test
    public void testGetName() throws Exception {

    }

    @Test
    public void testGetRole() throws Exception {
        Assert.assertEquals(fileService.getRole(),"user||admin");
    }

}
