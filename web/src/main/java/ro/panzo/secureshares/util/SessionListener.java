package ro.panzo.secureshares.util;

import org.apache.log4j.Logger;
import ro.panzo.secureshares.db.DBManager;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

    private Logger log = Logger.getLogger(SessionListener.class);

    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        log.debug("Session Created: " + httpSessionEvent.getSession().getId());
        httpSessionEvent.getSession().setAttribute("dbManager", DBManager.getInstance());
    }

    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        log.debug("Session Destroyed: " + httpSessionEvent.getSession().getId());
        httpSessionEvent.getSession().removeAttribute("dbManager");
    }
}
