package ch.ethz.ssh2.crypto.cipher;

/**
 * BlockCipher.
 *
 * @author Christian Plattner, plattner@inf.ethz.ch
 * @version $Id: BlockCipher.java,v 1.1 2006/07/31 14:12:25 cticu Exp $
 */
public interface BlockCipher
{
    public void init(boolean forEncryption, byte[] key);

    public int getBlockSize();

    public void transformBlock(byte[] src, int srcoff, byte[] dst, int dstoff);
}
