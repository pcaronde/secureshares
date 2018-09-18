package ro.panzo.secureshares.servlet;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * Created by petercaron on 06.08.18.
 *
 * This class tests the getInstance invocation in ActionFactory.java
 */
public class ActionFactoryTest extends TestCase {

    ActionFactory af = ActionFactory.getInstance();


    @Test
    public void testGetInstance() throws Exception {

        Assert.assertNotNull(af);
        Assert.assertSame(af, ActionFactory.getInstance());
    }

    @Test
    public void testExecuteService() throws Exception {
        //af.executeService(request, response);
    }
}