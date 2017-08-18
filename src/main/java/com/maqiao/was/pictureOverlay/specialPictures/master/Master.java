/**
 * 
 */
package com.maqiao.was.pictureOverlay.specialPictures.master;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.maqiao.was.pictureOverlay.MQLayerSpecialPicture;
import com.maqiao.was.pictureOverlay.specialPictures.InterfaceSpecialPicture;

/**
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
@Service("specialPictureMaster")
@Scope("singleton")
public class Master  implements InterfaceSpecialPicture {

	/* (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.specialPictures.InterfaceSpecialPicture#getSpecialPictureID()
	 */
	@Override
	public int getSpecialPictureID() {
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.specialPictures.InterfaceSpecialPicture#getBufferedImage(com.maqiao.was.pictureOverlay.MQPO, int)
	 */
	@Override
	public BufferedImage getBufferedImage(MQLayerSpecialPicture mqLayerSpecialPicture, int index) {
		int width=mqLayerSpecialPicture.getParameter().getWidth();
		int height=mqLayerSpecialPicture.getParameter().getHeight();
		Color bgColor=mqLayerSpecialPicture.getParameter().getBgcolor();
		int TYPE = BufferedImage.TYPE_INT_RGB;
		if (bgColor==null) TYPE = BufferedImage.TYPE_4BYTE_ABGR;
		BufferedImage bi = new BufferedImage(width, height, TYPE);
		Graphics2D g = bi.createGraphics();
		if (bgColor!=null) {
			g.setColor(bgColor);
			g.fillRect(0, 0, width, height);
		}
		g.dispose();
		return bi;		
	}
}
