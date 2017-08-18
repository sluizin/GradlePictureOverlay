/**
 * 
 */
package com.maqiao.was.pictureOverlay;

import java.awt.Color;
import java.awt.Font;

/**
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
public class MQLayerText extends MQAbstractLayer {
	String text = null;
	Font font = new Font("宋体", Font.PLAIN, 30);
	Color color = Color.BLACK;
	/** 多行时的行间隔 */
	int linespacing = 0;
	/**
	 * 是否平滑
	 */
	boolean issmooth = false;

	/**
	 * @param mqpo
	 * @param index
	 */
	public MQLayerText(MQPO mqpo, int index) {
		super(mqpo, index);
		initialization(mqpo, index);
	}

	/**
	 * 初始化
	 * @param mqpo MQPO
	 * @param index int
	 */
	public void initialization(MQPO mqpo, int index) {
		this.text = mqpo.getRequest(index, "font.text");
		if (this.text == null || this.text.length() == 0) return;
		String name = mqpo.getRequest(index, "font.name", "宋体");
		int style = mqpo.getRequestInt(index, "font.style", 0) % 3;
		int size = mqpo.getRequestInt(index, "font.size", 30);
		this.font = new Font(name, style, size);
		this.color = mqpo.getRequestColor(index, "font.color", Color.BLACK);
		this.linespacing = mqpo.getRequestInt(index, "font.linespacing", 0);
		this.issmooth = mqpo.getRequestBoolean(index, "font.issmooth", false);
	}

	@Override
	public void bufferImageFormat() {
		this.buffImg = MQDrawShapeText.getBufferImageDrawText(this, parameter);
	}

	/*
	 * (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.MQAbstractLayer#isSafe()
	 */
	@Override
	public boolean isSafe() {
		if (font == null || (text == null || text.length() == 0) || color == null) return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.MQAbstractLayer#isPicture()
	 */
	@Override
	public int isState() {
		return 2;
	}

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}

	public final Font getFont() {
		return font;
	}

	public final void setFont(Font font) {
		this.font = font;
	}

	public final Color getColor() {
		return color;
	}

	public final void setColor(Color color) {
		this.color = color;
	}

	public final int getLinespacing() {
		return linespacing;
	}

	public final void setLinespacing(int linespacing) {
		this.linespacing = linespacing;
	}

	public final boolean isIssmooth() {
		return issmooth;
	}

	public final void setIssmooth(boolean issmooth) {
		this.issmooth = issmooth;
	}

}
