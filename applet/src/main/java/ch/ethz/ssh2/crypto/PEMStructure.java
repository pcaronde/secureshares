package ch.ethz.ssh2.crypto;

/**
 * Parsed PEM structure.
 *
 * @author Christian Plattner, plattner@inf.ethz.ch
 * @version $Id: PEMStructure.java,v 1.1 2006/07/31 14:12:25 cticu Exp $
 */

public class PEMStructure
{
    int pemType;
    String dekInfo[];
    String procType[];
    byte[] data;
}