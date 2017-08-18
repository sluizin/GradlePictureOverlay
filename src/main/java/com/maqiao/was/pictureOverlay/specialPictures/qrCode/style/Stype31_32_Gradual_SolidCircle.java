/**
 * 
 */
package com.maqiao.was.pictureOverlay.specialPictures.qrCode.style;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.maqiao.was.pictureOverlay.specialPictures.qrCode.InterfaceQRCode;
import com.maqiao.was.pictureOverlay.specialPictures.qrCode.ColorStyle;

/**
 * 渐进色实体圆
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
@Service("stype31_32_Gradual_SolidCircle")
@Scope("singleton")
public class Stype31_32_Gradual_SolidCircle implements InterfaceQRCode {
	/* (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.specialPictures.qrCode.InterfaceQRCode#getKey()
	 */
	@Override
	public String getKey() {
		return "31_32";
	}
	/*
	 * (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.specialPictures.qrCode.InterfaceQRCode#isStyle(int)
	 */
	@Override
	public boolean isStyle(int style) {
		return style == 31 || style == 32;
	}
	/*
	 * (non-Javadoc)
	 * @see
	 * com.maqiao.was.pictureOverlay.specialPictures.qrCode.InterfaceQRCode#getBufferedImageStyle(com.maqiao.was.pictureOverlay.specialPictures.qrCode.ColorStyle
	 * )
	 */
	@Override
	public BufferedImage getBufferedImageStyle(ColorStyle cs) {
		int style = cs.getStyle();
		if (!isStyle(style)) return null;
		if (style == 31) return getBufferedImageStyle_31(cs);
		if (style == 32) return getBufferedImageStyle_32(cs);
		return null;
	}
	/**
	 * 得到图片有背景的渐进色实体圆
	 * @param cs ColorStyle
	 * @return BufferedImage
	 */
	public static final BufferedImage getBufferedImageStyle_31(ColorStyle cs) {
		BufferedImage image = new BufferedImage(cs.getWidth(), cs.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = image.createGraphics();
		if (cs.getColorList().size() < 3) return image;
		Color c1 = cs.getColorList().get(0);
		Color c2 = cs.getColorList().get(1);
		Color c3 = cs.getColorList().get(2);
		g.setColor(c1);
		g.fillRect(0, 0, cs.getWidth(), cs.getHeight());
		Color[] colarray = { c2, c3 };
		Point2D.Double doubl = new Point2D.Double(cs.getWidth() / 2.0, cs.getHeight() / 2.0);
		RadialGradientPaint p = new RadialGradientPaint(doubl, cs.getWidth(), new float[] { 0.1f, 0.9f }, colarray);
		g.setPaint(p);
		g.fillOval(0, 0, cs.getWidth(), cs.getHeight());
		g.dispose();
		return image;
	}

	/**
	 * 没有背景的中心向外渐进色实体圆
	 * @param cs ColorStyle
	 * @return BufferedImage
	 */
	public static final BufferedImage getBufferedImageStyle_32(ColorStyle cs) {
		BufferedImage image = new BufferedImage(cs.getWidth(), cs.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = image.createGraphics();
		if (cs.getColorList().size() < 2) return image;
		Color c1 = cs.getColorList().get(0);
		Color c2 = cs.getColorList().get(1);
		g.setColor(c1);
		g.fillRect(0, 0, cs.getWidth(), cs.getHeight());
		Color[] colarray = { c1, c2 };
		Point2D.Double doubl = new Point2D.Double(cs.getWidth() / 2.0, cs.getHeight() / 2.0);
		RadialGradientPaint p = new RadialGradientPaint(doubl, cs.getWidth(), new float[] { 0.1f, 0.9f }, colarray);
		g.setPaint(p);
		g.fillOval(-(cs.getWidth() / 2), -(cs.getHeight() / 2), cs.getWidth() * 2, cs.getHeight() * 2);
		g.dispose();
		return image;
	}

}
