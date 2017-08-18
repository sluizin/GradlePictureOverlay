/**
 * 
 */
package com.maqiao.was.pictureOverlay;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import com.maqiao.was.pictureOverlay.pictureShape.InterfaceShape;

/**
 * 设置图片相关操作
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
public final class MQDrawShapePic {
	/**
	 * 对图片进行操作
	 * @param mqLayerPicture MQLayerPicture
	 * @param parameter MQLayerParameter
	 * @return BufferedImage
	 */
	static BufferedImage Graphics2DPicture(MQLayerPicture mqLayerPicture, MQLayerParameter parameter) {
		BufferedImage buffImg = mqLayerPicture.buffImg;
		/* 图片变形 */
		if (parameter.shape != 0) {
			InterfaceShape e = MQConst.ACC_PictureShapeMap.get(parameter.shape);
			if (e != null) { {
				e.setParameter(mqLayerPicture);
				return e.getBufferedImage(mqLayerPicture); }
			}
		}
		PicPointState p = new PicPointState(0, 0, parameter.width, parameter.height);
		if (mqLayerPicture.intercept) {
			buffImg = Graphics2DPictureIsFull(buffImg, p);
			return buffImg;
		}
		BufferedImage bufferedImage = Graphics2DPictureAlignment(buffImg, p, mqLayerPicture);
		return bufferedImage;
	}

	/**
	 * 画圆，是否需要透明
	 * @param buffImg BufferedImage
	 * @param transparent boolean
	 * @return BufferedImage
	 */
	public static final BufferedImage Graphics2DPictureCircular(final BufferedImage buffImg, final boolean transparent) {
		int width = buffImg.getWidth();
		int height = buffImg.getHeight();
		int TYPE = BufferedImage.TYPE_INT_RGB;
		if (transparent) TYPE = BufferedImage.TYPE_4BYTE_ABGR;
		BufferedImage bi = new BufferedImage(width, height, TYPE);
		Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, width, height);
		Graphics2D g = bi.createGraphics();
		if (!transparent) {
			g.setComposite(AlphaComposite.getInstance(10, 1.0f));
			g.fill(new Rectangle(width, height));
		}
		g.setClip(shape);
		g.drawImage(buffImg, 0, 0, null);
		g.dispose();
		return bi;
	}

	/**
	 * 把图片比例缩放
	 * @param buffImg BufferedImage
	 * @param p PicPointState
	 * @param parameter parameter
	 * @param bgcolor Color
	 * @return BufferedImage
	 */
	static BufferedImage Graphics2DPictureAlignment(BufferedImage buffImg, PicPointState p, MQLayerPicture mqLayerPicture) {
		PicPointState e = null;
		if (mqLayerPicture.autoscale) e = changeNotFull(mqLayerPicture.parameter, p, buffImg.getWidth(), buffImg.getHeight());
		else e = p;
		int TYPE = BufferedImage.TYPE_4BYTE_ABGR;
		if (mqLayerPicture.parameter.bgcolor != null) TYPE = BufferedImage.TYPE_INT_RGB;
		BufferedImage bi = new BufferedImage(p.width, p.height, TYPE);
		Graphics2D g = bi.createGraphics();
		if (mqLayerPicture.parameter.bgcolor != null) {
			g.setColor(mqLayerPicture.parameter.bgcolor);
			g.fillRect(0, 0, p.width, p.height);
		}
		g.drawImage(buffImg, e.x, e.y, e.width, e.height, null);
		g.dispose();
		return bi;
	}

	/**
	 * 图片全填充，并截取部分图片
	 * @param buffImg BufferedImage
	 * @param isFull boolean
	 * @param p PicPointState
	 * @param align int
	 * @param bgcolor Color
	 * @return BufferedImage
	 */
	static BufferedImage Graphics2DPictureIsFull(BufferedImage buffImg, PicPointState p) {
		PicPointState e = changeIsFull(p, buffImg.getWidth(), buffImg.getHeight());
		BufferedImage bi = new BufferedImage(p.width, p.height, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = bi.createGraphics();
		g.drawImage(buffImg, e.x, e.y, e.width, e.height, null);
		g.dispose();
		return bi;
	}

	/**
	 * 定位图片的信息
	 * @author Sunjian
	 * @version 1.0
	 * @since jdk1.7
	 */
	static class PicPointState {
		int x = 0;
		int y = 0;
		int width = 0;
		int height = 0;

		public PicPointState() {

		}

		public PicPointState(int x, int y, int width, int height) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("PicPointState [x=");
			builder.append(x);
			builder.append(", y=");
			builder.append(y);
			builder.append(", width=");
			builder.append(width);
			builder.append(", height=");
			builder.append(height);
			builder.append("]");
			return builder.toString();
		}
	}

	/**
	 * 计算出图片填入框中，出现两侧空白时的信息
	 * @param parameter parameter
	 * @param p PicPointState
	 * @param picWidth int
	 * @param picHeight int
	 * @return PicPointState
	 */
	static PicPointState changeNotFull(MQLayerParameter parameter, PicPointState p, int picWidth, int picHeight) {
		PicPointState e = new PicPointState();
		int newPicWidth = p.width, newPicHeight = p.height;
		float perW = (float) p.width / (float) picWidth;
		float perH = (float) p.height / (float) picHeight;
		if (perW > perH) {
			newPicWidth = (int) (picWidth * perH);
			newPicHeight = p.height;
			/* 高大于长 */
			e.y = p.y;
			switch (parameter.align) {
			case 1:
				e.x = p.x + (p.width - newPicWidth) / 2;/* 中间 */
				break;
			case 2:
				e.x = p.x + (p.width - newPicWidth);/* 右侧 */
				break;
			default:
				e.x = p.x;/* 左侧 */
			}
		} else {
			newPicWidth = p.width;
			newPicHeight = (int) (picHeight * perW);
			/* 长大于高 */
			e.x = p.x;
			switch (parameter.valign) {
			case 1:
				e.y = p.y + (p.height - newPicHeight) / 2;/* 中间 */
				break;
			case 2:
				e.y = p.y + (p.height - newPicHeight);/* 底部 */
				break;
			default:
				e.y = p.y;/* 上底 */
			}
		}
		e.width = newPicWidth;
		e.height = newPicHeight;
		return e;

	}

	/**
	 * 计算出图片填入框中，出现两侧空白时的信息
	 * @param align int
	 * @param p PicPointState
	 * @param picWidth int
	 * @param picHeight int
	 * @return PicPointState
	 */
	static PicPointState changeNotFull(int align, PicPointState p, int picWidth, int picHeight) {
		PicPointState e = new PicPointState();
		int newPicWidth = p.width, newPicHeight = p.height;
		float perW = (float) p.width / (float) picWidth;
		float perH = (float) p.height / (float) picHeight;
		if (perW > perH) {
			newPicWidth = (int) (picWidth * perH);
			newPicHeight = p.height;
			/* 高大于长 */
			e.y = p.y;
			switch (align) {
			case 1:
				e.x = p.x + (p.width - newPicWidth) / 2;/* 中间 */
				break;
			case 2:
				e.x = p.x + (p.width - newPicWidth);/* 右侧 */
				break;
			default:
				e.x = p.x;/* 左侧 */
			}
		} else {
			newPicWidth = p.width;
			newPicHeight = (int) (picHeight * perW);
			/* 长大于高 */
			e.x = p.x;
			switch (align) {
			case 1:
				e.y = p.y + (p.height - newPicHeight) / 2;/* 中间 */
				break;
			case 2:
				e.y = p.y + (p.height - newPicHeight);/* 底部 */
				break;
			default:
				e.y = p.y;/* 上底 */
			}
		}
		e.width = newPicWidth;
		e.height = newPicHeight;
		return e;
	}

	/**
	 * 计算出图片全填充时的信息
	 * @param p PicPointState
	 * @param picWidth int
	 * @param picHeight int
	 * @return PicPointState
	 */
	static PicPointState changeIsFull(PicPointState p, int picWidth, int picHeight) {
		PicPointState e = new PicPointState();
		float perW = (float) p.width / (float) picWidth;
		float perH = (float) p.height / (float) picHeight;
		float per = (perW < perH) ? perH : perW;
		e.x = p.x;
		e.y = p.y;
		e.width = (int) (picWidth * per);
		e.height = (int) (picHeight * per);
		return e;
	}

	public static void main(String[] args) {
		BufferedImage bi1;
		try {
			bi1 = ImageIO.read(new File("d:/data/logo.png"));
			// 根据需要是否使用 BufferedImage.TYPE_INT_ARGB
			BufferedImage bi2 = new BufferedImage(bi1.getWidth(), bi1.getHeight(), BufferedImage.TYPE_INT_RGB);

			Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi1.getWidth(), bi1.getHeight());

			Graphics2D g2 = bi2.createGraphics();
			g2.setClip(shape);
			g2.drawImage(bi1, 0, 0, null);
			g2.dispose();

			ImageIO.write(bi2, "png", new File(MQConst.path + "def.png"));

			ImageIO.write(Graphics2DPictureCircular(bi1, true), "png", new File(MQConst.path + "def2.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
