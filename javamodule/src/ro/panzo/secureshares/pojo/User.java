package ro.panzo.secureshares.pojo;

public class User {
    private long id;
    private String username;
    private String role;

    public User(long id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
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
}
