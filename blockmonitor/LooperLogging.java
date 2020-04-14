package com.ng8.mobile.blockmonitor;

import android.util.Printer;

/**
 * Created by likaiyu on 2020/4/13.
 *
 *  {@link android.os.Looper }在loop()方法中消息分发前后都会调用logging.println()
 *
 *  if (logging != null) {
 *       logging.println(">>>>> Dispatching to " + msg.target + " " +
 *               msg.callback + ": " + msg.what);
 *  }
 *
 *  if (logging != null) {
 *      logging.println("<<<<< Finished to " + msg.target + " " + msg.callback);
 *  }
 *
 *  在第一次调用时开始抓取堆栈信息并记录时间点，
 * 	在第二次调用时根据阈值判断卡顿情况、记录信息并停止抓取堆栈
 *
 */
public class LooperLogging implements Printer {

    private OnBlockListener mBlockListener;
    private boolean mPrintStarted = false;
    private long mStartTime = 0;

    public LooperLogging(OnBlockListener blockListener) {
        this.mBlockListener = blockListener;
    }

    @Override
    public void println(String x) {
        if (!mPrintStarted) {
            //分发消息前调用
            mPrintStarted = true;
            mStartTime = System.currentTimeMillis();
            //开始捕获堆栈信息
            startGetStackTrace();
        } else {
            //分发消息后调用
            final long endTime = System.currentTimeMillis();
            mPrintStarted = false;
            if (isBlocked(endTime)) {
                notifyBlock(endTime);
            }
            //停止捕获堆栈信息
            stopGetStackTrace();
        }
    }

    /**
     * 通知卡顿
     *
     * @param endTime
     */
    private void notifyBlock(final long endTime) {
        final long startTime = mStartTime;
        HandlerThreadFactory.getSaveInfoThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                if (mBlockListener != null) {
                    mBlockListener.onBlock(startTime, endTime);
                }
            }
        });
    }

    private boolean isBlocked(long endTime) {
        //大部分系统消息分发时长都很小（<10）
        if (endTime - mStartTime > 100) {
            System.out.println("FSBlockMonitor---消息分发时长 " + (endTime - mStartTime));
        }
        return endTime - mStartTime > ForestoneConfig.TIME_MIN_DELAY;
    }

    private void startGetStackTrace() {
        if (FSBlockMonitor.getInstance().stackTrace != null) {
            FSBlockMonitor.getInstance().stackTrace.start();
        }
    }

    private void stopGetStackTrace() {
        if (FSBlockMonitor.getInstance().stackTrace != null) {
            FSBlockMonitor.getInstance().stackTrace.stop();
        }
    }

    public interface OnBlockListener {
        void onBlock(long startTime, long endTime);
    }

}
