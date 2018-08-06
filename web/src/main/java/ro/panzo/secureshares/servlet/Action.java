package ro.panzo.secureshares.servlet;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * This file generates a warning when compiled and tested. "No appenders could be found for logger"
 *
 * According to the log4j site:
 * Most commonly this is due to multiple appenders attempting to use the same file path and most likely by having multiple
 * independent instances of log4j read the same configuration file, however having the log file open by another process
 * (an editor, backup utility) can also interfere with rolling. No provided file appender is reliable when multiple
 * instances are writing to the same file path and java.io provides no mechanism to coordinate writing between JVM's.
 */
public class Action extends HttpServlet {

    private final Logger log = Logger.getLogger(Action.class);

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        if(request.getUserPrincipal() != null){
            ActionFactory.getInstance().executeService(request, response);
        } else {
            log.debug("No user logged in, unauthorized access denied");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
