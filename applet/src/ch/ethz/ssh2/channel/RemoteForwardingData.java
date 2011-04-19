package ch.ethz.ssh2.channel;

/**
 * RemoteForwardingData. Data about a requested remote forwarding.
 *
 * @author Christian Plattner, plattner@inf.ethz.ch
 * @version $Id: RemoteForwardingData.java,v 1.1 2006/07/31 14:12:25 cticu Exp $
 */
public class RemoteForwardingData
{
    public String bindAddress;
    public int bindPort;

    String targetAddress;
    int targetPort;
}
