/**
 * 
 */
package com.maqiao.was.pictureOverlay.pictureShape;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.maqiao.was.pictureOverlay.MQLayerPicture;
import com.maqiao.was.pictureOverlay.MQPO;

/**
 * 切成圆形
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
@Service("shapeCircular")
@Scope("singleton")
public class ShapeCircular implements InterfaceShape {
	int circularwidth = 0;
	int circularheight = 0;

	/*
	 * (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.pictureShape.InterfaceShape#getShapeID()
	 */
	@Override
	public int getShapeID() {
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.pictureShape.InterfaceShape#setParameter(com.maqiao.was.pictureOverlay.MQLayerPicture)
	 */
	@Override
	public void setParameter(MQLayerPicture mqLayerPicture) {
		int index = mqLayerPicture.getIndex();
		MQPO mqpo = mqLayerPicture.getMqpo();
		this.circularwidth = mqpo.getRequestInt(index, "shape.circular.width", 0);
		this.circularheight = mqpo.getRequestInt(index, "shape.circular.height", 0);
	}

	/*
	 * (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.pictureShape.InterfaceShape#getBufferedImage(com.maqiao.was.pictureOverlay.MQLayerPicture)
	 */
	@Override
	public BufferedImage getBufferedImage(MQLayerPicture mqLayerPicture) {
		BufferedImage buffImg = mqLayerPicture.getBufferedImage();
		if (buffImg == null) return buffImg;
		int width = this.circularwidth;
		if (width == 0) width = buffImg.getWidth();
		int height = this.circularheight;
		if (height == 0) height = buffImg.getHeight();
		if (width == 0 || height == 0) return buffImg;
		int TYPE = BufferedImage.TYPE_INT_RGB;
		if (mqLayerPicture.getParameter().getBgcolor() == null) TYPE = BufferedImage.TYPE_4BYTE_ABGR;
		BufferedImage bi = new BufferedImage(width, height, TYPE);
		Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, width, height);
		Graphics2D g = bi.createGraphics();
		if (mqLayerPicture.getParameter().getBgcolor() != null) {
			g.setColor(mqLayerPicture.getParameter().getBgcolor());
			g.fillRect(0, 0, width, height);
			g.setComposite(AlphaComposite.getInstance(10, 1.0f));
			g.fill(new Rectangle(width, height));
		}
		g.setClip(shape);
		g.drawImage(buffImg, 0, 0, null);
		g.dispose();
		return bi;
	}

	/**
	 * 画圆，是否需要透明 如果bgcolor为null，则背景为透明
	 * @param buffImg BufferedImage
	 * @param mqLayerPicture MQLayerPicture
	 * @return BufferedImage
	 */
	@Deprecated
	static BufferedImage Graphics2DPictureCircular(MQLayerPicture mqLayerPicture) {
		BufferedImage buffImg = mqLayerPicture.getBufferedImage();
		int TYPE = BufferedImage.TYPE_INT_RGB;
		if (mqLayerPicture.getParameter().getBgcolor() == null) TYPE = BufferedImage.TYPE_4BYTE_ABGR;
		BufferedImage bi = new BufferedImage(buffImg.getWidth(), buffImg.getHeight(), TYPE);
		Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, buffImg.getWidth(), buffImg.getHeight());
		Graphics2D g = bi.createGraphics();
		if (mqLayerPicture.getParameter().getBgcolor() != null) {
			g.setColor(mqLayerPicture.getParameter().getBgcolor());
			g.fillRect(0, 0, buffImg.getWidth(), buffImg.getHeight());
			g.setComposite(AlphaComposite.getInstance(10, 1.0f));
			g.fill(new Rectangle(buffImg.getWidth(), buffImg.getHeight()));
		}
		g.setClip(shape);
		g.drawImage(buffImg, 0, 0, null);
		g.dispose();
		return bi;
	}

}
