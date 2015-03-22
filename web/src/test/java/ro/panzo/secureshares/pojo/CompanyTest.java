package ro.panzo.secureshares.pojo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
        String s = "/\\r ";
        for(int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            String s1 = Character.toString(c);
            System.out.println("Count is: " + i);
            Company tester = new Company(1, "caron", s1);
            assertEquals("Repo should be "+s1, s1, tester.getSubrepositoryName());
        }
    }
}