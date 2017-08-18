/**
 * 
 */
package com.maqiao.was.pictureOverlay;

import org.apache.log4j.Logger;

/**
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
public final class MQLogger {
	public static final Logger logger = Logger.getLogger(MQLogger.class);
	/**
	 * 保存日志
	 * @param message Object
	 */
	public static final void loggerInfo(Object message){
		logger.info(message);
	}
}
