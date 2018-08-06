package ro.panzo.secureshares.servlet;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;


/**
 * Created by petercaron on 06.08.18.
 *
 * This class tests the getInstance invokation in ActionFactory.java
 */
public class ActionFactoryTest extends TestCase {

    @Test
    public void testGetInstance() throws Exception {
        ActionFactory af = ActionFactory.getInstance();
        Assert.assertNotNull(af);
        Assert.assertSame(af, ActionFactory.getInstance());
    }

    @Test
    public void testExecuteService() throws Exception {

    }
}