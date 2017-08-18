/**
 * 
 */
package com.maqiao.was.pictureOverlay.specialPictures.qrCode.style;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.maqiao.was.pictureOverlay.specialPictures.qrCode.InterfaceQRCode;
import com.maqiao.was.pictureOverlay.specialPictures.qrCode.ColorStyle;

/**
 * 得到左向右还是上向下的颜色块
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
@Service("style11_12_Block")
@Scope("singleton")
public class Style11_12_Block implements InterfaceQRCode {
	/* (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.specialPictures.qrCode.InterfaceQRCode#getKey()
	 */
	@Override
	public String getKey() {
		return "11_12";
	}

	/*
	 * (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.specialPictures.qrCode.InterfaceQRCode#isStyle(int)
	 */
	@Override
	public boolean isStyle(int style) {
		return style == 11 || style == 12;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.maqiao.was.pictureOverlay.specialPictures.qrCode.InterfaceQRCode#getBufferedImageStyle(com.maqiao.was.pictureOverlay.specialPictures.qrCode.ColorStyle
	 * )
	 */
	@Override
	public BufferedImage getBufferedImageStyle(ColorStyle cs) {
		int style = cs.getStyle();
		if (!isStyle(style)) return null;
		boolean isTransverse = false;
		if (style == 12) isTransverse = true;
		BufferedImage image = new BufferedImage(cs.getWidth(), cs.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
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

}
