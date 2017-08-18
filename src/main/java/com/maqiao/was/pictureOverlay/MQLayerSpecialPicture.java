/**
 * 
 */
package com.maqiao.was.pictureOverlay;

import java.util.HashMap;
import java.util.Map;

import com.maqiao.was.pictureOverlay.specialPictures.InterfaceSpecialPicture;

/**
 * 其它特殊图片
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
public class MQLayerSpecialPicture extends MQAbstractLayer {
	/**
	 * 特殊图片
	 * 1:底版图片 只有背景尺寸的图层，或尺寸的透明图层
	 * 2:二维码图片
	 */
	int specialid = 0;

	/**
	 * @param mqpo
	 * @param index
	 */
	public MQLayerSpecialPicture(MQPO mqpo, int index) {
		super(mqpo, index);
		this.specialid = mqpo.getRequestInt(index, "sp.id", 0);
	}

	/*
	 * (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.MQAbstractLayer#isSafe()
	 */
	@Override
	public boolean isSafe() {
		return specialid > 0;
	}

	/*
	 * (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.MQAbstractLayer#isState()
	 */
	@Override
	public int isState() {
		return 3;
	}

	/*
	 * (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.MQAbstractLayer#bufferImageFormat()
	 */
	@Override
	public void bufferImageFormat() {
		if (specialid <= 0) return;
		InterfaceSpecialPicture e=ACC_SPExtendMap.get(specialid);
		if(e==null)return;
		this.buffImg = e.getBufferedImage(this, index);
	}

	public static MQLayerSpecialPicture getMQLayerSpecialPicture(MQPO mqpo, int index) {
		if (mqpo == null) return null;
		try {
			MQLayerSpecialPicture e = new MQLayerSpecialPicture(mqpo, index);
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
	/**
	 * 特殊图片缓存
	 */
	public static final Map<Integer, InterfaceSpecialPicture> ACC_SPExtendMap = new HashMap<Integer, InterfaceSpecialPicture>();

}
