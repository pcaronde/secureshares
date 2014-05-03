package ch.ethz.ssh2.signature;

import java.math.BigInteger;


/**
 * RSASignature.
 *
 * @author Christian Plattner, plattner@inf.ethz.ch
 * @version $Id: RSASignature.java,v 1.1 2006/07/31 14:12:25 cticu Exp $
 */

public class RSASignature
{
    BigInteger s;

    public BigInteger getS()
    {
        return s;
    }

    public RSASignature(BigInteger s)
    {
        this.s = s;
    }
}