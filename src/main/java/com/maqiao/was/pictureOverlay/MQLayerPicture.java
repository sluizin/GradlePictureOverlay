/**
 * 
 */
package com.maqiao.was.pictureOverlay;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
public final class MQLayerPicture extends MQAbstractLayer {
	/**
	 * 图片自动缩放
	 */
	boolean autoscale = false;
	/**
	 * 截取图片，只保留原图部分
	 */
	boolean intercept = false;

	/**
	 * @param mqpo MQPO
	 * @param index int
	 */
	public MQLayerPicture(MQPO mqpo, int index) {
		super(mqpo, index);
		this.autoscale = mqpo.getRequestBoolean(index, "autoscale", false);
		this.intercept = mqpo.getRequestBoolean(index, "intercept", false);
	}

	public static final MQLayerPicture getMQLayerPictureByTypeMultiPartFile(MQPO mqpo, int index) {
		String key = mqpo.getParaName(index, "file");
		if (key == null || key.length() == 0) return null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(mqpo.request.getSession().getServletContext());
		if (!multipartResolver.isMultipart(mqpo.request)) return null;
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) mqpo.request;
		MultipartFile file = multiRequest.getFile(key);
		if (file == null) return null;
		MQLayerPicture e = getMQLayerPictureByTypeMultiPartFile(mqpo, index, file);
		if (e == null) return null;
		return e;
	}

	public static final MQLayerPicture getMQLayerPictureByTypeHttp(MQPO mqpo, int index) {
		String http = mqpo.getRequest(index, "url");
		if (http == null || http.length() == 0) return null;
		return getMQLayerPictureByTypeHttp(mqpo, index, http);
	}

	public static final MQLayerPicture getMQLayerPictureByTypeHttp(MQPO mqpo, int index, String http) {
		try {
			URL url = new URL(http);
			return getMQLayerPictureByTypeUrl(mqpo, index, url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static final MQLayerPicture getMQLayerPictureByTypeUrl(MQPO mqpo, int index, URL url) {
		if (url == null || MQUtils.isConnect(url) == null) return null;
		try {
			MQLayerPicture e = new MQLayerPicture(mqpo, index);
			e.buffImg = ImageIO.read(url);
			e.buffImgInit();
			return e;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static final MQLayerPicture getMQLayerPictureByTypeMultiPartFile(MQPO mqpo, int index, MultipartFile file) {
		if (file == null) return null;
		try {
			MQLayerPicture e = new MQLayerPicture(mqpo, index);
			e.buffImg = ImageIO.read(file.getInputStream());
			return e;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static final MQLayerPicture getMQLayerPicture(MQPO mqpo, int index, File file) {
		if (file == null) return null;
		if (!file.isFile()) return null;
		try {
			MQLayerPicture e = new MQLayerPicture(mqpo, index);
			e.buffImg = ImageIO.read(file);
			e.buffImgInit();
			return e;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 设置默认值
	 */
	void buffImgInit() {
		if (buffImg == null) return;
		if (this.parameter.width == 0) this.parameter.width = this.buffImg.getWidth();
		if (this.parameter.height == 0) this.parameter.height = this.buffImg.getHeight();

	}

	/*
	 * (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.MQAbstractLayer#isSafe()
	 */
	@Override
	public boolean isSafe() {
		if (buffImg == null) return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.MQAbstractLayer#isPicture()
	 */
	@Override
	public int isState() {
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.MQAbstractLayer#getBufferedImage()
	 */
	@Override
	public void bufferImageFormat() {
		this.buffImg = MQDrawShapePic.Graphics2DPicture(this, parameter);
	}

	public final boolean isAutoscale() {
		return autoscale;
	}

	public final void setAutoscale(boolean autoscale) {
		this.autoscale = autoscale;
	}

	public final boolean isIntercept() {
		return intercept;
	}

	public final void setIntercept(boolean intercept) {
		this.intercept = intercept;
	}

}
