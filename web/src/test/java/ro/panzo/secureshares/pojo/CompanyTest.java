package ro.panzo.secureshares.pojo;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Test;
;

public class CompanyTest {
    
    @Test
    public void testGetId() throws Exception {

    }

    @Test
    public void testGetName() throws Exception {
        Company tester = new Company(1, "caron", "repo");
        assertEquals("Name should be caron", "caron", tester.getName());
    }

    @Test
    public void testGetSubrepositoryName() throws Exception {

    }
}