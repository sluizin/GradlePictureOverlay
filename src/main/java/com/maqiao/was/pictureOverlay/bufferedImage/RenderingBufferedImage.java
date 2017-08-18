/**
 * 
 */
package com.maqiao.was.pictureOverlay.bufferedImage;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.maqiao.was.pictureOverlay.MQRenderingStyle;
import com.maqiao.was.pictureOverlay.MQUtils;

/**
 * 后期渲染
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
public class RenderingBufferedImage {
	/**
	 * 
	 * @param bim BufferedImage
	 * @param cs MQRenderingStyle
	 * @return BufferedImage
	 */
	public static final BufferedImage getBufferedImage(BufferedImage bim, MQRenderingStyle cs) {
		if (bim == null || cs == null) return null;
		int width = bim.getWidth();
		int height = bim.getHeight();
		cs.setWidth(width);
		cs.setHeight(height);
		MQUtils.toFile(bim, "D:/data/pic1.png");
		BufferedImage sourcebi = getBufferedImage(cs);
		MQUtils.toFile(sourcebi, "D:/data/pic2.png");
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int p = bim.getRGB(x, y);
				if (cs.isTransparent() && MQUtils.isTransparent(p)) {
					MQUtils.setTransparent(sourcebi, x, y);/* 把生成的图片的定点变成透明 */
					continue;
				}
				if (cs.isWhite() && MQUtils.isWhite(p)) {
					sourcebi.setRGB(x, y, Color.WHITE.getRGB());/* 把生成的图片的定点变成白色 */
				}
			}
		}
		MQUtils.toFile(sourcebi, "D:/data/pic3.png");
		return sourcebi;
	}

	public static final BufferedImage getBufferedImage(MQRenderingStyle cs) {
		if (cs == null || cs.getColorList() == null || cs.getColorList().size() == 0 || cs.getStyle() <= 0) return getBufferedImageStyle_BLACK(cs);
		int style = cs.getStyle();
		switch (style) {
		case 1:
			return getBufferedImageStyle_BLACK(cs);
		case 11:/* 从左向右颜色块 */
		case 12:/* 从上向下颜色块 */
			return getBufferedImageStyle_11_12(cs);
		case 31:
			/*
			 * 得到图片有背景的渐进色实体圆
			 * 颜色列表中
			 * 1:背景色
			 * 2:中心色
			 * 3:实体圆边缘色
			 */
			return getBufferedImageStyle_31(cs);
		case 32:
			/*
			 * 得到图片的渐进色实体圆
			 * 颜色列表中
			 * 1:中心色
			 * 2:实体圆边缘色
			 */
			return getBufferedImageStyle_32(cs);
		}
		/* [ 21--28 ]全局渐进色 从颜色1 - 颜色2 */
		if (style >= 20 && style <= 29) return getBufferedImageStyle_2X(cs);
		return getBufferedImageStyle_BLACK(cs);
	}

	/**
	 * 得到左向右还是上向下的颜色块
	 * @param cs MQRenderingStyle
	 * @return BufferedImage
	 */
	private static final BufferedImage getBufferedImageStyle_11_12(MQRenderingStyle cs) {
		int style = cs.getStyle();
		BufferedImage image = new BufferedImage(cs.getWidth(), cs.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		if (style != 11 && style != 12) return image;
		boolean isTransverse = false;
		if (style == 12) isTransverse = true;
		int size = cs.getColorList().size();
		int per = (isTransverse ? cs.getHeight() : cs.getWidth()) / size;
		Graphics2D g = image.createGraphics();
		for (int i = 0; i < size; i++) {
			g.setColor(cs.getColorList().get(i));
			int xory = (i == 0) ? 0 : i * per + 1;
			if (isTransverse) g.fillRect(0, xory, cs.getWidth(), per);
			else g.fillRect(xory, 0, per, cs.getHeight());
		}
		g.dispose();
		return image;
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
	private static final BufferedImage getBufferedImageStyle_2X(MQRenderingStyle cs) {
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
	private static final BufferedImage getBufferedImageStyle_20(MQRenderingStyle cs) {
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

	/**
	 * 得到图片有背景的渐进色实体圆
	 * @param cs ColorStyle
	 * @return BufferedImage
	 */
	private static final BufferedImage getBufferedImageStyle_31(MQRenderingStyle cs) {
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
	private static final BufferedImage getBufferedImageStyle_32(MQRenderingStyle cs) {
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

	/**
	 * 得到全黑色的图片
	 * @param cs ColorStyle
	 * @return BufferedImage
	 */
	private static final BufferedImage getBufferedImageStyle_BLACK(MQRenderingStyle cs) {
		BufferedImage image = new BufferedImage(cs.getWidth(), cs.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = image.createGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, cs.getWidth(), cs.getHeight());
		g.dispose();
		return image;
	}

	public static void main(String[] args) {
		String path = "D:/data/test/";
		String[] fileArray = { "bg", "bg2" };
		List<MQRenderingStyle> list = new ArrayList<MQRenderingStyle>();
		list.add(getMQRenderingStyle(23, MQUtils.getColor("FCE38A"), MQUtils.getColor("F38181")));
		list.add(getMQRenderingStyle(27, MQUtils.getColor("FCE38A"), MQUtils.getColor("F38181")));
		list.add(getMQRenderingStyle(23, MQUtils.getColor("17EAD9"), MQUtils.getColor("6078EA")));
		list.add(getMQRenderingStyle(27, MQUtils.getColor("17EAD9"), MQUtils.getColor("6078EA")));
		list.add(getMQRenderingStyle(11, MQUtils.getColor("FF7676")));
		list.add(getMQRenderingStyle(11, MQUtils.getColor("57CA85")));
		try {
			for (int i = 0, len = fileArray.length; i < len; i++) {
				String filepath = path + fileArray[i] + ".png";
				File file = new File(filepath);
				BufferedImage buffImgSource = ImageIO.read(file);
				for (int ii = 0, size = list.size(); ii < size; ii++) {
					MQRenderingStyle cs = list.get(ii);
					BufferedImage buffImg = getBufferedImage(buffImgSource, cs);
					if (buffImg != null) {
						String newFilepath = path + fileArray[i] + "_" + ii + ".png";
						MQUtils.toFile(buffImg, newFilepath);
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	static final MQRenderingStyle getMQRenderingStyle(int style, Color... colorArray) {
		MQRenderingStyle cs = new MQRenderingStyle();
		List<Color> list = java.util.Arrays.asList(colorArray);
		cs.setColorList(list);
		cs.setStyle(style);
		return cs;
	}
}
