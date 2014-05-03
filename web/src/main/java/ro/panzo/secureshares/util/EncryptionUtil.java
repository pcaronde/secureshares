package ro.panzo.secureshares.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;

public class EncryptionUtil {
    private static EncryptionUtil ourInstance = new EncryptionUtil();
    private Key key = null;

    public static EncryptionUtil getInstance() {
        return ourInstance;
    }

    private EncryptionUtil() {
        byte[] raw = new byte[]{0x01, 0x72, 0x43, 0x3E, 0x1C, 0x7A, 0x55, 0x1A};
        key = new SecretKeySpec(raw, "DES");
    }

    public byte[] encryptDESBytes(byte[] src) throws EncryptionException {
        try{
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(src);
        } catch (Exception ex){
            throw new EncryptionException(ex);
        }
    }

    public byte[] decryptDESBytes(byte[] enc) throws EncryptionException {
        try{
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(enc);
        } catch (Exception ex){
            throw new EncryptionException(ex);
        }
    }

    public String encryptDES(String src) throws EncryptionException {
        try{
            byte[] enc = this.encryptDESBytes(src.getBytes("UTF-8"));
            return Base64.encodeBase64URLSafeString(enc);
        } catch (Exception ex){
            throw new EncryptionException(ex);
        }
    }

    public String decryptDES(String enc) throws EncryptionException {
        try{
            byte[] src = this.decryptDESBytes(Base64.decodeBase64(enc));
            return new String(src, "UTF-8");
        } catch (Exception ex){
            throw new EncryptionException(ex);
        }
    }

    public void encryptDESStream(InputStream in, OutputStream out) throws EncryptionException {
        try {
            byte[] buf = new byte[1024];
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // Bytes written to out will be encrypted
            OutputStream cout = new CipherOutputStream(out, cipher);
            // Read in the cleartext bytes and write to out to encrypt
            int numRead = 0;
            while ((numRead = in.read(buf)) >= 0) {
                cout.write(buf, 0, numRead);
            }
            out.close();
        } catch (Exception ex){
            throw new EncryptionException(ex);
        }
    }

    public void decryptDESStream(InputStream in, OutputStream out) throws EncryptionException {
        try {
            byte[] buf = new byte[1024];
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            // Bytes read from in will be decrypted
            InputStream cin = new CipherInputStream(in, cipher);
            // Read in the decrypted bytes and write the cleartext to out
            int numRead = 0;
            while ((numRead = cin.read(buf)) >= 0) {
                out.write(buf, 0, numRead);
            }
            out.close();
        } catch (Exception ex){
            throw new EncryptionException(ex);
        }
    }

}
