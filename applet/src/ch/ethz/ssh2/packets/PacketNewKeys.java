package ch.ethz.ssh2.packets;

import java.io.IOException;

/**
 * PacketNewKeys.
 *
 * @author Christian Plattner, plattner@inf.ethz.ch
 * @version $Id: PacketNewKeys.java,v 1.1 2006/07/31 14:12:25 cticu Exp $
 */
public class PacketNewKeys
{
    byte[] payload;

    public PacketNewKeys()
    {
    }

    public PacketNewKeys(byte payload[], int off, int len) throws IOException
    {
        this.payload = new byte[len];
        System.arraycopy(payload, off, this.payload, 0, len);

        TypesReader tr = new TypesReader(payload, off, len);

        int packet_type = tr.readByte();

        if (packet_type != Packets.SSH_MSG_NEWKEYS)
            throw new IOException("This is not a SSH_MSG_NEWKEYS! ("
                    + packet_type + ")");

        if (tr.remain() != 0)
            throw new IOException("Padding in SSH_MSG_NEWKEYS packet!");
    }

    public byte[] getPayload()
    {
        if (payload == null)
        {
            TypesWriter tw = new TypesWriter();
            tw.writeByte(Packets.SSH_MSG_NEWKEYS);
            payload = tw.getBytes();
        }
        return payload;
    }
}
