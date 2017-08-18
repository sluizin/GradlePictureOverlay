/**
 * 
 */
package com.maqiao.was.pictureOverlay;

import java.awt.Color;
import java.util.List;

/**
 * 后期渲染参数
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
public final class MQRenderingStyle {
	MQPO mqpo = null;
	int index = 0;
	int style = 0;
	List<Color> colorList = null;
	boolean isTransparent = false;
	boolean isWhite = false;
	int width = 0;
	int height = 0;
	public MQRenderingStyle(){
		
	}
	public MQRenderingStyle(MQPO mqpo,int index){
		this.mqpo=mqpo;
		this.index=index;
		initialization();
	}
	public void initialization() {
		this.style = mqpo.getRequestInt(index, "rendering.id", 0);
		this.colorList = mqpo.getRequestColorList(index, "rendering.colorarray");
		this.isTransparent = mqpo.getRequestBoolean(index, "rendering.istransparent", false);
		this.isWhite = mqpo.getRequestBoolean(index, "rendering.iswhite", false);

	}

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

	public final int getIndex() {
		return index;
	}

	public final void setIndex(int index) {
		this.index = index;
	}

	public final MQPO getMqpo() {
		return mqpo;
	}

	public final void setMqpo(MQPO mqpo) {
		this.mqpo = mqpo;
	}

	public final boolean isTransparent() {
		return isTransparent;
	}

	public final void setTransparent(boolean isTransparent) {
		this.isTransparent = isTransparent;
	}

	public final boolean isWhite() {
		return isWhite;
	}

	public final void setWhite(boolean isWhite) {
		this.isWhite = isWhite;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MQRenderingStyle [mqpo=");
		builder.append(mqpo);
		builder.append(", index=");
		builder.append(index);
		builder.append(", style=");
		builder.append(style);
		builder.append(", colorList=");
		builder.append(colorList);
		builder.append(", isTransparent=");
		builder.append(isTransparent);
		builder.append(", isWhite=");
		builder.append(isWhite);
		builder.append(", width=");
		builder.append(width);
		builder.append(", height=");
		builder.append(height);
		builder.append("]");
		return builder.toString();
	}

}
