package ro.panzo.secureshares.db;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}
