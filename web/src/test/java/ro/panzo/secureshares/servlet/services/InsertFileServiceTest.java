package ro.panzo.secureshares.servlet.services;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
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
        //Assert.assertEquals(fileService.getName(),"File Name");
        Assert.assertEquals(fileService.getName(),"Insert File");
    }

    @Test
    public void testGetRole() throws Exception {
        Assert.assertEquals(fileService.getRole(),"user||admin");
    }

}
