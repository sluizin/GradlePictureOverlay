/**
 * 
 */
package com.maqiao.was.pictureOverlay.specialPictures.qrCode.style;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.maqiao.was.pictureOverlay.MQUtils;
import com.maqiao.was.pictureOverlay.specialPictures.qrCode.InterfaceQRCode;
import com.maqiao.was.pictureOverlay.specialPictures.qrCode.ColorStyle;

/**
 * 得到图片的渐进色<br>
 * 20:从中间到四角<br>
 * 21:左上角到右下角<br>
 * 22:上边中到下边中<br>
 * 23:右上角到左下角<br>
 * 24:右边中到左边中<br>
 * 25:右下角到左上角<br>
 * 26:下边中到上边中<br>
 * 27:左下角到右上角<br>
 * 28:左边中到右边中<br>
 * 29:随机渐进色<br>
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
@Service("style2X_Gradual_Oneway")
@Scope("singleton")
public class Style2X_Gradual_Oneway implements InterfaceQRCode {

	/* (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.specialPictures.qrCode.InterfaceQRCode#getKey()
	 */
	@Override
	public String getKey() {
		return "2X";
	}
	/*
	 * (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.specialPictures.qrCode.InterfaceQRCode#isStyle(int)
	 */
	@Override
	public boolean isStyle(int style) {
		return style >= 20 && style <= 29;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.maqiao.was.pictureOverlay.specialPictures.qrCode.InterfaceQRCode#getBufferedImageStyle(com.maqiao.was.pictureOverlay.specialPictures.qrCode.QR_CODE
	 * .ColorStyle)
	 */
	@Override
	public BufferedImage getBufferedImageStyle(ColorStyle cs) {
		int style = cs.getStyle();
		if (!isStyle(style)) return null;
		return getBufferedImageStyle_2X(cs);
	}

	/**
	 * 得到图片的渐进色<br>
	 * 20:从中间到四角<br>
	 * 21:左上角到右下角<br>
	 * 22:上边中到下边中<br>
	 * 23:右上角到左下角<br>
	 * 24:右边中到左边中<br>
	 * 25:右下角到左上角<br>
	 * 26:下边中到上边中<br>
	 * 27:左下角到右上角<br>
	 * 28:左边中到右边中<br>
	 * 29:随机渐进色<br>
	 * @param cs ColorStyle
	 * @return BufferedImage
	 */
	public static final BufferedImage getBufferedImageStyle_2X(ColorStyle cs) {
		BufferedImage image = new BufferedImage(cs.getWidth(), cs.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = image.createGraphics();
		if (cs.getColorList().size() < 2) return image;
		Color c1 = cs.getColorList().get(0);
		Color c2 = cs.getColorList().get(1);
		int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
		switch (cs.getStyle()) {
		case 20:
			return getBufferedImageStyle_20(cs);
		case 21:
			x2 = cs.getWidth();
			y2 = cs.getHeight();
			break;
		case 22:
			x1 = cs.getWidth() / 2;
			x2 = cs.getWidth() / 2;
			y2 = cs.getHeight();
			break;
		case 23:
			x1 = cs.getWidth();
			y2 = cs.getHeight();
			break;
		case 24:
			x1 = cs.getWidth();
			y1 = cs.getHeight() / 2;
			y2 = cs.getHeight() / 2;
			break;
		case 25:
			x1 = cs.getWidth();
			y1 = cs.getHeight();
			break;
		case 26:
			x1 = cs.getWidth() / 2;
			y1 = cs.getHeight();
			x2 = cs.getWidth() / 2;
			break;
		case 27:
			y1 = cs.getHeight();
			x2 = cs.getWidth();
			break;
		case 28:
			y1 = cs.getHeight() / 2;
			x2 = cs.getWidth();
			y2 = cs.getHeight() / 2;
			break;
		default:
			cs.setStyle(MQUtils.getRndInt(20, 28));
			return getBufferedImageStyle_2X(cs);
		}
		GradientPaint pat = new GradientPaint(x1, y1, c1, x2, y2, c2);
		g.setPaint(pat);
		g.fillRect(0, 0, cs.getWidth(), cs.getHeight());
		g.dispose();
		return image;
	}

	/**
	 * 从中心向四角渐进色
	 * @param cs ColorStyle
	 * @return BufferedImage
	 */
	public static final BufferedImage getBufferedImageStyle_20(ColorStyle cs) {
		BufferedImage image = new BufferedImage(cs.getWidth(), cs.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = image.createGraphics();
		if (cs.getColorList().size() < 2) return image;
		Color c1 = cs.getColorList().get(0);
		Color c2 = cs.getColorList().get(1);
		GradientPaint pat = null;
		pat = new GradientPaint(cs.getWidth() / 2, cs.getHeight() / 2, c1, 0, 0, c2);
		g.setPaint(pat);
		g.fillRect(0, 0, cs.getWidth() / 2, cs.getHeight() / 2);
		pat = new GradientPaint(cs.getWidth() / 2, cs.getHeight() / 2, c1, cs.getWidth(), 0, c2);
		g.setPaint(pat);
		g.fillRect(cs.getWidth() / 2, 0, cs.getWidth() / 2, cs.getHeight() / 2);
		pat = new GradientPaint(cs.getWidth() / 2, cs.getHeight() / 2, c1, 0, cs.getHeight(), c2);
		g.setPaint(pat);
		g.fillRect(0, cs.getHeight() / 2, cs.getWidth() / 2, cs.getHeight());
		pat = new GradientPaint(cs.getWidth() / 2, cs.getHeight() / 2, c1, cs.getWidth(), cs.getHeight(), c2);
		g.setPaint(pat);
		g.fillRect(cs.getWidth() / 2, cs.getHeight() / 2, cs.getWidth(), cs.getHeight());
		g.dispose();
		return image;
	}
	public static void main(String[] args) {
		String pathfile = "D:/data/shopimage/image/shop/1426/poster/zzz.png";
		ColorStyle cs = new ColorStyle();
		List<Color> list = new ArrayList<Color>();
		list.add(Color.ORANGE);
		list.add(Color.GREEN);
		list.add(Color.PINK);
		list.add(Color.BLUE);
		list.add(MQUtils.getColor("000000"));
		list.add(MQUtils.getColor("ff00ee"));
		list.add(MQUtils.getColor("00ccaa"));
		list.add(MQUtils.getColor("ee99cc"));
		cs.setColorList(list);
		cs.setColor(Color.BLACK);
		cs.setWidth(300);
		cs.setHeight(300);
		cs.setStyle(20);
		BufferedImage buffImg = getBufferedImageStyle_20(cs);
		MQUtils.toFile(buffImg, pathfile);
	}

}
