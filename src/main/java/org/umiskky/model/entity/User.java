package org.umiskky.model.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/18
 */
@Entity
public class User {
    @Id
    private long id;
    @Index
    private String uuid;
    private String nickname;
    private String ipAddress;
    private int serverPort;
    private Boolean status;
    private long lastUpdated;

    public User() {
    }

    public User(long id, String uuid, String nickname, String ipAddress, int serverPort, Boolean status, long lastUpdated) {
        this.id = id;
        this.uuid = uuid;
        this.nickname = nickname;
        this.ipAddress = ipAddress;
        this.serverPort = serverPort;
        this.status = status;
        this.lastUpdated = lastUpdated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}