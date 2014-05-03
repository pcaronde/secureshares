package ro.panzo.secureshares.pojo;

import java.util.Calendar;

public class File {
    private long id;
    private User user;
    private String filename;
    private Calendar date;

    public File(long id, User user, String filename, Calendar date) {
        this.id = id;
        this.user = user;
        this.filename = filename;
        this.date = date;
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
}
