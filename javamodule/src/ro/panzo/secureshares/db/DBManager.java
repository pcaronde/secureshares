package ro.panzo.secureshares.db;

import org.apache.log4j.Logger;
import ro.panzo.secureshares.pojo.User;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.transform.Result;
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

    protected void close(Connection connection, Statement statement, ResultSet resultset)
    {
        try {
            if(connection != null)
                connection.close();
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
}
