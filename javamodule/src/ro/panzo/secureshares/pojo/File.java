package ro.panzo.secureshares.pojo;

import java.util.Calendar;

public class File {
    private long id;
    private User user;
    private DownloadType downloadType;
    private String filename;
    private String savedname;
    private String contentType;
    private Calendar date;
    private int downloadCount;

    public File(long id, User user, DownloadType downloadType, String filename, String savedname, String contentType, Calendar date, int downloadCount) {
        this.id = id;
        this.user = user;
        this.downloadType = downloadType;
        this.filename = filename;
        this.savedname = savedname;
        this.contentType = contentType;
        this.date = date;
        this.downloadCount = downloadCount;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public DownloadType getDownloadType() {
        return downloadType;
    }

    public String getFilename() {
        return filename;
    }

    public String getSavedname() {
        return savedname;
    }

    public String getContentType() {
        return contentType;
    }

    public Calendar getDate() {
        return date;
    }

    public int getDownloadCount() {
        return downloadCount;
    }
}
