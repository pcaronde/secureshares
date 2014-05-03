package ro.panzo.secureshares.pojo;

import java.util.Calendar;

public class Download {
    private long id;
    private File file;
    private DownloadType downloadType;
    private Calendar date;
    private int count;

    public Download(long id, File file, DownloadType downloadType, Calendar date, int count) {
        this.id = id;
        this.file = file;
        this.downloadType = downloadType;
        this.date = date;
        this.count = count;
    }

    public long getId() {
        return id;
    }

    public File getFile() {
        return file;
    }

    public DownloadType getDownloadType() {
        return downloadType;
    }

    public Calendar getDate() {
        return date;
    }

    public int getCount() {
        return count;
    }
}
