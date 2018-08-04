package ro.panzo.secureshares.servlet;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletConfig;
import java.util.ResourceBundle;

import static org.mockito.Mockito.*;

/**
 * Created by petercaron on 04.08.18.
 */
public class ActionTest {
    @Mock
    Logger log;
    @Mock
    ResourceBundle lStrings;
    @Mock
    ResourceBundle lStrings;
    @Mock
    ServletConfig config;
    @InjectMocks
    Action action;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testService() throws Exception {
        action.service(null, null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme