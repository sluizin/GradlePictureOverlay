/**
 * 
 */
package com.maqiao.was.pictureOverlay.specialPictures.qrCode.style;

import java.awt.image.BufferedImage;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.maqiao.was.pictureOverlay.MQLayerPicture;
import com.maqiao.was.pictureOverlay.specialPictures.qrCode.InterfaceQRCode;
import com.maqiao.was.pictureOverlay.specialPictures.qrCode.ColorStyle;

/**
 * 从url或上传图片方式得到 BufferedImage
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
@Service("style100_HrefMethodFile")
@Scope("singleton")
public class Style100_HrefMethodFile implements InterfaceQRCode {

	/*
	 * (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.specialPictures.qrCode.InterfaceQRCode#getKey()
	 */
	@Override
	public String getKey() {
		return "100";
	}

	/*
	 * (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.specialPictures.qrCode.InterfaceQRCode#isStyle(int)
	 */
	@Override
	public boolean isStyle(int style) {
		return style == 100;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.maqiao.was.pictureOverlay.specialPictures.qrCode.InterfaceQRCode#getBufferedImageStyle(com.maqiao.was.pictureOverlay.specialPictures.qrCode.ColorStyle
	 * )
	 */
	@Override
	public BufferedImage getBufferedImageStyle(ColorStyle cs) {
		return getBufferedImageStyle_100(cs);
	}

	/**
	 * 从url或上传图片方式得到 BufferedImage
	 * @param cs ColorStyle
	 * @return BufferedImage
	 */
	public static final BufferedImage getBufferedImageStyle_100(ColorStyle cs) {
		BufferedImage image = null;
		MQLayerPicture layerpicutre = null;
		layerpicutre = MQLayerPicture.getMQLayerPictureByTypeHttp(cs.getMqLayerSpecialPicture().getMqpo(), cs.getIndex());
		if (layerpicutre != null) {
			image = layerpicutre.getBufferedImage();
			if (image != null) return image;
		}
		layerpicutre = MQLayerPicture.getMQLayerPictureByTypeMultiPartFile(cs.getMqLayerSpecialPicture().getMqpo(), cs.getIndex());
		if (layerpicutre != null) {
			image = layerpicutre.getBufferedImage();
			if (image != null) return image;
		}
		return null;
	}
}
