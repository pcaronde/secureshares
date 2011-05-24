package ro.panzo.secureshares.servlet.services;

import org.apache.log4j.Logger;
import ro.panzo.secureshares.db.DBManager;
import ro.panzo.secureshares.servlet.Service;
import ro.panzo.secureshares.util.ServiceUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class InsertUserService implements Service {

    private Logger log = Logger.getLogger(this.getClass());

    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean result = false;
        List<String> messages = new LinkedList<String>();
        ServiceUtil su = ServiceUtil.getInstance();
        String username = request.getParameter("u");
        String password = request.getParameter("p");
        String retypePassword = request.getParameter("rp");
        try{
            if(su.validateEmail(username) && su.validateString(password) && su.validateString(retypePassword) && password.equals(retypePassword)){
                result = DBManager.getInstance().insertUser(username, password);
            } else {
                if(!su.validateEmail(username)){
                    messages.add("Invalid username (eg: myuser@mydomain.com)!!!");
                }
                if(!su.validateString(password)){
                    messages.add("Invalid password!!!");
                }
                if(!su.validateString(retypePassword)){
                    messages.add("Invalid retyped password!!!");
                }
                if(su.validateString(password) && su.validateString(retypePassword) && !password.equals(retypePassword)){
                    messages.add("The retyped password must match password!!!");
                }
            }
        } catch (Exception ex){
            log.debug(ex.getMessage(), ex);
            if(ex.getMessage() != null && ex.getMessage().contains("uq_username")){
                messages.add("Username already exist!!!");
            } else {
                messages.add("Internal error!!!");
            }
        }
        String responseData = su.getJSON(result, messages);
        log.debug("Response: " + responseData);
        response.getWriter().write(responseData);
    }

    public String getName() {
        return "Insert User";
    }
}
