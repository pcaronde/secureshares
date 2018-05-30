package ro.panzo.secureshares.servlet.services;

import org.junit.Test;
import org.mockito.*;

import org.powermock.api.easymock.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.StringWriter;


public class SendDownloadLinkServiceTest {
/**
@Test
public void testExecute() throws Exception {

    SendDownloadLinkService service = PowerMock(new SendDownloadLinkService());
    StringWriter writer = new StringWriter();
    HttpServletRequest request = Mock(HttpServletRequest);
    HttpServletResponse response = Mock(HttpServletResponse);

    //this will collect all data written by your service to response
    when(response.getWriter).thenReturn(writer);
    HttpServletSession session = mock(HttpServletSession.class);
    //we program the mocked request
    when(request.getParameter("fid")).thenReturn("123");
    //do the same for all necesary request parameters
    //use same strategy for mocking the session attributes (the "lang") but first
    when(request.getSession()).thenReturn(session);
}
*/
/**
    @Mock
    private SendDownloadLinkService downloadLinkService;

    //@InjectMocks
    //private ?????;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testExecute() throws Exception {

        //Assert.assertEquals("Should be ...", downloadLinkService.execute(HttpServletRequest, HttpServletResponse), "200 OK");
    }
    */
}