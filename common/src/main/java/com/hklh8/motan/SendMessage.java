package com.hklh8.motan;

import java.util.List;

/**
 * 主服务器下发消息到注册服务器
 * <p>
 * Created by GouBo on 2018/1/2.
 */
public interface SendMessage {
    String test(String mac, String fun, String args);

    /**
     * 获取所有在线设备
     */
    List<String> getOnlineDevices();

    /***
     * 开启加密，'1' 为加密 '0' 为不加密
     * @param isEncrypt 是否加密标志 '1' 加密 ，'0' 不加密
     */
    void openEncrypt(String mac,char isEncrypt);
}
