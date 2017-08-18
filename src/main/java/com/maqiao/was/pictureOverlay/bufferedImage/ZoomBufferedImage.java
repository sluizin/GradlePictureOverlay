/**
 * 
 */
package com.maqiao.was.pictureOverlay.bufferedImage;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
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
		int TYPE = BufferedImage.TYPE_INT_RGB;
		if (mqAbstractLayer.getParameter().getBgcolor() == null) TYPE = BufferedImage.TYPE_4BYTE_ABGR;
		BufferedImage bi = new BufferedImage(width, height, TYPE);
		Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, width, height);
		Graphics2D g = bi.createGraphics();
		if (mqAbstractLayer.getParameter().getBgcolor() != null) {
			g.setColor(mqAbstractLayer.getParameter().getBgcolor());
			g.fillRect(0, 0, width, height);
			g.setComposite(AlphaComposite.getInstance(10, 1.0f));
			g.fill(new Rectangle(width, height));
		}
		g.setClip(shape);
		g.drawImage(buffImg, 0, 0, null);
		g.dispose();
		return bi;
	}
}
