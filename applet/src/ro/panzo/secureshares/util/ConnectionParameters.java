package ro.panzo.secureshares.util;

public class ConnectionParameters {
    private String host;
    private int port;
    private String user;
    private String password;
    private String repository;

    public ConnectionParameters(String host, int port, String user, String password, String repository) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.repository = repository;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getRepository() {
        return repository;
    }
}
