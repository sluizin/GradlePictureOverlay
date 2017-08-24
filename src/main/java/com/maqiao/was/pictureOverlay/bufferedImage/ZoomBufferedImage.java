/**
 * 
 */
package com.maqiao.was.pictureOverlay.bufferedImage;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import com.maqiao.was.pictureOverlay.MQAbstractLayer;

/**
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
public class ZoomBufferedImage {
	/**
	 * 缩放 BufferedImage
	 * @param buffImg BufferedImage
	 * @param mqAbstractLayer MQAbstractLayer
	 * @return BufferedImage
	 */
	public static final BufferedImage getBufferedImageZoom(BufferedImage buffImg,MQAbstractLayer mqAbstractLayer){
		if (buffImg == null) return buffImg;
		int width=mqAbstractLayer.getParameter().getWidth();
		int height = mqAbstractLayer.getParameter().getHeight();
		int TYPE = BufferedImage.TYPE_4BYTE_ABGR;
		BufferedImage bi = new BufferedImage(width, height, TYPE);
		Graphics2D g = bi.createGraphics();
		g.drawImage(buffImg, 0, 0,width,height,0,0,buffImg.getWidth(),buffImg.getHeight(), null);
		g.dispose();
		return bi;
	}
}
