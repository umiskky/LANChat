package org.umiskky.service.task.pcap.parsetask;

import com.alibaba.fastjson.JSON;
import org.pcap4j.packet.EthernetPacket;
import org.slf4j.Logger;
import org.umiskky.factories.ServiceDispatcher;
import org.umiskky.model.dao.FriendDAO;
import org.umiskky.model.dao.UserDAO;
import org.umiskky.model.entity.Friend;
import org.umiskky.model.entity.User;
import org.umiskky.service.pcaplib.networkcards.NetworkCard;
import org.umiskky.service.pcaplib.packet.HelloPacket;
import org.umiskky.service.pcaplib.packet.namednumber.HelloPacketTypeCode;
import org.umiskky.service.pcaplib.utils.IpAddressTools;
import org.umiskky.service.task.InitTask;
import org.umiskky.service.task.pcap.sendtask.SendHelloPacketTask;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/21
 */
public class ParseHelloPacketTask implements Runnable{
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ParseHelloPacketTask.class);
    private final EthernetPacket ethernetPacket;
    private final HelloPacket packet;
    private final NetworkCard networkCard;
    private final HelloPacketTypeCode typeCode;

    public ParseHelloPacketTask(EthernetPacket ethernetPacket, NetworkCard networkCard) {
        this.ethernetPacket = ethernetPacket;
        this.packet = (HelloPacket) ethernetPacket.getPayload();
        this.networkCard = networkCard;
        this.typeCode = packet.getHeader().getTypeCode();
    }

    public ParseHelloPacketTask(EthernetPacket ethernetPacket) {
        this.ethernetPacket = ethernetPacket;
        this.packet = (HelloPacket) ethernetPacket.getPayload();
        this.networkCard = InitTask.networkCardSelected;
        this.typeCode = packet.getHeader().getTypeCode();
    }

    @Override
    public void run() {
        Thread.currentThread().setName(Thread.currentThread().getName() + "(Parse_HelloPacket_Thread)");
        User user = new User();
        user.setLinkLayerAddress(ethernetPacket.getHeader().getSrcAddr().toString());
        user.setUuid(packet.getHeader().getUuid().getUuid());
        user.setIpAddress(IpAddressTools.ipAddressToString(packet.getHeader().getServerAddress()));
        user.setServerPort(packet.getHeader().getServerPort().valueAsInt());
        user.setAvatarId(packet.getHeader().getAvatarId().getAvatarId());
        user.setLastUpdated(Instant.now().toEpochMilli());
        user.setStatus(Boolean.TRUE);
        user.setNickname(JSON.parseArray(new String(packet.getPayload().getRawData(), StandardCharsets.UTF_8)).getJSONObject(0).getString("nickname"));

        //ToDo: 测试性代码，需要重写...
        if(user.getNickname() != null && !user.getNickname().isEmpty()){
            UserDAO.updateUser(user);
            log.debug("Update user.\n" + packet);
        }

        Friend friend = FriendDAO.getFriendById(packet.getHeader().getUuid().getUuid());
        if(friend != null){
            friend.setNickname(user.getNickname());
            friend.setAvatarId(user.getAvatarId());
            friend.setIpAddress(user.getIpAddress());
            friend.setServerPort(user.getServerPort());
            friend.setStatus(Boolean.TRUE);
            friend.setLastUpdated(Instant.now().toEpochMilli());
            FriendDAO.updateFriend(friend);
        }

        if(HelloPacketTypeCode.HELLO.equals(typeCode)){
            SendHelloPacketTask sendHelloPacketTask = new SendHelloPacketTask(networkCard, ethernetPacket.getHeader().getSrcAddr(), HelloPacketTypeCode.HELLOACK);
            ServiceDispatcher.submitTask(sendHelloPacketTask);
            log.debug("Receive hello packet.\n" + packet);
        }else if(HelloPacketTypeCode.HELLOREQUEST.equals(typeCode)){
            SendHelloPacketTask sendHelloPacketTask = new SendHelloPacketTask(networkCard, ethernetPacket.getHeader().getSrcAddr(), HelloPacketTypeCode.HELLOREPLY);
            ServiceDispatcher.submitTask(sendHelloPacketTask);
            log.debug("Receive hello request packet..\n" + packet);
        }
    }
}
