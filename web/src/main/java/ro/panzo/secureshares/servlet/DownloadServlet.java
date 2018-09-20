package ro.panzo.secureshares.servlet;

import org.apache.log4j.Logger;
import ro.panzo.secureshares.db.DBManager;
import ro.panzo.secureshares.mongo.MongoDB;
import ro.panzo.secureshares.pojo.Download;
import ro.panzo.secureshares.pojo.File;
import ro.panzo.secureshares.servlet.Action;
import ro.panzo.secureshares.util.EncryptionException;
import ro.panzo.secureshares.util.EncryptionUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DownloadServlet extends HttpServlet {

    private final long HOUR_MILLIS = 60 * 60 * 1000;
    private final Logger log = Logger.getLogger(Action.class);


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            long downloadId = this.getDownloadId(request.getParameter("t"));
            DBManager dbManager = DBManager.getInstance();
            Download download = dbManager.getDownloadById(downloadId);
            //@todo check if there is an ongoing download if download type is 1 = single download
            if(this.isDownloadValid(download)){
                response.setContentType("application/x-unknown");
                File secureShareFile = download.getFile();
                response.setHeader("Content-Disposition", "attachment; filename=" + secureShareFile.getFilename());
                try {
                    MongoDB.getInstance().writeToOutputStream(secureShareFile.getUser().getCompany().getId(), secureShareFile.getMongoFileId(), response.getOutputStream());
                    dbManager.updateDownloadCount(download.getId(), download.getCount() + 1);
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

    private long getDownloadId(String encodedValue) throws EncryptionException {
        String dec = EncryptionUtil.getInstance().decryptDES(encodedValue);
        return Long.parseLong(dec.split("\\^")[0]);
    }

    private boolean isDownloadValid(Download download){
        if(download == null){
            return false;
        }
        long downloadTypeId = download.getDownloadType().getId();
        return (downloadTypeId == 1 && download.getCount() == 0) ||
                (downloadTypeId >= 2 && downloadTypeId <= 4 &&
                        (System.currentTimeMillis() - download.getDate().getTimeInMillis()) < download.getDownloadType().getValidity() * HOUR_MILLIS);
    }
}


