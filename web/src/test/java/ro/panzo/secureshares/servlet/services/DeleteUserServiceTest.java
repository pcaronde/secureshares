package ro.panzo.secureshares.servlet.services;

import org.junit.Assert;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUserServiceTest {

    DeleteUserService delUser = new DeleteUserService();
    HttpServletResponse response;
    HttpServletRequest request;


    @Test
    public void testGetName() throws Exception {
        Assert.assertEquals(delUser.getName(),"Delete User");
    }

    @Test
    public void testGetRole() throws Exception {
        Assert.assertEquals(delUser.getRole(),"admin");
    }
}