package ro.panzo.secureshares.servlet;

import org.apache.log4j.Logger;
import ro.panzo.secureshares.servlet.services.DeleteUserService;
import ro.panzo.secureshares.servlet.services.InsertUserService;
import ro.panzo.secureshares.servlet.services.UpdateUserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ActionFactory {
    private Logger log = Logger.getLogger(ActionFactory.class);
    private static ActionFactory ourInstance = new ActionFactory();
    private Map<String, Service> services = new HashMap<String, Service>();

    public static ActionFactory getInstance() {
        return ourInstance;
    }

    private ActionFactory() {
        this.registerService("1", new InsertUserService());
        this.registerService("2", new UpdateUserService());
        this.registerService("3", new DeleteUserService());
    }

    public void executeService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = request.getParameter("a");
        if(key != null && key.length() > 0){
            Service service = services.get(key);
            if(service != null){
                if(request.isUserInRole(service.getRole())){
                    log.debug("Execute Service: " + service.getName() + "(" + key + ")");
                    service.execute(request, response);
                } else {
                    log.debug("Unauthorized Service Execution: " + service.getName() + "(" + key + ")");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
            } else {
                log.debug("Unregistered Service: " + key);
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            log.debug("Invalid Service: " + (key != null ? key : "null"));
        }
    }

    public void registerService(String key, Service service){
        services.put(key, service);
    }
}
