/**
 * 
 */
package com.maqiao.was.pictureOverlay.specialPictures.qrCode;

/**
 * 工具方法类
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
public final class QR_CODEUtils {
	@Deprecated
	public static final Postion postion(int width, int height, Direction d) {
		switch (d) {
		case Tl:
			return new Postion(0, 0);
		case Tc:
			return new Postion(width / 2, 0);
		case Tr:
			return new Postion(width, 0);
		case Cl:
			return new Postion(0, height / 2);
		case Cr:
			return new Postion(width, height / 2);
		case Ll:
			return new Postion(0, height);
		case Lc:
			return new Postion(width / 2, height);
		case Lr:
			return new Postion(width, height);
		case CORE:
			return new Postion(width / 2, height / 2);
		default:
			return new Postion(0, 0);
		}
	}

	/**
	 * 定位
	 * @author Sunjian
	 * @version 1.0
	 * @since jdk1.7
	 */
	@Deprecated
	public static class Postion {
		int x;
		int y;

		public Postion(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	/**
	 * 方形方向
	 * @author Sunjian
	 * @version 1.0
	 * @since jdk1.7
	 */
	@Deprecated
	public enum Direction {
		/** 左上角 */
		Tl, /** 上中点 */
		Tc, /** 上右角 */
		Tr, /** 中左点 */
		Cl, /** 中右点 */
		Cr, /** 左下角 */
		Ll, /** 左中点 */
		Lc, /** 右下角 */
		Lr, /** 中心点 */
		CORE
	}
}
