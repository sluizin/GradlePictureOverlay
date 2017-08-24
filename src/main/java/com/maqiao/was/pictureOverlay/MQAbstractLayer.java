/**
 * 
 */
package com.maqiao.was.pictureOverlay;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.maqiao.was.pictureOverlay.bufferedImage.RenderingBufferedImage;
import com.maqiao.was.pictureOverlay.bufferedImage.ZoomBufferedImage;

/**
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
public abstract class MQAbstractLayer {
	MQPO mqpo = null;
	int index = -1;
	/** 有效性 */
	boolean valid = false;
	/** 图层关键字，图层唯一关键字，如果缓存中有此关键缓存，则直接调用 */
	String mainkey = "";
	/**
	 * 设置图层x,y,width,height,align,valign等
	 */
	MQLayerParameter parameter = null;
	/**
	 * 是否已经把buffImg格式化
	 */
	boolean isFormat = false;
	/**
	 * 是否是背景
	 */
	boolean isBackground = false;
	/**
	 * 是否是后期对BufferedImage进行操作
	 */
	int laterStage = 0;
	/**
	 * 图片图层
	 */
	BufferedImage buffImg = null;
	/**
	 * 是否静态保存MQAbstractLayer 注意：背景图不能使用缓存。
	 */
	boolean iscache = false;

	/**
	 * 是否生成临时图层文件，以 {filename}_pic_{index}.png进行保存
	 */
	boolean issave = false;

	public MQAbstractLayer(MQPO mqpo, int index) {
		this.mqpo = mqpo;
		this.index = index;
		initialization();
		mainkey = "pic_" + index;
	}

	/**
	 * 初始化
	 * @param mqpo MQPO
	 * @param index int
	 */
	public void initialization() {
		if (!mqpo.isSafe()) return;
		if (index < 0 || index >= mqpo.count()) return;
		boolean valid = mqpo.getRequestBoolean(index, MQConst.ACC_ValidKey, true);
		if (!valid) return;
		parameter = new MQLayerParameter(this.mqpo, this.index);
		this.valid = valid;
		isBackground = mqpo.getRequestBoolean(index, "isBG", false);
		issave = mqpo.getRequestBoolean(index, "issave", false);
		laterStage = mqpo.getRequestInt(index, "laterStage", 0);
	}

	/**
	 * 图层合成 把本图层合并到buffImgBG中
	 * @param buffImgBG BufferedImage
	 * @return BufferedImage
	 */
	public BufferedImage merge(BufferedImage buffImgBG) {
		if (buffImgBG == null || (!isAvailability())) return buffImgBG;
		switch(laterStage){
		case 1:
			return ZoomBufferedImage.getBufferedImageZoom(buffImgBG, this);
		}
		BufferedImage newBuffImg = this.getBufferedImage();
		if (newBuffImg == null) return buffImgBG;
		int width = parameter.width;
		int height = parameter.height;
		if (width == 0) width = newBuffImg.getWidth();
		if (height == 0) height = newBuffImg.getHeight();
		Graphics2D g2d = buffImgBG.createGraphics();
		g2d.drawImage(newBuffImg, parameter.x, parameter.y, width, height, null);// 在指定区域，把本图层绘制
		g2d.dispose();// 释放图形上下文使用的系统资源
		return buffImgBG;
	}

	/**
	 * 图层状态是否有效与安全
	 * @return boolean
	 */
	public abstract boolean isSafe();

	/**
	 * 图层是否有效
	 * @return boolean
	 */
	public boolean isAvailability() {
		if (laterStage > 0) return true;
		if (getBufferedImage() == null) return false;
		if (parameter.width <= 0 || parameter.height <= 0) return false;
		return isSafe();
	}

	/**
	 * 判断是图层状态，0:null 1:图片 2:文字 3:其它特殊图片
	 * @return int
	 */
	public abstract int isState();

	/**
	 * 得到图片的流
	 * @return BufferedImage
	 */
	public BufferedImage getBufferedImage() {
		if (isFormat) return buffImg;
		isFormat = true;
		bufferImageFormat();
		/* 后期渲染 */
		if (parameter.renderingStyle != null) {
			MQLogger.loggerInfo("后期渲染:" + this.mainkey);
			this.buffImg = RenderingBufferedImage.getBufferedImage(this.buffImg, parameter);
		}
		return buffImg;
	}

	/**
	 * 对图片进行格式化
	 */
	public abstract void bufferImageFormat();

	/**
	 * 生成缓存文件 path:"D:/data/shopimage/image/shop/1426/poster/banner"
	 * @param path String
	 */
	public void makeOwnFile(String path) {
		if (!issave) return;
		BufferedImage buffImg = getBufferedImage();
		if (buffImg==null) return;
		try {
			File file = new File(path + "_" + this.mainkey + MQConst.ACC_FileExt);
			ImageIO.write(buffImg, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public final MQPO getMqpo() {
		return mqpo;
	}

	public final void setMqpo(MQPO mqpo) {
		this.mqpo = mqpo;
	}

	public final int getIndex() {
		return index;
	}

	public final void setIndex(int index) {
		this.index = index;
	}

	public final boolean isValid() {
		return valid;
	}

	public final void setValid(boolean valid) {
		this.valid = valid;
	}

	public final String getMainkey() {
		return mainkey;
	}

	public final void setMainkey(String mainkey) {
		this.mainkey = mainkey;
	}

	public final MQLayerParameter getParameter() {
		return parameter;
	}

	public final void setParameter(MQLayerParameter parameter) {
		this.parameter = parameter;
	}

	public final boolean isFormat() {
		return isFormat;
	}

	public final void setFormat(boolean isFormat) {
		this.isFormat = isFormat;
	}

	public final boolean isBackground() {
		return isBackground;
	}

	public final void setBackground(boolean isBackground) {
		this.isBackground = isBackground;
	}

	public final boolean isIssave() {
		return issave;
	}

	public final void setIssave(boolean issave) {
		this.issave = issave;
	}

	public final boolean isIscache() {
		return iscache;
	}

	public final void setIscache(boolean iscache) {
		this.iscache = iscache;
	}

	public final int getLaterStage() {
		return laterStage;
	}

	public final void setLaterStage(int laterStage) {
		this.laterStage = laterStage;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MQAbstractLayer [mqpo=");
		builder.append(mqpo);
		builder.append(", index=");
		builder.append(index);
		builder.append(", valid=");
		builder.append(valid);
		builder.append(", mainkey=");
		builder.append(mainkey);
		builder.append(", parameter=");
		builder.append(parameter);
		builder.append(", isFormat=");
		builder.append(isFormat);
		builder.append(", isBackground=");
		builder.append(isBackground);
		builder.append(", laterStage=");
		builder.append(laterStage);
		builder.append(", buffImg=");
		builder.append(buffImg);
		builder.append(", iscache=");
		builder.append(iscache);
		builder.append(", issave=");
		builder.append(issave);
		builder.append("]");
		return builder.toString();
	}

}
