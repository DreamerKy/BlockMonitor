package com.ng8.mobile.blockmonitor;

import android.os.Looper;

import java.util.ArrayList;

/**
 * Created by likaiyu on 2020/4/13.
 */
public class FSBlockMonitor {

    StackTrace stackTrace;
    private LooperLogging looperLogging;
    private static FSBlockMonitor instance = new FSBlockMonitor();
    private static boolean mMonitorStarted = false;

    FSBlockMonitor() {
        stackTrace = new StackTrace();
        looperLogging = new LooperLogging(new LooperLogging.OnBlockListener() {

            @Override
            public void onBlock(long startTime, long endTime) {
                ArrayList<String> threadStackList = stackTrace.getThreadStackEntries(startTime, endTime);
                if (threadStackList.size() > 0) {
                    final long time = System.currentTimeMillis();
                    StringBuilder builder = new StringBuilder();
                    for (int index = 0; index < threadStackList.size(); index++) {
                        String item = threadStackList.get(index);
                        if (index > 0) {
                            builder.append("\n----------------------------------------\n");
                        }
                        builder.append(item);
                    }
                    System.out.println("FSBlockMonitor---" + builder.toString());
                }
            }
        });
    }

    public static FSBlockMonitor getInstance() {
        return instance;
    }

    public static void start() {
        if (!mMonitorStarted) {
            mMonitorStarted = true;
            instance.startInternal();
        }
    }

    public static void stop() {
        if (mMonitorStarted) {
            mMonitorStarted = false;
            Looper.getMainLooper().setMessageLogging(null);
        }
    }

    private void startInternal() {
        //给主线程的Looper设置一个自己的Logging
        Looper.getMainLooper().setMessageLogging(looperLogging);
    }


}
