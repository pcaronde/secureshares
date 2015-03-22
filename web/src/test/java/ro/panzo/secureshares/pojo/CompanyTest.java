package ro.panzo.secureshares.pojo;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Test;
;

public class CompanyTest {

    /**
     *   @Test(expected = IllegalArgumentException.class)
    public void testExceptionIsThrown() {
    MyClass tester = new MyClass();
    tester.multiply(1000, 5);
    }

     @Test
     public void testMultiply() {
     MyClass tester = new MyClass();
     assertEquals("10 x 5 must be 50", 50, tester.multiply(10, 5));
     }
     * @throws Exception
     */

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