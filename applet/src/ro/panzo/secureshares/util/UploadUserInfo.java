package ro.panzo.secureshares.util;

import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

public class UploadUserInfo implements UserInfo, UIKeyboardInteractive
{
    private String password;

    public UploadUserInfo(String password)
    {
        this.password = password;
    }

    public String getPassphrase()
    {
        return null;
    }

    public String getPassword()
    {
        return this.password;
    }

    public boolean promptPassword(String msg)
    {
        return true;
    }

    public boolean promptPassphrase(String msg)
    {
        return true;
    }

    public boolean promptYesNo(String msg)
    {
        return true;
    }

    public void showMessage(String msg)
    {
    }

    public String[] promptKeyboardInteractive(String destination, String name, String instruction, String[] prompt, boolean[] echo) {
        return new String[]{password};
    }
}
