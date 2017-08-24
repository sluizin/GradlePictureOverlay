/**
 * 
 */
package com.maqiao.was.pictureOverlay;

import java.awt.Color;

/**
 * 图形: 图片变形 > 截断原图 > 比例缩放
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
public class MQLayerParameter {
	/** 图片左上角x值 */
	int x = 0;
	/** 图片左上角y值 */
	int y = 0;
	int width = 0;
	int height = 0;
	/**
	 * 图片与文件变形
	 * 0:无变化
	 * 1:[图片]圆形
	 * 2:[图片]圆滑四角
	 */
	int shape = 0;
	/**
	 * 颜色
	 */
	Color color = null;
	/**
	 * 背景色
	 */
	Color bgcolor = null;
	/**
	 * 左右对齐方式：1:中间 2:右侧 默认左对齐
	 */
	int align = 0;
	/**
	 * 上下对齐方式：1:中间 2:下侧 默认上对齐
	 */
	int valign = 0;
	/**
	 * 后期渲染
	 */
	MQRenderingStyle renderingStyle = null;

	/**
	 * 初始化
	 * @param mqpo MQPO
	 * @param index int
	 */
	public void initialization(MQPO mqpo, int index) {
		if (!mqpo.isSafe()) return;
		if (index < 0 || index >= mqpo.count()) return;
		x = mqpo.getRequestInt(index, "x");
		y = mqpo.getRequestInt(index, "y");
		width = mqpo.getRequestInt(index, "width");
		height = mqpo.getRequestInt(index, "height");
		color = mqpo.getRequestColor(index, "color", null);
		bgcolor = mqpo.getRequestColor(index, "bgcolor", null);
		align = mqpo.getRequestInt(index, "align");
		valign = mqpo.getRequestInt(index, "valign");
		shape = mqpo.getRequestInt(index, "shape", 0);
		int renderingid = mqpo.getRequestInt(index, "rendering.id", 0);
		if (renderingid > 0) {
			this.renderingStyle = new MQRenderingStyle(mqpo,index);
		}
	}

	public MQLayerParameter(MQPO mqpo, int index) {
		initialization(mqpo, index);
	}

	public final int getX() {
		return x;
	}

	public final void setX(int x) {
		this.x = x;
	}

	public final int getY() {
		return y;
	}

	public final void setY(int y) {
		this.y = y;
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

	public final int getShape() {
		return shape;
	}

	public final void setShape(int shape) {
		this.shape = shape;
	}

	public final Color getBgcolor() {
		return bgcolor;
	}

	public final void setBgcolor(Color bgcolor) {
		this.bgcolor = bgcolor;
	}

	public final int getAlign() {
		return align;
	}

	public final void setAlign(int align) {
		this.align = align;
	}

	public final int getValign() {
		return valign;
	}

	public final void setValign(int valign) {
		this.valign = valign;
	}

	public final Color getColor() {
		return color;
	}

	public final void setColor(Color color) {
		this.color = color;
	}

	public final MQRenderingStyle getRenderingStyle() {
		return renderingStyle;
	}

	public final void setRenderingStyle(MQRenderingStyle renderingStyle) {
		this.renderingStyle = renderingStyle;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MQLayerParameter [x=");
		builder.append(x);
		builder.append(", y=");
		builder.append(y);
		builder.append(", width=");
		builder.append(width);
		builder.append(", height=");
		builder.append(height);
		builder.append(", shape=");
		builder.append(shape);
		builder.append(", color=");
		builder.append(color);
		builder.append(", bgcolor=");
		builder.append(bgcolor);
		builder.append(", align=");
		builder.append(align);
		builder.append(", valign=");
		builder.append(valign);
		builder.append("]");
		return builder.toString();
	}

}
