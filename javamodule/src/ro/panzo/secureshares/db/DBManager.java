package ro.panzo.secureshares.db;

import org.apache.log4j.Logger;
import ro.panzo.secureshares.pojo.DownloadType;
import ro.panzo.secureshares.pojo.User;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DBManager {
    private static DBManager ourInstance = new DBManager();

    public static DBManager getInstance() {
        return ourInstance;
    }

    private DBManager() {
    }

    private final Logger log = Logger.getLogger(DBManager.class);

    protected Connection getConnection()
        throws SQLException, NamingException {
        InitialContext initialcontext = new InitialContext();
        Context context = (Context)initialcontext.lookup("java:/comp/env");
        DataSource datasource = (DataSource)context.lookup("jdbc/secureshares");
        return datasource.getConnection();
    }

    protected void close(Connection connection, Statement statement){
        close(connection, statement, null);
    }

    protected void close(Connection connection, Statement statement, ResultSet resultset)
    {
        try {
            if(connection != null){
                connection.setAutoCommit(true);
            }
        } catch(Exception exception){
            log.error(exception.getMessage(), exception);
        }
        try {
            if(connection != null){
                connection.close();
            }
        } catch(Exception exception){
            log.error(exception.getMessage(), exception);
        }
        try {
            if(statement != null)
                statement.close();
        } catch(Exception exception1) {
            log.error(exception1.getMessage(), exception1);
        }
        try {
            if(resultset != null)
                resultset.close();
        } catch(Exception exception2) {
            log.error(exception2.getMessage(), exception2);
        }
    }

    public List<User> getUsers() throws NamingException, SQLException {
        List<User> result = new LinkedList<User>();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            c = this.getConnection();
            ps = c.prepareStatement("select * from users u, roles r where u.username = r.username");
            rs = ps.executeQuery();
            while(rs.next()){
                result.add(new User(rs.getLong("u.id"), rs.getString("u.username"), rs.getString("r.rolename")));
            }
        } finally {
            close(c, ps, rs);
        }
        return result;
    }

    public User getUserById(Long id) throws NamingException, SQLException {
        User user = null;
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            c = this.getConnection();
            ps = c.prepareStatement("select * from users u, roles r where u.username = r.username and u.id = ?");
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                user = new User(rs.getLong("u.id"), rs.getString("u.username"), rs.getString("r.rolename"));
            }
        } finally {
            close(c, ps, rs);
        }
        return user;
    }

    public boolean insertUser(String username, String password) throws NamingException, SQLException {
        boolean result = false;
        Connection c = null;
        PreparedStatement psUsers = null;
        PreparedStatement psRoles = null;
        try{
            c = this.getConnection();
            c.setAutoCommit(false);
            psUsers = c.prepareStatement("insert into users values (null, ?, md5(?))");
            psUsers.setString(1, username);
            psUsers.setString(2, password);
            result = psUsers.executeUpdate() == 1;
            if(result){
                psRoles = c.prepareStatement("insert into roles values (null, ?, ?)");
                psRoles.setString(1, username);
                psRoles.setString(2, "user");
                result = psRoles.executeUpdate() == 1;
            }
            if(result){
                c.commit();
            } else {
                c.rollback();
            }
        } catch (SQLException ex){
            try{
                if(c != null){
                    c.rollback();
                }
            } catch(Exception ex1){
                log.error(ex1.getMessage(), ex);
            }
            throw ex;
        } finally {
            close(null, psRoles);
            close(c, psUsers);
        }
        return result;
    }

    public boolean updateUser(long id, String password) throws NamingException, SQLException {
        boolean result = false;
        Connection c = null;
        PreparedStatement ps = null;
        try{
            c = this.getConnection();
            ps = c.prepareStatement("update users set password = md5(?) where id = ?");
            ps.setString(1, password);
            ps.setLong(2, id);
            result = ps.executeUpdate() == 1;
        } finally {
            close(c, ps);
        }
        return result;
    }

    public boolean deleteUser(long id) throws NamingException, SQLException {
        boolean result = false;
        Connection c = null;
        PreparedStatement psUsers = null;
        PreparedStatement psRoles = null;
        try{
            c = this.getConnection();
            c.setAutoCommit(false);
            psRoles = c.prepareStatement("delete from roles where username = (select u.username from users u where u.id = ?)");
            psRoles.setLong(1, id);
            result = psRoles.executeUpdate() == 1;
            if(result){
                psUsers = c.prepareStatement("delete from users where id = ?");
                psUsers.setLong(1, id);
                result = psUsers.executeUpdate() == 1;
            }
            if(result){
                c.commit();
            } else {
                c.rollback();
            }
        } catch (SQLException ex){
            try{
                if(c != null){
                    c.rollback();
                }
            } catch(Exception ex1){
                log.error(ex1.getMessage(), ex);
            }
            throw ex;
        } finally {
            close(null, psRoles);
            close(c, psUsers);
        }
        return result;
    }

    public List<DownloadType> getDownloadTypes() throws NamingException, SQLException {
        List<DownloadType> result = new LinkedList<DownloadType>();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            c = this.getConnection();
            ps = c.prepareStatement("select * from downloadTypes order by id");
            rs = ps.executeQuery();
            while(rs.next()){
                result.add(new DownloadType(rs.getLong("id"), rs.getString("name"), rs.getInt("count"), rs.getInt("validity")));
            }
        } finally {
            close(c, ps, rs);
        }
        return result;
    }

}
