package ro.panzo.secureshares.pojo;

public class User {
    private long id;
    private String username;
    private String role;
    private Company company;

    public User(long id, String username, String role, Company company) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.company = company;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public Company getCompany() {
        return company;
    }
}
