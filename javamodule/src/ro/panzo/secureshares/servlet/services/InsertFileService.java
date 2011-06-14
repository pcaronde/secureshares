package ro.panzo.secureshares.servlet.services;

import org.apache.log4j.Logger;
import ro.panzo.secureshares.db.DBManager;
import ro.panzo.secureshares.pojo.File;
import ro.panzo.secureshares.servlet.Service;
import ro.panzo.secureshares.util.ServiceUtil;
import ro.panzo.secureshares.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class InsertFileService implements Service {

    private Logger log = Logger.getLogger(this.getClass());

    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean result = false;
        List<String> messages = new LinkedList<String>();
        ServiceUtil su = ServiceUtil.getInstance();
        String downloadTypeId = request.getParameter("tdid");
        String filename = request.getParameter("fn");
        try{
            DBManager db = DBManager.getInstance();
            File file = db.getFileByName(filename);
            long lDownloadTypeId = Long.parseLong(downloadTypeId);
            if(file == null){
                result = db.insertFile(request.getUserPrincipal().getName(), lDownloadTypeId, filename);
            } else {
                result = db.updateFile(request.getUserPrincipal().getName(), lDownloadTypeId, filename);
            }
            //in case of success upload send the email with the link and download type is not disabled
            if(result && lDownloadTypeId != 5){
                String subject = "Secure-Shares Upload Notify";
                StringBuffer text = new StringBuffer();
                text.append("<html>");
                text.append("<body>");
                text.append("<p>").append("A new file was uploaded").append("<p>");
                text.append("<p>").append("to download the file click <a href=\"\">here</a>").append("</p>");
                text.append("<p>").append("This link is available only for <b>").append(lDownloadTypeId).append("</b></p>");
                text.append("<p>").append("Have a nice day.<br/> Secure-Shares Team").append("</p>");
                text.append("</body>");
                text.append("</html>");
                Util.getInstance().sendDownloadLinkViaMail(request.getUserPrincipal().getName(), subject, text.toString());
            }
        } catch (Exception ex){
            log.debug(ex.getMessage(), ex);
        }
        String responseData = su.getJSON(result, messages);
        log.debug("Response: " + responseData);
        response.getWriter().write(responseData);
    }

    public String getName() {
        return "Insert File";
    }

    public String getRole() {
        return "user||admin";
    }
}
