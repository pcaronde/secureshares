package ro.panzo.secureshares.servlet;

import org.apache.log4j.Logger;
import ro.panzo.secureshares.db.DBManager;
import ro.panzo.secureshares.pojo.File;
import ro.panzo.secureshares.util.EncryptionUtil;
import ro.panzo.secureshares.util.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Download extends HttpServlet {

    private final Logger log = Logger.getLogger(Action.class);

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            String enc = request.getParameter("t");
            String dec = EncryptionUtil.getInstance().decryptDES(enc);
            long fileId = Long.parseLong(dec.split("\\^")[0]);
            File file = DBManager.getInstance().getFileById(fileId);
            if(file != null && ((file.getDownloadType().getId() == 1 && file.getDownloadCount() == 0) ||
                    (file.getDownloadType().getId() >= 2 && file.getDownloadType().getId() <= 4 &&
                            (System.currentTimeMillis() - file.getDate().getTimeInMillis()) < file.getDownloadType().getValidity() * 60 * 60 * 1000))){
                response.setContentType("application/x-unknown");
                response.setHeader("Content-Disposition", "attachment; filename=" + file.getFilename());
                String repository = Util.getInstance().getEnviromentValue("REPOSITORY");
                BufferedInputStream bin = null;
                try {
                    bin = new BufferedInputStream(new FileInputStream(repository + "/" + file.getFilename()));
                    byte[] buffer = new byte[1024];
                    int read;
                    while((read = bin.read(buffer)) != -1){
                        response.getOutputStream().write(buffer, 0, read);
                    }
                    DBManager.getInstance().updateFileDownloadCount(file.getId(), file.getDownloadCount() + 1);
                } catch (IOException ioex){
                    log.debug(ioex.getMessage(), ioex);
                } finally {
                    if(bin != null){
                        bin.close();
                    }
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception ex){
            log.debug(ex.getMessage(), ex);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
