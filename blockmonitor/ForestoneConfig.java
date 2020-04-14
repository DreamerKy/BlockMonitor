package com.ng8.mobile.blockmonitor;

/**
 * @author tuzhao
 */
public final class ForestoneConfig {


    /**
     * 栈采样周期频率
     */
    public static final int TIME_TRACE_STACK = 10;
    /**
     * frame周期发消息间隔
     */
    public static final int TIME_TRACE_FRAME = 100;
    /**
     * 允许的最大白屏时间 毫秒
     */
    public static final long TIME_MAX_WHITE_SCREEN = 0;
    /**
     * 大卡顿时间 2000毫秒
     */
    public static final long TIME_MAX_DELAY = 2000;
    /**
     * 小卡顿时间 300毫秒
     */
    public static final long TIME_MIN_DELAY = 1000;
    /**
     * OkHttp client设置网络超时时间 单位:秒
     */
    public static final int SECONDS_DEFAULT_NETWORK_TIME_OUT = 15;
}
