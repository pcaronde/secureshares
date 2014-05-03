package ch.ethz.ssh2.transport;

import java.io.IOException;

/**
 * MessageHandler.
 *
 * @author Christian Plattner, plattner@inf.ethz.ch
 * @version $Id: MessageHandler.java,v 1.1 2006/07/31 14:12:25 cticu Exp $
 */
public interface MessageHandler
{
    public void handleMessage(byte[] msg, int msglen) throws IOException;
}
