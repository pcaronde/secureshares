package ro.panzo.secureshares.servlet.services;

import org.apache.log4j.Logger;
import ro.panzo.secureshares.db.DBManager;
import ro.panzo.secureshares.pojo.DownloadType;
import ro.panzo.secureshares.pojo.File;
import ro.panzo.secureshares.pojo.User;
import ro.panzo.secureshares.servlet.Service;
import ro.panzo.secureshares.util.EncryptionUtil;
import ro.panzo.secureshares.util.ServiceUtil;
import ro.panzo.secureshares.util.Util;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class SendDownloadLinkService implements Service {

    private Logger log = Logger.getLogger(this.getClass());

    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean result = false;
        List<String> messages = new LinkedList<String>();
        ServiceUtil su = ServiceUtil.getInstance();
        String fileId = request.getParameter("fid");
        String downloadTypeId = request.getParameter("dtid");
        String recipients = request.getParameter("r");
        log.debug("FileId: " + fileId);
        log.debug("DownloadTypeId: " + downloadTypeId);
        log.debug("Recipients: " + recipients);
        try{
            if(su.validateLong(fileId) && su.validateLong(downloadTypeId) && recipients != null && recipients.length() > 0){
                File f = DBManager.getInstance().getFileById(Long.parseLong(fileId));
                DownloadType downloadType = DBManager.getInstance().getDownloadTypeById(Long.parseLong(downloadTypeId));
                if(f != null && downloadType != null && downloadType.getId() != 5){
                    //insert download into db
                    long downloadId = DBManager.getInstance().insertDownload(f.getId(), downloadType.getId());
                    if(downloadId != -1){
                        String url =  request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
                        String path = "/download?t=" + EncryptionUtil.getInstance().encryptDES(downloadId + "^" + System.currentTimeMillis());
                        String subject = "Secure-Shares Download Notify";
                        StringBuilder text = new StringBuilder();
                        text.append("<html>");
                        text.append("<body>");
                        text.append("<p>").append("Dear Secure Shares User, ").append("</p><p>").append(" This is a download notification. ")
                                .append("You have been send a secure shares file and can download the file using the link below. ")
                                .append("<br/>").append("Though secure-shares makes every effort to assure the integrity and files please make sure to check all downloaded")
                                .append("file with an up-to-date anti-virus program to insure the security of your workstation.").append("<p>");
                        text.append("<p><a href='").append(url).append(path).append("' target=\"_blank\"><b>").append(f.getFilename()).append("</b></a><p>");
                        text.append("<p>The link is available for: ").append(downloadType.getName()).append("</p>");
                        text.append("<p>").append("Thank you for using secure-shares.<br/> Your Secure-Shares Team").append("</p>");
                        text.append("</body>");
                        text.append("</html>");
                        log.debug("You can download your file via this link: " + url + path);
                        Util.getInstance().sendUploadNotifyMail(recipients.split(","), subject, text.toString());
                        result = true;
                    } else {
                        messages.add("Internal error!!!(db)");
                    }
                }
            } else {
                messages.add("Internal error!!!(parameters)");
            }
        } catch (Exception ex){
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
