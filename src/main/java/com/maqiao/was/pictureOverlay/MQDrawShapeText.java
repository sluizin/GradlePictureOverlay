/**
 * 
 */
package com.maqiao.was.pictureOverlay;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * 对文字图层进行画画
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
public class MQDrawShapeText {


	/**
	 * 对文字图层进行画画
	 * @param mqLayerText MQLayerText
	 * @return BufferedImage
	 */
	static BufferedImage getBufferImageDrawText(MQLayerText mqLayerText) {
		return getBufferImageDrawText(mqLayerText,mqLayerText.parameter);
	}
	/**
	 * 对文字图层进行画画
	 * @param mqLayerText MQLayerText
	 * @param parameter parameter
	 * @return BufferedImage
	 */
	static BufferedImage getBufferImageDrawText(MQLayerText mqLayerText, MQLayerParameter parameter) {
		if (mqLayerText.text == null || mqLayerText.text.length() == 0) return null;
		int TYPE = BufferedImage.TYPE_4BYTE_ABGR;
		if (parameter.bgcolor != null) TYPE = BufferedImage.TYPE_INT_RGB;
		BufferedImage bi = new BufferedImage(parameter.width, parameter.height, TYPE);
		Graphics2D g = bi.createGraphics();
		if (parameter.bgcolor != null) g.setBackground(parameter.bgcolor);
		g.setPaint(mqLayerText.color);
		g.setFont(mqLayerText.font);
		if (mqLayerText.issmooth) g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		FontMetrics m = g.getFontMetrics();
		final int fontHeight = m.getAscent() + m.getDescent();
		int x = 0, y = 0;
		int textlen = m.stringWidth(mqLayerText.text);
		//MQLogger.loggerInfo(parameter.width+"/"+textlen);
		if (textlen <= parameter.width) {
			y = getY(m, parameter.height, parameter.valign);
			//MQLogger.loggerInfo("fontHeight:[" + fontHeight + "]m.getAscent():[" + m.getAscent() + "]x[" + x + "]y[" + y + "]" + mqLayerText.text + "\t parameter.align:" + (parameter.align % 3));
			drawString(g, mqLayerText.text, parameter.width, x, y, parameter.align % 3);
			return bi;
		}
		/* 分行，计算各行文字内容 */
		List<String> rows = getMultiList(m, mqLayerText.text, parameter.width);
		int height = parameter.height;
		int linespacing = mqLayerText.linespacing;
		final int rowMax = 1 + (int) ((height - fontHeight) / (fontHeight + linespacing));//最高多少行
		final int row = (rowMax > rows.size()) ? rows.size() : rowMax;

		int viewHeight = fontHeight + ((row - 1) * (fontHeight + linespacing));
		int yOffset = 0;
		switch (parameter.valign) {
		case 1:
			yOffset = (height - viewHeight) / 2;
			break;
		case 2:
			yOffset = (height - viewHeight);
			break;
		}
		y = yOffset + m.getAscent();
		MQLogger.loggerInfo("x[" + x + "]y[" + y + "]parameter.width[" + parameter.width + "]parameter.height[" + parameter.height + "]rowMax[" + rowMax + "]row[" + row + "]yOffset[" + yOffset + "]" + mqLayerText.text);
		String element;
		for (int i = 0; i < row; i++) {
			element = rows.get(i);
			/* 绘制字符串 */
			MQLogger.loggerInfo("(" + i + ")x[" + x + "]y[" + y + "]" + element);
			drawString(g, element, parameter.width, x, y, parameter.align % 3);
			y += (linespacing + fontHeight);
		}
		return bi;
	}

	/**
	 * 得到高度
	 * @param m FontMetrics
	 * @param height int
	 * @param valign int
	 * @return int
	 */
	static int getY(FontMetrics m, int height, int valign) {
		if (m == null) return 0;
		final int fontHeight = m.getAscent() + m.getDescent();
		int ascent = m.getAscent();
		if (height <= fontHeight) return ascent;
		switch (valign) {
		case 1:/* 上下居中 */
			return (height - fontHeight) / 2 + ascent;
		case 2:/* 上下居底部 */
			return (height - fontHeight) + ascent;
		default:/* 上下居顶部 */
			return ascent;
		}
	}

	static int getY(FontMetrics m, MQLayerParameter parameter, List<String> rows) {
		final int fontHeight = m.getAscent() + m.getDescent();
		int height = parameter.height;
		int rowMax = (int) height / fontHeight;//最高多少行
		int row = (rowMax > rows.size()) ? rows.size() : rowMax;
		int viewHeight = row * height;
		int yOffset = 0;
		switch (parameter.valign) {
		case 1:
			yOffset = (height - viewHeight) / 2;
		case 2:
			yOffset = (height - viewHeight);
		}
		return parameter.y + yOffset + m.getAscent();
	}

	/**
	 * 以左上角为标准进行居中显示字符串
	 * @param g Graphics
	 * @param str String
	 * @param width int
	 * @param xPos int
	 * @param yPos int
	 * @param align int
	 */
	static void drawString(Graphics g, String str, int width, int xPos, int yPos, int align) {
		int strWidth = g.getFontMetrics().stringWidth(str);
		if (width <= strWidth) {
			g.drawString(str, xPos, yPos);
			return;
		}
		int x = xPos;
		switch (align) {
		case 1:
			x = xPos + ((width - strWidth) / 2);
			break;
		case 2:
			x = xPos + width - strWidth;
			break;
		}
		g.drawString(str, x, yPos);
	}

	/**
	 * 以中心为定点进行居中显示字符串
	 * @param g Graphics
	 * @param str String
	 * @param xPos int
	 * @param yPos int
	 * @param align int 1:中间对齐 2:右对齐 其它左对齐
	 */
	static void drawString(Graphics g, String str, int xPos, int yPos, int align) {
		int strWidth = g.getFontMetrics().stringWidth(str);
		switch (align) {
		case 1:
			g.drawString(str, xPos + strWidth / 2, yPos);
			break;
		case 2:
			g.drawString(str, xPos + strWidth, yPos);
			break;
		default:
			g.drawString(str, xPos, yPos);
		}
	}

	/**
	 * 把文字化成多行列表
	 * @param g Graphics2D
	 * @param text String
	 * @param lineWidth int
	 * @return List<String>
	 */
	static List<String> getMultiList(Graphics2D g, String text, int lineWidth) {
		return getMultiList(g.getFontMetrics(), text, lineWidth);
	}

	/**
	 * 把文字化成多行列表
	 * @param m FontMetrics
	 * @param text String
	 * @param lineWidth int
	 * @return List<String>
	 */
	private static List<String> getMultiList(FontMetrics m, String text, int lineWidth) {
		List<String> rows = new ArrayList<String>(3);
		if (text == null || text.length() == 0) return rows;
		int fromIndex = 0, strWidth = 0;
		char[] chars = text.toCharArray();
		for (int bgn = 0; bgn < text.length();) {//逐个字符累加计算长度,超过行宽,自动换行
			strWidth += m.charWidth(chars[bgn]);
			if (strWidth > lineWidth) {
				rows.add(text.substring(fromIndex, bgn));
				strWidth = 0;
				fromIndex = bgn;
			} else bgn++;
		}/* 加上最后一行 */
		if (fromIndex < text.length()) rows.add(text.substring(fromIndex, text.length()));
		return rows;
	}

}
