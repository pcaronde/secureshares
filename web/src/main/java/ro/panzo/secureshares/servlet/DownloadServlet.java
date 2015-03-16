package ro.panzo.secureshares.servlet;

import org.apache.log4j.Logger;
import ro.panzo.secureshares.db.DBManager;
import ro.panzo.secureshares.mongo.MongoDB;
import ro.panzo.secureshares.pojo.Download;
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

public class DownloadServlet extends HttpServlet {

    private final Logger log = Logger.getLogger(Action.class);


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            String enc = request.getParameter("t");
            String dec = EncryptionUtil.getInstance().decryptDES(enc);
            long downloadId = Long.parseLong(dec.split("\\^")[0]);
            Download download = DBManager.getInstance().getDownloadById(downloadId);
            //@todo check if there is an ongoing download if download type is 1 = single download
            if(download != null && ((download.getDownloadType().getId() == 1 && download.getCount() == 0) ||
                    (download.getDownloadType().getId() >= 2 && download.getDownloadType().getId() <= 4 &&
                            (System.currentTimeMillis() - download.getDate().getTimeInMillis()) < download.getDownloadType().getValidity() * 60 * 60 * 1000))){
                response.setContentType("application/x-unknown");
                response.setHeader("Content-Disposition", "attachment; filename=" + download.getFile().getFilename());
                try {
                    MongoDB.getInstance().writeToOutputStream(download.getFile().getUser().getCompany().getId(), download.getFile().getMongoFileId(), response.getOutputStream());
                    DBManager.getInstance().updateDownloadCount(download.getId(), download.getCount() + 1);
                } catch (IOException ioex){
                    log.debug(ioex.getMessage(), ioex);
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
