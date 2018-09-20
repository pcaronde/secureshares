/*

*/

package ro.panzo.secureshares.servlet.services;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import ro.panzo.secureshares.db.DBManager;
import ro.panzo.secureshares.mongo.MongoDB;
import ro.panzo.secureshares.pojo.File;
import ro.panzo.secureshares.pojo.User;
import ro.panzo.secureshares.servlet.Service;
import ro.panzo.secureshares.util.ServiceUtil;
import ro.panzo.secureshares.util.Util;

import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
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
        DBManager db = DBManager.getInstance();
        try{
            if(isMultipart) {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                ServletContext servletContext = request.getSession().getServletContext();
                factory.setRepository((java.io.File) servletContext.getAttribute("javax.servlet.context.tempdir"));
                ServletFileUpload upload = new ServletFileUpload(factory);
                List<FileItem> items = upload.parseRequest(request);
                String filename = null;
                String mongoFileId = null;
                //look for uploaded file and save it
                for (FileItem fileItem : items) {
                    if (!fileItem.isFormField() && "file".equals(fileItem.getFieldName())) {
                        //String repository = Util.getInstance().getEnviromentValue("REPOSITORY");
                        filename = fileItem.getName();
                        //size = IOUtils.copyLarge(fileItem.getInputStream(), new FileOutputStream(repository + "/" + filename));
                        User loggedUser = db.getUserByUsername(request.getUserPrincipal().getName());
                        mongoFileId = MongoDB.getInstance().saveFile(loggedUser.getCompany().getId(), filename,
                                fileItem.getContentType(), fileItem.getInputStream());
                    }
                }
                //if file found and saved then save the info to database
                if(filename != null && mongoFileId != null){

                    File file = db.getFileByName(filename);
                    long fileId = -1;
                    if(file == null){
                        fileId = db.insertFile(request.getUserPrincipal().getName(), filename, mongoFileId);
                        result = fileId != -1;
                        log.debug("Insert file: " + filename + "(" +fileId + ")");
                    } else {
                        fileId = file.getId();
                        //@todo delete old file from mongo
                        result = db.updateFile(request.getUserPrincipal().getName(), filename, mongoFileId);
                        log.debug("Update file: " + filename + "(" +fileId + ")");
                    }
                    //in case of success upload send the email with info to admins
                    if (result) {
                        String subject = DBManager.getInstance().getI18NFor(language, "upload_notify_email_subject");
                        String pattern = DBManager.getInstance().getI18NFor(language, "upload_notify_email_text");
                        String text = MessageFormat.format(pattern, filename);

                        Util.getInstance().sendUploadNotifyMail(getAdminsEmailAddresses(), subject, text);
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
