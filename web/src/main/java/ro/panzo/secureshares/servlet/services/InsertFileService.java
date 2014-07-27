/*

*/

package ro.panzo.secureshares.servlet.services;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import ro.panzo.secureshares.db.DBManager;
import ro.panzo.secureshares.pojo.File;
import ro.panzo.secureshares.pojo.User;
import ro.panzo.secureshares.servlet.Service;
import ro.panzo.secureshares.util.ServiceUtil;
import ro.panzo.secureshares.util.Util;

import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class InsertFileService implements Service {

    private Logger log = Logger.getLogger(this.getClass());

    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean result = false;
        String language = (String)request.getSession().getAttribute("lang");
        List<String> messages = new LinkedList<String>();
        ServiceUtil su = ServiceUtil.getInstance();
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        try{
            if(isMultipart) {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                ServletContext servletContext = request.getSession().getServletContext();
                factory.setRepository((java.io.File) servletContext.getAttribute("javax.servlet.context.tempdir"));
                ServletFileUpload upload = new ServletFileUpload(factory);
                List<FileItem> items = upload.parseRequest(request);
                String filename = null;
                long size = 0;
                //look for uploaded file and save it
                for (FileItem fileItem : items) {
                    if (!fileItem.isFormField() && "file".equals(fileItem.getFieldName())) {
                        String repository = Util.getInstance().getEnviromentValue("REPOSITORY");
                        filename = fileItem.getName();
                        size = IOUtils.copyLarge(fileItem.getInputStream(), new FileOutputStream(repository + "/" + filename));
                    }
                }
                //if file found and saved save the info to database
                if(filename != null && size > 0){
                    DBManager db = DBManager.getInstance();
                    File file = db.getFileByName(filename);
                    long fileId = -1;
                    if(file == null){
                        fileId = db.insertFile(request.getUserPrincipal().getName(), filename);
                        result = fileId != -1;
                        log.debug("Insert file: " + filename + "(" +fileId + ")");
                    } else {
                        fileId = file.getId();
                        result = db.updateFile(request.getUserPrincipal().getName(), filename);
                        log.debug("Update file: " + filename + "(" +fileId + ")");
                    }
                    //in case of success upload send the email with info to admins
                    if (result) {
                        String subject = DBManager.getInstance().getI18NFor(language, "upload_notify_email_subject");
                        String pattern = DBManager.getInstance().getI18NFor(language, "upload_notify_email_text");
                        //String text = MessageFormat.format(pattern, filename);

                        /*String subject = "Secure-Shares Upload Notify";
                        StringBuffer text = new StringBuffer();
                        text.append("<html>");
                        text.append("<body>");
                        text.append("<p>").append("Dear Secure Shares User, ").append("</p><p>").append(" this is an automated message from secure-shares. A new file has been uploaded.").append("<p>");
                        text.append("<p><b>").append(filename).append("</b><p>");
                        text.append("<p>").append("Thank you for using SecureShares.<br/> Secure-Shares Team").append("</p>");
                        text.append("</body>");
                        text.append("</html>");
                        */
                        //@todo add support for SendGrid which requires a username OUTGOING_MAIL_USER instead of an email address as sender. Also as well we need to define a port (eg 587 or 25) OUTGOING_MAIL_PORT
                        //Util.getInstance().sendUploadNotifyMail(getAdminsEmailAddresses(), subject, text);
                    }
                }
            }
        } catch (Exception ex){
            log.debug(ex.getMessage(), ex);
        }
        String responseData = su.getJSON(result, messages);
        log.debug("Response: " + responseData);
        response.getWriter().write(responseData);
    }

    private String[] getAdminsEmailAddresses() throws NamingException, SQLException {
        //@todo to avoid sending alerts to all admins, we need to limit this - perhaps filter admin and company
        List<User> admins = DBManager.getInstance().getUsersByRole("admin");
        String[] emailAddresses = new String[admins.size()];
        int index = 0;
        for(User admin : admins){
            emailAddresses[index++] = admin.getUsername();
        }
        return emailAddresses;
    }

    public String getName() {
        return "Insert File";
    }

    public String getRole() {
        return "user||admin";
    }
}
