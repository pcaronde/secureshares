package ro.panzo.secureshares.servlet.services;

import org.apache.log4j.Logger;
import ro.panzo.secureshares.db.DBManager;
import ro.panzo.secureshares.mongo.MongoDB;
import ro.panzo.secureshares.pojo.File;
import ro.panzo.secureshares.pojo.User;
import ro.panzo.secureshares.servlet.Service;
import ro.panzo.secureshares.util.ServiceUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class DeleteFileService implements Service {

    private Logger log = Logger.getLogger(this.getClass());

    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean result = false;
        List<String> messages = new LinkedList<String>();
        ServiceUtil su = ServiceUtil.getInstance();
        String id = request.getParameter("id");
        DBManager db = DBManager.getInstance();
        try{
            if(su.validateLong(id)){
                File f = db.getFileById(Long.parseLong(id));
                if(f != null){
                    result = db.deleteFile(f.getId());
                    if(result){
                        /**String path = Util.getInstance().getEnvironmentValue("REPOSITORY");
                        java.io.File ioFile = new java.io.File(path + "/" + f.getFilename());
                        if(ioFile.exists()){
                            ioFile.delete();
                        }*/
                        User loggedUser = db.getUserByUsername(request.getUserPrincipal().getName());
                        MongoDB.getInstance().delete(loggedUser.getCompany().getId(), f.getMongoFileId());
                    }
                }
            }
        } catch (Exception ex){
            result = false;
            messages.add("Internal error!!!");
        }
        String responseData = su.getJSON(result, messages);
        log.debug("Response: " + responseData);
        response.getWriter().write(responseData);
    }

    public String getName() {
        return "Delete File";
    }

    public String getRole() {
        return "admin";
    }
}
