# BlockMonitor
Block Monitor Of Android Ui Thread

方法1
		Looper可以设置自定义的Logging
		
		public void setMessageLogging(@Nullable Printer printer) {
			mLogging = printer;
		}	
		
		在每个消息分发前后都会调用println()打印日志
		之前
			
		if (logging != null) {
			logging.println(">>>>> Dispatching to " + msg.target + " " + msg.callback + ": " + msg.what);
		}
		之后
		if (logging != null) {
			logging.println("<<<<< Finished to " + msg.target + " " + msg.callback);
		}
		
		在第一次调用时开始抓取堆栈信息并记录时间点，
		在第二次调用时根据当前时间处理超时情况，记录信息并停止抓取堆栈
		就可以避免频繁抓取主线程堆栈了

方法2
		handler.postDelayed(stackCollectTask, stackCollectPeriod)
		
		public void doFrame(long frameTimeNanos) {
			handler.removeCallbacks(stackCollectTask)
			if (掉帧数>阈值) {
			   //检测到卡顿
				...
			}
			handler.postDelayed(stackCollectTask, stackCollectPeriod)
		}
		
		启动监测时发送一个延时消息，如果在延时范围内回调doFrame说明没有发生卡顿，则移除该消息，
		然后再发送延迟消息，同样的道理一直重复，理想状态下如果没有过发生卡顿理论上就不会去采集堆栈信息，
		是不是可以避免频繁抓取主线程堆栈，但这样抓取到的并不一定是卡顿点, 不过抓取概率很大
	
	
