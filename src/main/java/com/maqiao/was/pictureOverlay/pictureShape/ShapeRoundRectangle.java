/**
 * 
 */
package com.maqiao.was.pictureOverlay.pictureShape;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.maqiao.was.pictureOverlay.MQLayerPicture;
import com.maqiao.was.pictureOverlay.MQPO;

/**
 * 切图片的四角，使之圆滑
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
@Service("shapeRoundRectangle")
@Scope("singleton")
public class ShapeRoundRectangle implements InterfaceShape {
	int roundSize = 50;

	/*
	 * (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.pictureShape.InterfaceShape#getShapeID()
	 */
	@Override
	public int getShapeID() {
		return 2;
	}

	/*
	 * (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.pictureShape.InterfaceShape#getBufferedImage(com.maqiao.was.pictureOverlay.MQLayerPicture)
	 */
	@Override
	public BufferedImage getBufferedImage(MQLayerPicture mqLayerPicture) {
		BufferedImage buffImg = mqLayerPicture.getBufferedImage();
		return Graphics2DPictureRoundRectangle(buffImg, roundSize);
	}

	/**
	 * 把图片四角圆滑
	 * @param buffImg BufferedImage
	 * @param roundSize int
	 * @return BufferedImage
	 */
	public static final BufferedImage Graphics2DPictureRoundRectangle(BufferedImage buffImg, int roundSize) {
		if (buffImg == null) return buffImg;
		RoundRectangle2D rect = new RoundRectangle2D.Double(0, 0, buffImg.getWidth(), buffImg.getHeight(), roundSize, roundSize);
		BufferedImage bi = new BufferedImage(buffImg.getWidth(), buffImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bi.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setClip(rect);
		g.drawImage(buffImg, 0, 0, null);
		g.dispose();
		return bi;
	}

	/*
	 * (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.pictureShape.InterfaceShape#setParameter(com.maqiao.was.pictureOverlay.MQLayerPicture)
	 */
	@Override
	public void setParameter(MQLayerPicture mqLayerPicture) {
		int index = mqLayerPicture.getIndex();
		MQPO mqpo = mqLayerPicture.getMqpo();
		roundSize = mqpo.getRequestInt(index, "shape.round.size", roundSize);
	}

	public static void main(String[] args) {
		try {
			BufferedImage bufferImg = ImageIO.read(new File("d:/data/logo.png"));
			BufferedImage b = Graphics2DPictureRoundRectangle(bufferImg, 80);
			ImageIO.write(b, "PNG", new File("D:\\Saturn4.png"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
