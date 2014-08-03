package ro.panzo.secureshares.mongo;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;

/**
 * @author Ciprian Ticu
 */
public class MongoDB {

    private Logger log = Logger.getLogger(this.getClass());
    private static MongoDB ourInstance = new MongoDB();
    private MongoClient client;

    public static MongoDB getInstance() {
        return ourInstance;
    }

    private MongoDB() {
        try {
            client = new MongoClient("localhost");
        } catch (UnknownHostException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    private DB getDB() {
        return client.getDB("secure-shares-repository");
    }

    public String saveFile(long companyId, String fileName, String contentType, InputStream inputStream) {
        GridFS gfsFile = new GridFS(getDB(), "company_" + companyId);
        GridFSInputFile file = gfsFile.createFile(inputStream);
        file.setFilename(fileName);
        file.setContentType(contentType);
        file.save();
        return file.getId().toString();
    }

    public boolean delete(long companyId, String id){
        GridFS gfsFile = new GridFS(getDB(), "company_" + companyId);
        gfsFile.remove(new ObjectId(id));
        return true;
    }

    public void writeToOutputStream(long companyId, String id, OutputStream os) throws IOException {
        GridFS gfsFile = new GridFS(getDB(), "company_" + companyId);
        GridFSDBFile file = gfsFile.find(new ObjectId(id));
        file.writeTo(os);
    }
}
