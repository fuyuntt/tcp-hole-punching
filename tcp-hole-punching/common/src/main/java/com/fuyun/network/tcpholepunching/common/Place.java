package com.fuyun.network.tcpholepunching.common;

/**
 * Created by fuyun on 16-5-22.
 * 标记一个网络位置
 */
public class Place {
    private String ip;
    private int port;

    public Place(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "ip:" + ip + "; port:" + port;
    }
}
