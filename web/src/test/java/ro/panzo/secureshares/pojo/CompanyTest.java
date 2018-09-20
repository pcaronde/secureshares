package ro.panzo.secureshares.pojo;

import org.junit.Test;
import org.junit.Assert;

import org.mockito.InjectMocks;


public class CompanyTest {

    //@InjectMocks annotation is used to create and inject the mock object
    @InjectMocks
    Company tester = new Company(1, "caron", "repo");
    @Test
    public void testGetId() throws Exception {
        Assert.assertEquals("", tester.getId(), 1);
    }

    @Test
    public void testGetName() throws Exception {

        Assert.assertEquals(tester.getName(),"caron");

    }

    @Test
    public void testGetSubrepositoryName() throws Exception {

            Assert.assertEquals(tester.getSubrepositoryName(),"repo");

        }
    }
