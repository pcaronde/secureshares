package ro.panzo.secureshares.pojo;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


/**
 * Created by petercaron on 29.05.18.
 *
 * This class tests the class and methods in User.java
 */
public class UserTest {
    @Mock
    Company company;
    @InjectMocks
    User user = new User(1, "caron", "admin", company);



    @Test
    public void testGetUsername() throws Exception {

        Assert.assertEquals(user.getUsername(), "caron");
    }

    @Test
    public void testGetRole() throws Exception {
        // Check false
        Assert.assertNotEquals(user.getRole(), "user");
        Assert.assertEquals("Role can be user or admin", user.getRole(), "admin");
    }
}
//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme