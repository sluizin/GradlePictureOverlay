/**
 * 
 */
package com.maqiao.was.pictureOverlay.specialPictures.qrCode;

import java.awt.image.BufferedImage;

/**
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
public interface InterfaceQRCode {
	/**
	 * 唯一关键字
	 * @return String
	 */
	String getKey();
	/**
	 * 判断style值是否是对象内
	 * @param style int
	 * @return boolean
	 */
	boolean isStyle(int style);
	/**
	 * 得到BufferedImage
	 * @param cs ColorStyle
	 * @return BufferedImage
	 */
	BufferedImage getBufferedImageStyle(ColorStyle cs);
}
