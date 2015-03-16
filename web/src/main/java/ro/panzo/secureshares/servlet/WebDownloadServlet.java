package ro.panzo.secureshares.servlet;

import org.apache.log4j.Logger;
import ro.panzo.secureshares.db.DBManager;
import ro.panzo.secureshares.mongo.MongoDB;
import ro.panzo.secureshares.pojo.File;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebDownloadServlet extends HttpServlet {

    private final Logger log = Logger.getLogger(Action.class);


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            long fileId = Long.parseLong(request.getParameter("id"));
            File file = DBManager.getInstance().getFileById(fileId);
            if(file != null){
                if(request.getUserPrincipal() != null) {
                    response.setContentType("application/x-unknown");
                    response.setHeader("Content-Disposition", "attachment; filename=" + file.getFilename());
                    try {
                        MongoDB.getInstance().writeToOutputStream(file.getUser().getCompany().getId(), file.getMongoFileId(), response.getOutputStream());
                    } catch (IOException ioex) {
                        log.debug(ioex.getMessage(), ioex);
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
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
