package ro.panzo.secureshares.pojo;

public class DownloadType {
    private long id;
    private String name;
    private int count;
    private int validity;

    public DownloadType(long id, String name, int count, int validity) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.validity = validity;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public int getValidity() {
        return validity;
    }
}
