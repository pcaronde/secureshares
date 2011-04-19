package ch.ethz.ssh2.channel;

/**
 * X11ServerData. Data regarding an x11 forwarding target.
 *
 * @author Christian Plattner, plattner@inf.ethz.ch
 * @version $Id: X11ServerData.java,v 1.1 2006/07/31 14:12:25 cticu Exp $
 */
public class X11ServerData
{
    public String hostname;
    public int port;
    public byte[] x11_magic_cookie; /* not the remote (fake) one, the local (real) one */
}
