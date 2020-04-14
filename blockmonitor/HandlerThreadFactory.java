package com.ng8.mobile.blockmonitor;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * Created by likaiyu on 2020/4/13.
 */
public class HandlerThreadFactory {

    private static HandlerThreadWrapper mSaveInfoHandlerThread = new HandlerThreadWrapper("save");
    private static HandlerThreadWrapper mTraceHandlerThread = new HandlerThreadWrapper("trace");

    public static Handler getSaveInfoThreadHandler() {
        return mSaveInfoHandlerThread.getHandler();
    }

    public static Handler getTraceThreadHandler() {
        return mTraceHandlerThread.getHandler();
    }

    public static class HandlerThreadWrapper {

        private Handler handler;

        public HandlerThreadWrapper(String name) {
            HandlerThread handlerThread = new HandlerThread(name);
            handlerThread.start();
            Looper looper = handlerThread.getLooper();
            handler = new Handler(looper);
        }

        public Handler getHandler() {
            return handler;
        }
    }

}
