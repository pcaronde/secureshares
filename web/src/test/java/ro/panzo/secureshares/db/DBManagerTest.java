package ro.panzo.secureshares.db;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import ro.panzo.secureshares.pojo.*;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by petercaron on 27.05.18.
 */
 class DBManagerTest {
    @Mock
    DBManager ourInstance;
    @Mock
    Logger log;
    @InjectMocks
    DBManager dBManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetConnection() {
        try {
            Connection result = dBManager.getConnection();
            Assertions.assertEquals(null, result);
        } catch (SQLException e) {
        e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void testClose() {
        dBManager.close(null, null);
    }

    @Test
    void testClose2() {
        dBManager.close(null, null, null);
    }

    @Test
    void testGetUsers() {

        try {
            when(ourInstance.getConnection()).thenReturn(null);
           // when(ourInstance.getUserFromResultSet(any())).thenReturn(new User(0L, "username", "role", new Company(0L, "name", "subrepository")));

            List<User> result = dBManager.getUsers(0L);
            Assertions.assertEquals(Arrays.<User>asList(new User(0L, "username", "role", new Company(0L, "name", "subrepository"))), result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetUsersByRole() {
        try {
            when(ourInstance.getConnection()).thenReturn(null);
            //when(ourInstance.getUserFromResultSet(any())).thenReturn(new User(0L, "username", "role", new Company(0L, "name", "subrepository")));

            List<User> result = dBManager.getUsersByRole("rolename");
            Assertions.assertEquals(Arrays.<User>asList(new User(0L, "username", "role", new Company(0L, "name", "subrepository"))), result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetUserById() {
        try {
            when(ourInstance.getConnection()).thenReturn(null);
            //when(ourInstance.getUserFromResultSet(any())).thenReturn(new User(0L, "username", "role", new Company(0L, "name", "subrepository")));

            User result = dBManager.getUserById(Long.valueOf(1));
            Assertions.assertEquals(new User(0L, "username", "role", new Company(0L, "name", "subrepository")), result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetUserByUsername() {
        try {
            when(ourInstance.getConnection()).thenReturn(null);
            //when(ourInstance.getUserFromResultSet(any())).thenReturn(new User(0L, "username", "role", new Company(0L, "name", "subrepository")));

            User result = dBManager.getUserByUsername("username");
            Assertions.assertEquals(new User(0L, "username", "role", new Company(0L, "name", "subrepository")), result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testInsertUser() {
        try {
            when(ourInstance.getConnection()).thenReturn(null);

            boolean result = dBManager.insertUser("username", "password", 0L, "role");
            Assertions.assertEquals(true, result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testUpdateUser() {
        try {
            when(ourInstance.getConnection()).thenReturn(null);

            boolean result = dBManager.updateUser(0L, "password");
            Assertions.assertEquals(true, result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testUpdateRole() {
        try {
            when(ourInstance.getConnection()).thenReturn(null);

            boolean result = dBManager.updateRole(0L, "rolename");
            Assertions.assertEquals(true, result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testDeleteUser() {
        try {
            when(ourInstance.getConnection()).thenReturn(null);

            boolean result = dBManager.deleteUser(0L);
            Assertions.assertEquals(true, result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetDownloadTypes() {
        try {
            when(ourInstance.getConnection()).thenReturn(null);
            //when(ourInstance.getDownloadTypeFromResultSet(any())).thenReturn(new DownloadType(0L, "name", 0, 0));

            List<DownloadType> result = dBManager.getDownloadTypes();
            Assertions.assertEquals(Arrays.<DownloadType>asList(new DownloadType(0L, "name", 0, 0)), result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetDownloadTypeById() {

        try {
            when(ourInstance.getConnection()).thenReturn(null);
            //when(ourInstance.getDownloadTypeFromResultSet(any())).thenReturn(new DownloadType(0L, "name", 0, 0));

            DownloadType result = dBManager.getDownloadTypeById(0L);
            Assertions.assertEquals(new DownloadType(0L, "name", 0, 0), result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetFiles() {
        try {
            when(ourInstance.getConnection()).thenReturn(null);
            //when(ourInstance.getUserFromResultSet(any())).thenReturn(new User(0L, "username", "role", new Company(0L, "name", "subrepository")));
           // when(ourInstance.convertToCalendar(any())).thenReturn(null);
            //when(ourInstance.getFileFromResultSet(any())).thenReturn(new File(0L, new User(0L, "username", "role", new Company(0L, "name", "subrepository")), "filename", null, "mongoFileId"));

            List<File> result = dBManager.getFiles();
            Assertions.assertEquals(Arrays.<File>asList(new File(0L, new User(0L, "username", "role", new Company(0L, "name", "subrepository")), "filename", null, "mongoFileId")), result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testInsertFile() {
        try {
            when(ourInstance.getConnection()).thenReturn(null);

            long result = dBManager.insertFile("username", "filename", "mongoFileId");
            Assertions.assertEquals(0L, result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testUpdateFile() {
        try {
            when(ourInstance.getConnection()).thenReturn(null);

            boolean result = dBManager.updateFile("username", "filename", "mongoFileId");
            Assertions.assertEquals(true, result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testDeleteFile() {
        try {
            when(ourInstance.getConnection()).thenReturn(null);

            boolean result = dBManager.deleteFile(0L);
            Assertions.assertEquals(true, result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetFileByName() {
        try {
            when(ourInstance.getConnection()).thenReturn(null);
            //when(ourInstance.getUserFromResultSet(any())).thenReturn(new User(0L, "username", "role", new Company(0L, "name", "subrepository")));
            //when(ourInstance.convertToCalendar(any())).thenReturn(null);
            //when(ourInstance.getFileFromResultSet(any())).thenReturn(new File(0L, new User(0L, "username", "role", new Company(0L, "name", "subrepository")), "filename", null, "mongoFileId"));

            File result = dBManager.getFileByName("filename");
            Assertions.assertEquals(new File(0L, new User(0L, "username", "role", new Company(0L, "name", "subrepository")), "filename", null, "mongoFileId"), result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetFileById() {
        try {
            when(ourInstance.getConnection()).thenReturn(null);
            //when(ourInstance.getUserFromResultSet(any())).thenReturn(new User(0L, "username", "role", new Company(0L, "name", "subrepository")));
            //when(ourInstance.convertToCalendar(any())).thenReturn(null);
            //when(ourInstance.getFileFromResultSet(any())).thenReturn(new File(0L, new User(0L, "username", "role", new Company(0L, "name", "subrepository")), "filename", null, "mongoFileId"));

            File result = dBManager.getFileById(0L);
            Assertions.assertEquals(new File(0L, new User(0L, "username", "role", new Company(0L, "name", "subrepository")), "filename", null, "mongoFileId"), result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testInsertDownload() {
        try {
            when(ourInstance.getConnection()).thenReturn(null);

            long result = dBManager.insertDownload(0L, 0L);
            Assertions.assertEquals(0L, result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
/**
    @Test
    void testGetDownloadById() {
        try {
            when(ourInstance.getConnection()).thenReturn(null);
            when(ourInstance.getUserFromResultSet(any())).thenReturn(new User(0L, "username", "role", new Company(0L, "name", "subrepository")));
            when(ourInstance.getDownloadTypeFromResultSet(any())).thenReturn(new DownloadType(0L, "name", 0, 0));
            //when(ourInstance.convertToCalendar(any())).thenReturn(null);
            when(ourInstance.getFileFromResultSet(any())).thenReturn(new File(0L, new User(0L, "username", "role", new Company(0L, "name", "subrepository")), "filename", null, "mongoFileId"));
            when(ourInstance.getDownloadFromResultSet(any())).thenReturn(new Download(0L, new File(0L, new User(0L, "username", "role", new Company(0L, "name", "subrepository")), "filename", null, "mongoFileId"), new DownloadType(0L, "name", 0, 0), null, 0));

            Download result = dBManager.getDownloadById(0L);
            Assertions.assertEquals(new Download(0L, new File(0L, new User(0L, "username", "role", new Company(0L, "name", "subrepository")), "filename", null, "mongoFileId"), new DownloadType(0L, "name", 0, 0), null, 0), result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
*/
    @Test
    void testUpdateDownloadCount() {
        try {
            when(ourInstance.getConnection()).thenReturn(null);

            boolean result = dBManager.updateDownloadCount(0L, 0);
            Assertions.assertEquals(true, result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetI18NFor() {
        try {
            when(ourInstance.getConnection()).thenReturn(null);

            String result = dBManager.getI18NFor("lang", "key");
            Assertions.assertEquals("replaceMeWithExpectedResult", result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testIsLanguageSupported() {
        try {
            when(ourInstance.getConnection()).thenReturn(null);

            boolean result = dBManager.isLanguageSupported("language");
            Assertions.assertEquals(true, result);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme