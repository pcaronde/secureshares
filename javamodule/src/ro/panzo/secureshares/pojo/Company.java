package ro.panzo.secureshares.pojo;

public class Company {
    private long id;
    private String name;
    private String subrepositoryName;

    public Company(long id, String name, String subrepository) {
        this.id = id;
        this.name = name;
        this.subrepositoryName = subrepository;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSubrepositoryName() {
        return subrepositoryName;
    }
}
