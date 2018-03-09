package com.hklh8.motan;

/**
 * 注册服务器上报消息到主服务器
 * <p>
 * Created by GouBo on 2018/1/2.
 */
public interface ReportMessage {

    /*****
     * cpu 内存占用率
     * @param cpu cpu占用率
     * @param mem 内存占用率
     * @param mac mac地址
     * @return string
     */
    String cpuAndMemRate(double cpu, double mem, String mac);

    /******
     * 设备在线通知
     * @param mac mac地址
     * @param equipName 设备名称
     */
    void online(String mac, String equipName);

    /******
     * 设备离线通知
     * @param mac mac地址
     */
    void offline(String mac);
}
