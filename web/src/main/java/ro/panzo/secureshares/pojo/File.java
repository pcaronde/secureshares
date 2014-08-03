package ro.panzo.secureshares.pojo;

import java.util.Calendar;

public class File {
    private long id;
    private User user;
    private String filename;
    private Calendar date;
    private String mongoFileId;

    public File(long id, User user, String filename, Calendar date, String mongoFileId) {
        this.id = id;
        this.user = user;
        this.filename = filename;
        this.date = date;
        this.mongoFileId = mongoFileId;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getFilename() {
        return filename;
    }

    public Calendar getDate() {
        return date;
    }

    public String getMongoFileId() {
        return mongoFileId;
    }
}
