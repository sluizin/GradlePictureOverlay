/**
 * 
 */
package com.maqiao.was.pictureOverlay.specialPictures;

import java.awt.image.BufferedImage;

import com.maqiao.was.pictureOverlay.MQLayerSpecialPicture;

/**
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
public interface InterfaceSpecialPicture {
	public int getSpecialPictureID();

	/**
	 * 通过request与index得到参数，得到 BufferedImage
	 * @param mqpo MQPO
	 * @param index int
	 * @return BufferedImage
	 */
	public BufferedImage getBufferedImage(MQLayerSpecialPicture mqLayerSpecialPicture, int index);

	/*
	 * 获取参数
	 * @param mqpo MQPO
	 * @param index int
	public void setParameter(MQLayerSpecialPicture mqLayerSpecialPicture, int index);
	 */
}
