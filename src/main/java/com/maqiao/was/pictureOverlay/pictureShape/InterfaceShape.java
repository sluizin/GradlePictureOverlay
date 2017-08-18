/**
 * 
 */
package com.maqiao.was.pictureOverlay.pictureShape;

import java.awt.image.BufferedImage;

import com.maqiao.was.pictureOverlay.MQLayerPicture;

/**
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
public interface InterfaceShape {
	/**
	 * 得到shape ID值
	 * @return int
	 */
	public int getShapeID();
	/**
	 * 把 buffImg，通过参数，得到 BufferedImage
	 * @param mqLayerPicture MQLayerPicture
	 * @return BufferedImage
	 */
	public BufferedImage getBufferedImage(MQLayerPicture mqLayerPicture);
	/**
	 * 获取参数
	 * @param mqLayerPicture MQLayerPicture
	 */
	public void setParameter(MQLayerPicture mqLayerPicture);
}
