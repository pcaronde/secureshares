package ro.panzo.secureshares.util;

import com.jcraft.jsch.*;

import java.util.Vector;

public class SFTPPutTask {
    private String host;
    private int port;
    private String user;
    private String password;
    private SftpProgressMonitor monitor;
    private Channel channel;

    public SFTPPutTask(String host, int port, String user, String password, SftpProgressMonitor monitor) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.monitor = monitor;
    }

    public void execute(final String localPath, final String localFile, final String remoteFolder) throws JSchException, SftpException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(user, host, port);
        UserInfo ui = new UploadUserInfo(password);
        session.setUserInfo(ui);
        session.connect();

        channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp c = (ChannelSftp)channel;
        Vector v = c.ls(".");
        ChannelSftp.LsEntry entry;
        boolean hasDir = false;
        for(int i = 0; i < v.size(); i++){
            entry = (ChannelSftp.LsEntry)v.get(i);
            if(entry.getAttrs().isDir() && remoteFolder.equals(entry.getFilename()))
            {
                hasDir = true;
            }
        }
        if(!hasDir){
            c.mkdir(remoteFolder);
        }
        c.cd(remoteFolder);
        c.lcd(localPath);
        c.put(localFile, ".", monitor , ChannelSftp.RESUME);
        c.disconnect();
        channel.disconnect();
        session.disconnect();
    }

    public void cancel(){
        if(channel != null){
            try{
                channel.disconnect();
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
