/**
 * 
 */
package com.maqiao.was.pictureOverlay.specialPictures.qrCode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.maqiao.was.pictureOverlay.MQLayerSpecialPicture;
import com.maqiao.was.pictureOverlay.MQUtils;
import com.maqiao.was.pictureOverlay.specialPictures.InterfaceSpecialPicture;

/**
 * SpecialPictureID:1 二维码
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
@Service("specialPictureQR_CODE")
@Scope("singleton")
public class QR_CODE implements InterfaceSpecialPicture {
	/**
	 * 二维码地址
	 */
	String url = "";

	int size = 0;

	int margin = 0;

	int errorlevel = 0;

	Color bgcolor = null;

	ColorStyle cs = null;

	/*
	 * (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.specialPictures.InterfaceSpecialPicture#getSpecialPictureID()
	 */
	@Override
	public int getSpecialPictureID() {
		return 2;
	}

	/*
	 * (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.specialPictures.InterfaceSpecialPicture#getBufferedImage(com.maqiao.was.pictureOverlay.MQLayerPicture)
	 */
	@Override
	public BufferedImage getBufferedImage(MQLayerSpecialPicture mqLayerSpecialPicture, int index) {
		setParameter(mqLayerSpecialPicture, index);
		return makeBufferedImage();
	}

	/*
	 * (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.specialPictures.InterfaceSpecialPicture#setParameter(com.maqiao.was.pictureOverlay.MQLayerPicture)
	 */
	public void setParameter(MQLayerSpecialPicture mqLayerSpecialPicture, int index) {
		this.bgcolor = mqLayerSpecialPicture.getParameter().getBgcolor();
		this.size = mqLayerSpecialPicture.getMqpo().getRequestInt(index, "sp.qrcode.size", 100);
		this.url = mqLayerSpecialPicture.getMqpo().getRequest(index, "sp.qrcode.url");
		this.margin = mqLayerSpecialPicture.getMqpo().getRequestInt(index, "sp.qrcode.margin", 0);
		ColorStyle cs = new ColorStyle();
		cs.setMqLayerSpecialPicture(mqLayerSpecialPicture);
		cs.setIndex(index);
		this.errorlevel = mqLayerSpecialPicture.getMqpo().getRequestInt(index, "sp.qrcode.errorlevel", 0);
		int style = mqLayerSpecialPicture.getMqpo().getRequestInt(index, "sp.qrcode.style", 0);
		cs.setStyle(style);
		String colorarray = mqLayerSpecialPicture.getMqpo().getRequest(index, "sp.qrcode.colorarray");
		List<Color> colorList = MQUtils.getColorList(colorarray);
		cs.setColorList(colorList);
		Color color = mqLayerSpecialPicture.getParameter().getColor();
		cs.setColor(color);
		this.cs = cs;
	}

	/**
	 * 返回容错率
	 * @return ErrorCorrectionLevel
	 */
	private ErrorCorrectionLevel getErrorCorrectionLevel() {
		if (errorlevel < 0) return ErrorCorrectionLevel.H;
		switch (errorlevel % 4) {
		case 0:
			return ErrorCorrectionLevel.L;
		case 1:
			return ErrorCorrectionLevel.M;
		case 2:
			return ErrorCorrectionLevel.Q;
		case 3:
			return ErrorCorrectionLevel.H;
		}
		return ErrorCorrectionLevel.H;
	}

	public final BufferedImage makeBufferedImage() {
		try {
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			hints.put(EncodeHintType.ERROR_CORRECTION, getErrorCorrectionLevel());
			hints.put(EncodeHintType.MARGIN, 0);
			BitMatrix bitMatrix = multiFormatWriter.encode(url, BarcodeFormat.QR_CODE, size, size, hints);
			//bitMatrix = updateBit(bitMatrix, margin);
			int width = bitMatrix.getWidth();
			int height = bitMatrix.getHeight();
			cs.setWidth(width);
			cs.setHeight(height);
			BufferedImage image = getBufferedImageStyle(cs);
			if (image == null) return null;
			for (int x = 0; x < size; x++) {
				for (int y = 0; y < size; y++) {
					if (!bitMatrix.get(x, y)) {
						image.setRGB(x, y, getBgColor(image.getRGB(x, y)));
					}
				}
			}
			return image;
		} catch (WriterException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 得到背景的颜色
	 * @param rgb int
	 * @return int
	 */
	int getBgColor(int rgb) {
		if (bgcolor == null) return MQUtils.getTransparent(rgb);
		return bgcolor.getRGB();
	}

	static BitMatrix updateBit(BitMatrix matrix, int margin) {
		int tempM = margin * 2;
		int[] rec = matrix.getEnclosingRectangle(); // 获取二维码图案的属性
		int resWidth = rec[2] + tempM;
		int resHeight = rec[3] + tempM;
		BitMatrix resMatrix = new BitMatrix(resWidth, resHeight); // 按照自定义边框生成新的BitMatrix
		resMatrix.clear();
		for (int i = margin; i < resWidth - margin; i++) { // 循环，将二维码图案绘制到新的bitMatrix中
			for (int j = margin; j < resHeight - margin; j++) {
				if (matrix.get(i - margin + rec[0], j - margin + rec[1])) {
					resMatrix.set(i, j);
				}
			}
		}
		return resMatrix;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QR_CODE [cs=");
		builder.append(cs);
		builder.append(", url=");
		builder.append(url);
		builder.append(", size=");
		builder.append(size);
		builder.append(", margin=");
		builder.append(margin);
		builder.append(", bgcolor=");
		builder.append(bgcolor);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * 得到全黑色的图片
	 * @param cs ColorStyle
	 * @return BufferedImage
	 */
	public static final BufferedImage getBufferedImageStyle_BLACK(ColorStyle cs) {
		BufferedImage image = new BufferedImage(cs.width, cs.height, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = image.createGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, cs.width, cs.height);
		g.dispose();
		return image;
	}

	/**
	 * 特殊图形(二维码)不同样式
	 */
	public static final List<InterfaceQRCode> QRCodeList = new ArrayList<InterfaceQRCode>();

	/**
	 * 从缓存中得到不同的二维码的样式
	 * @param cs ColorStyle
	 * @return BufferedImage
	 */
	public static final BufferedImage getBufferedImageStyle(ColorStyle cs) {
		if (cs.colorList == null || cs.colorList.size() == 0 || cs.style <= 0) return getBufferedImageStyle_BLACK(cs);
		for (int i = 0, len = QRCodeList.size(); i < len; i++) {
			InterfaceQRCode e = QRCodeList.get(i);
			if (e.isStyle(cs.style)) return e.getBufferedImageStyle(cs);
		}
		return getBufferedImageStyle_BLACK(cs);
	}
}
