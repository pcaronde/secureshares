package ch.ethz.ssh2.packets;


/**
 * PacketSessionSubsystemRequest.
 *
 * @author Christian Plattner, plattner@inf.ethz.ch
 * @version $Id: PacketSessionSubsystemRequest.java,v 1.1 2006/07/31 14:12:25 cticu Exp $
 */
public class PacketSessionSubsystemRequest
{
    byte[] payload;

    public int recipientChannelID;
    public boolean wantReply;
    public String subsystem;

    public PacketSessionSubsystemRequest(int recipientChannelID, boolean wantReply, String subsystem)
    {
        this.recipientChannelID = recipientChannelID;
        this.wantReply = wantReply;
        this.subsystem = subsystem;
    }

    public byte[] getPayload()
    {
        if (payload == null)
        {
            TypesWriter tw = new TypesWriter();
            tw.writeByte(Packets.SSH_MSG_CHANNEL_REQUEST);
            tw.writeUINT32(recipientChannelID);
            tw.writeString("subsystem");
            tw.writeBoolean(wantReply);
            tw.writeString(subsystem);
            payload = tw.getBytes();
            tw.getBytes(payload);
        }
        return payload;
    }
}
