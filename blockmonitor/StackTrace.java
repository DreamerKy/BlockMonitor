package com.ng8.mobile.blockmonitor;

import android.annotation.SuppressLint;
import android.os.Looper;
import android.util.LongSparseArray;

import java.util.ArrayList;

/**
 * Created by likaiyu on 2020/4/13.
 */
@SuppressLint("NewApi")
public class StackTrace extends BaseTrace {
    private static final int MAX_COUNT = 32;
    private static final LongSparseArray<JvmStackBean> sStackArray = new LongSparseArray<>();
    private Thread currentThread = Looper.getMainLooper().getThread();

    @Override
    public void trace() {
        System.out.println("FSBlockMonitor---真正获取堆栈");
        StackTraceElement[] stackTrace = currentThread.getStackTrace();
        JvmStackBean bean = new JvmStackBean();
        bean.setStack(stackTrace);
        synchronized (sStackArray) {
            if (sStackArray.size() == MAX_COUNT) {
                sStackArray.removeAt(0);
            }
            sStackArray.put(System.currentTimeMillis(), bean);
        }
    }

    public ArrayList<String> getThreadStackEntries(long startTime, long endTime) {
        ArrayList<String> list = new ArrayList<>();
        synchronized (sStackArray) {
            for (int i = 0; i < sStackArray.size(); i++) {
                long keyTime = sStackArray.keyAt(i);
                if (keyTime > startTime && keyTime < endTime) {
                    list.add(sStackArray.get(keyTime).toString());
                }
            }
        }
        return list;
    }
}
