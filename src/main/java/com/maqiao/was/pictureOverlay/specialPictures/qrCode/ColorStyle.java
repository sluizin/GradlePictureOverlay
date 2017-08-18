/**
 * 
 */
package com.maqiao.was.pictureOverlay.specialPictures.qrCode;

import java.awt.Color;
import java.util.List;

import com.maqiao.was.pictureOverlay.MQLayerSpecialPicture;

/**
 * 颜色接收对象，例如：渐近色或多种色块的接收参数值
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
public final class ColorStyle {

	/**
	 * 样式 1 / 2 / 3
	 */
	int style = 0;
	List<Color> colorList = null;
	Color color = null;
	int width = 0;
	int height = 0;
	MQLayerSpecialPicture mqLayerSpecialPicture = null;
	int index = 0;

	public final int getStyle() {
		return style;
	}

	public final void setStyle(int style) {
		this.style = style;
	}

	public final List<Color> getColorList() {
		return colorList;
	}

	public final void setColorList(List<Color> colorList) {
		this.colorList = colorList;
	}

	public final Color getColor() {
		return color;
	}

	public final void setColor(Color color) {
		this.color = color;
	}

	public final int getWidth() {
		return width;
	}

	public final void setWidth(int width) {
		this.width = width;
	}

	public final int getHeight() {
		return height;
	}

	public final void setHeight(int height) {
		this.height = height;
	}

	public final MQLayerSpecialPicture getMqLayerSpecialPicture() {
		return mqLayerSpecialPicture;
	}

	public final void setMqLayerSpecialPicture(MQLayerSpecialPicture mqLayerSpecialPicture) {
		this.mqLayerSpecialPicture = mqLayerSpecialPicture;
	}

	public final int getIndex() {
		return index;
	}

	public final void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ColorStyle [style=");
		builder.append(style);
		builder.append(", colorList=");
		builder.append(colorList);
		builder.append(", color=");
		builder.append(color);
		builder.append(", width=");
		builder.append(width);
		builder.append(", height=");
		builder.append(height);
		builder.append("]");
		return builder.toString();
	}
}
