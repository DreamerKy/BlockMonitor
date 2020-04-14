package com.ng8.mobile.blockmonitor;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by likaiyu on 2020/4/14.
 */
abstract class BaseTrace {

    private AtomicBoolean shouldTrace = new AtomicBoolean(false);

    private Runnable traceTask = new Runnable() {
        @Override
        public void run() {
            trace();
            if (shouldTrace.get()) {
                HandlerThreadFactory.getTraceThreadHandler().postDelayed(traceTask, ForestoneConfig.TIME_TRACE_FRAME);
            }
        }
    };

    /**
     * 开始获取
     */
    public void start() {
        if (!shouldTrace.get()) {
            shouldTrace.set(true);
            HandlerThreadFactory.getTraceThreadHandler().removeCallbacks(traceTask);
            HandlerThreadFactory.getTraceThreadHandler().postDelayed(traceTask, (long) (ForestoneConfig.TIME_MIN_DELAY * 0.6f));
        }
    }

    /**
     * 停止获取
     */
    public void stop() {
        if (shouldTrace.get()) {
            shouldTrace.set(false);
            HandlerThreadFactory.getTraceThreadHandler().removeCallbacks(traceTask);
        }
    }

    abstract void trace();
}
