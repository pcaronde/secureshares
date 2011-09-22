package ro.panzo.secureshares.servlet.services;

import org.apache.log4j.Logger;
import ro.panzo.secureshares.db.DBManager;
import ro.panzo.secureshares.pojo.DownloadType;
import ro.panzo.secureshares.pojo.File;
import ro.panzo.secureshares.servlet.Service;
import ro.panzo.secureshares.util.EncryptionUtil;
import ro.panzo.secureshares.util.ServiceUtil;
import ro.panzo.secureshares.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
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
        String message = request.getParameter("msg");
        String language = (String)request.getSession().getAttribute("lang");
        log.debug("FileId: " + fileId);
        log.debug("DownloadTypeId: " + downloadTypeId);
        log.debug("Recipients: " + recipients);
        log.debug("Language: " + language);
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
                        String subject = DBManager.getInstance().getI18NFor(language, "download_notify_email_subject");
                        String pattern = DBManager.getInstance().getI18NFor(language, "download_notify_email_text");
                        String text = MessageFormat.format(pattern, url + path, f.getFilename(), downloadType.getName(), message);
                        log.debug("You can download your file via this link: " + url + path);
                        Util.getInstance().sendUploadNotifyMail(recipients.split(","), subject, text);
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
