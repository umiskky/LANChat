package org.umiskky.model.pcap.packet;

import org.pcap4j.packet.AbstractPacket;

import java.util.List;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/15
 */
public class InviteToGroupPacket extends AbstractPacket{

    private static final long serialVersionUID = 7520078455841482662L;

    @Override
    public InviteToGroupPacket.Builder getBuilder() {
        return null;
    }


    public static final class Builder extends AbstractPacket.AbstractBuilder {


        @Override
        public InviteToGroupPacket build() {
            return null;
        }
    }

    public static final class InviteToGroupHeader extends AbstractPacket.AbstractHeader {

        @Override
        protected List<byte[]> getRawFields() {
            return null;
        }
    }
}