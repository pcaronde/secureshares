package ro.panzo.secureshares.servlet;

import org.apache.log4j.Logger;
import ro.panzo.secureshares.util.Util;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Key;

public class AppletServlet extends HttpServlet {

    private final Logger log = Logger.getLogger(Action.class);
    private final byte[] salt = {
                (byte)0xc7, (byte)0x73, (byte)0x21, (byte)0x8c,
                (byte)0x7e, (byte)0xc8, (byte)0xee, (byte)0x99
            };

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            String host = Util.getInstance().getEnviromentValue("SFTP_HOST");
            int port = Integer.parseInt(Util.getInstance().getEnviromentValue("SFTP_PORT"));
            String user = Util.getInstance().getEnviromentValue("SFTP_USER");
            String password = Util.getInstance().getEnviromentValue("SFTP_PASSWORD");
            String repository = Util.getInstance().getEnviromentValue("SFTP_REPOSITORY");

            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String encPassword = br.readLine();
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            Key key = factory.generateSecret(new PBEKeySpec(encPassword.toCharArray()));
            Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
            int count = 20;
            PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, count);
            cipher.init(Cipher.ENCRYPT_MODE, key, pbeParamSpec);
            CipherOutputStream out = new CipherOutputStream(response.getOutputStream(), cipher);
            DataOutputStream dout = new DataOutputStream(out);
            dout.writeUTF(host);
            dout.writeInt(port);
            dout.writeUTF(user);
            dout.writeUTF(password);
            dout.writeUTF(repository);
            dout.close();
            out.close();
        } catch (Exception ex){
           log.debug(ex.getMessage(), ex);
        }
    }
}
