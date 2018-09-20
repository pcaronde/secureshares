package ro.panzo.secureshares.db;

import org.apache.log4j.Logger;
import ro.panzo.secureshares.pojo.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class DBManager {
    public static DBManager ourInstance = new DBManager();

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

    public List<User> getUsers(long companyId) throws NamingException, SQLException {
        List<User> result = new LinkedList<User>();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            c = this.getConnection();
            ps = c.prepareStatement("select u.*, r.*, c.* from users u, roles r, companies c where c.id = u.companyId and u.username = r.username and c.id = ?");
            ps.setLong(1, companyId);
            rs = ps.executeQuery();
            while(rs.next()){
                result.add(getUserFromResultSet(rs));
            }
        } finally {
            close(c, ps, rs);
        }
        return result;
    }

    public List<User> getUsersByRole(String rolename) throws NamingException, SQLException {
        List<User> result = new LinkedList<User>();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            c = this.getConnection();
            ps = c.prepareStatement("select u.*, r.*, c.* from users u, roles r, companies c where c.id = u.companyId and u.username = r.username and r.rolename = ?");
            ps.setString(1, rolename);
            rs = ps.executeQuery();
            while(rs.next()){
                result.add(getUserFromResultSet(rs));
            }
        } finally {
            close(c, ps, rs);
        }
        return result;
    }

    public User getUserFromResultSet(ResultSet rs) throws SQLException {
        return new User(rs.getLong("u.id"), rs.getString("u.username"), rs.getString("r.rolename"),
                new Company(rs.getLong("c.id"), rs.getString("c.name"), rs.getString("c.subrepositoryname")));
    }

    public User getUserById(Long id) throws NamingException, SQLException {
        User user = null;
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            c = this.getConnection();
            ps = c.prepareStatement("select u.*, r.*, c.* from users u, roles r, companies c where c.id = u.companyId and u.username = r.username and u.id = ?");
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                user = getUserFromResultSet(rs);
            }
        } finally {
            close(c, ps, rs);
        }
        return user;
    }

    public User getUserByUsername(String username) throws NamingException, SQLException {
        User user = null;
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            c = this.getConnection();
            ps = c.prepareStatement("select u.*, r.*, c.* from users u, roles r, companies c where c.id = u.companyId and u.username = r.username and u.username = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            if(rs.next()){
                user = getUserFromResultSet(rs);
            }
        } finally {
            close(c, ps, rs);
        }
        return user;
    }

    public boolean insertUser(String username, String password, long companyId, String role) throws NamingException, SQLException {
        boolean result = false;
        Connection c = null;
        PreparedStatement psUsers = null;
        PreparedStatement psRoles = null;
        try{
            c = this.getConnection();
            c.setAutoCommit(false);
            psUsers = c.prepareStatement("insert into users values (null, ?, md5(?), ?)");
            psUsers.setString(1, username);
            psUsers.setString(2, password);
            psUsers.setLong(3, companyId);
            result = psUsers.executeUpdate() == 1;
            if(result){
                psRoles = c.prepareStatement("insert into roles values (null, ?, ?)");
                psRoles.setString(1, username);
                psRoles.setString(2, role);
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

    public boolean updateRole(long userId, String rolename) throws NamingException, SQLException {
        boolean result = false;
        Connection c = null;
        PreparedStatement ps = null;
        try{
            c = this.getConnection();
            ps = c.prepareStatement("update roles set rolename = ? where username = (select username from users where id = ?)");
            ps.setString(1, rolename);
            ps.setLong(2, userId);
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

    /**
     * Download types are one-time, one day, one week, etc. Currently to change this you must edit the DB directly.
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public List<DownloadType> getDownloadTypes() throws NamingException, SQLException {
        List<DownloadType> result = new LinkedList<DownloadType>();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            c = this.getConnection();
            ps = c.prepareStatement("select dt.* from downloadTypes dt order by dt.id");
            rs = ps.executeQuery();
            while(rs.next()){
                result.add(getDownloadTypeFromResultSet(rs));
            }
        } finally {
            close(c, ps, rs);
        }
        return result;
    }

    public DownloadType getDownloadTypeById(long id) throws NamingException, SQLException {
        DownloadType result = null;
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            c = this.getConnection();
            ps = c.prepareStatement("select dt.* from downloadTypes dt where dt.id = ?");
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                result = getDownloadTypeFromResultSet(rs);
            }
        } finally {
            close(c, ps, rs);
        }
        return result;
    }

    public DownloadType getDownloadTypeFromResultSet(ResultSet rs) throws SQLException {
        return new DownloadType(rs.getLong("dt.id"), rs.getString("dt.name"), rs.getInt("dt.count"), rs.getInt("dt.validity"));
    }

    private Calendar convertToCalendar(Timestamp t){
        if(t == null){
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(t.getTime());
        return cal;
    }

    public List<File> getFiles() throws NamingException, SQLException {
        List<File> result = new LinkedList<File>();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            c = this.getConnection();
            ps = c.prepareStatement("select f.*, u.*, r.*, c.* from companies c, files f, users u, roles r " +
                    "where c.id = u.companyId and f.userId = u.id and u.username = r.username order by f.date desc");
            rs = ps.executeQuery();
            while(rs.next()){
                result.add(getFileFromResultSet(rs));
            }
        } finally {
            close(c, ps, rs);
        }
        return result;
    }

    public long insertFile(String username, String filename, String mongoFileId) throws NamingException, SQLException {
        long fileId = -1;
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet generatedKeys; // = null;
        try{
            c = this.getConnection();
            ps = c.prepareStatement("insert into files values (null, (select u.id from users u where u.username = ?), ?, now(), ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, username);
            ps.setString(2, filename);
            ps.setString(3, mongoFileId);
            boolean result = ps.executeUpdate() == 1;
            if(result){
                generatedKeys = ps.getGeneratedKeys();
                if(generatedKeys.next()){
                    fileId = generatedKeys.getLong(1);
                }
            }
        } finally {
            close(c, ps);
        }
        return fileId;
    }

    public boolean updateFile(String username, String filename, String mongoFileId) throws NamingException, SQLException {
        boolean result = false;
        Connection c = null;
        PreparedStatement ps = null;
        try{
            c = this.getConnection();
            ps = c.prepareStatement("update files set userId = (select u.id from users u where u.username = ?), " +
                    "date = now(), mongofileid = ? where filename = ?");
            ps.setString(1, username);
            ps.setString(2, mongoFileId);
            ps.setString(3, filename);
            result = ps.executeUpdate() == 1;
        } finally {
            close(c, ps);
        }
        return result;
    }

    public boolean deleteFile(long id) throws NamingException, SQLException {
        boolean result = false;
        Connection c = null;
        PreparedStatement ps = null;
        try{
            c = this.getConnection();
            ps = c.prepareStatement("delete from files where id = ?");
            ps.setLong(1, id);
            result = ps.executeUpdate() == 1;
        } finally {
            close(c, ps);
        }
        return result;
    }


    public File getFileByName(String filename) throws NamingException, SQLException {
        File result = null;
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            c = this.getConnection();
            ps = c.prepareStatement("select f.*, u.*, r.*, c.* from files f, users u, roles r, companies c " +
                    "where c.id = u.companyId and f.userId = u.id and u.username = r.username and f.filename = ?");
            ps.setString(1, filename);
            rs = ps.executeQuery();
            if(rs.next()){
                result = getFileFromResultSet(rs);
            }
        } finally {
            close(c, ps, rs);
        }
        return result;
    }

    public File getFileById(long id) throws NamingException, SQLException {
        File result = null;
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            c = this.getConnection();
            ps = c.prepareStatement("select f.*, u.*, r.*, c.* from companies c, files f, users u, roles r " +
                    "where c.id = u.companyId and f.userId = u.id and u.username = r.username and f.id = ?");
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                result = getFileFromResultSet(rs);
            }
        } finally {
            close(c, ps, rs);
        }
        return result;
    }

    public File getFileFromResultSet(ResultSet rs) throws SQLException {
        return new File(rs.getLong("id"), this.getUserFromResultSet(rs), rs.getString("filename"),
                this.convertToCalendar(rs.getTimestamp("date")), rs.getString("mongofileid"));
    }

    public Download getDownloadFromResultSet(ResultSet rs) throws SQLException {
        return new Download(rs.getLong("d.id"), this.getFileFromResultSet(rs), this.getDownloadTypeFromResultSet(rs),
                this.convertToCalendar(rs.getTimestamp("d.date")), rs.getInt("d.count"));
    }

    public long insertDownload(long fileId, long downloadTypeId) throws NamingException, SQLException {
        long downloadId = -1;
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet generatedKeys = null;
        try{
            c = this.getConnection();
            ps = c.prepareStatement("insert into downloads values (null, ?, ?, now(), 0)", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setLong(1, fileId);
            ps.setLong(2, downloadTypeId);
            boolean result = ps.executeUpdate() == 1;
            if(result){
                generatedKeys = ps.getGeneratedKeys();
                if(generatedKeys.next()){
                    downloadId = generatedKeys.getLong(1);
                }
            }
        } finally {
            close(c, ps);
        }
        return downloadId;
    }

    public Download getDownloadById(long id) throws NamingException, SQLException {
        Download result = null;
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            c = this.getConnection();
            ps = c.prepareStatement("select f.*, u.*, r.*, d.*, dt.*, c.* from companies c, files f, users u, roles r, downloads d, downloadTypes dt " +
                    "where c.id = u.companyId and f.userId = u.id and u.username = r.username and f.id = d.fileId and dt.id = d.downLoadTypeId and d.id = ?");
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                result = getDownloadFromResultSet(rs);
            }
        } finally {
            close(c, ps, rs);
        }
        return result;
    }

    public boolean updateDownloadCount(long id, int count) throws NamingException, SQLException {
        boolean result = false;
        Connection c = null;
        PreparedStatement ps = null;
        try{
            c = this.getConnection();
            ps = c.prepareStatement("update downloads set count = ? where id = ?");
            ps.setInt(1, count);
            ps.setLong(2, id);
            result = ps.executeUpdate() == 1;
        } finally {
            close(c, ps);
        }
        return result;
    }

    public String getI18NFor(String lang, String key) throws NamingException, SQLException {
        String result = key;
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            c = this.getConnection();
            ps = c.prepareStatement("select `value` from i18n where lang = ? and `key` = ?");
            ps.setString(1, lang);
            ps.setString(2, key);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getString("value");
            }
        } finally {
            close(c, ps, rs);
        }
        return result;
    }

    public boolean isLanguageSupported(String language) throws NamingException, SQLException {
        boolean result = false;
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            c = this.getConnection();
            ps = c.prepareStatement("select count(*) as valid from i18n where lang = ?");
            ps.setString(1, language);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt("valid") > 0;
            }
        } finally {
            close(c, ps, rs);
        }
        return result;
    }
}
