/**
 * 
 */
package com.maqiao.was.pictureOverlay;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.maqiao.was.envParaVariable.MQEnvParaVariable;
import com.maqiao.was.envParaVariable.MQEnvParaVariable.Env;

/**
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
public final class MQUtils {

	/**
	 * 判断是否是白色
	 * @param rgb int
	 * @return boolean
	 */
	public static final boolean isWhite(int rgb) {
		return rgb == Color.WHITE.getRGB();
	}

	/**
	 * 判断是否透明
	 * @param rgb int
	 * @return boolean
	 */
	public static final boolean isTransparent(int rgb) {
		return rgb >>> 24 != 255;
	}

	/**
	 * 设置透明
	 * @param bi BufferedImage
	 * @param x int
	 * @param y int
	 */
	public static final void setTransparent(BufferedImage bi, int x, int y) {
		if (x < 0 || x >= bi.getWidth()) return;
		if (y < 0 || y >= bi.getHeight()) return;
		bi.setRGB(x, y, bi.getRGB(x, y) & 0x00ffffff);/* 把生成的图片的定点变成透明 */
	}

	static Random rd1 = new Random();

	public MQUtils() {
		rd1 = new Random();
	}

	/**
	 * 得到min-max之间的随机数
	 * @param min int
	 * @param max int
	 * @return int
	 */
	public final static int getRndInt(final int min, final int max) {
		return min + rd1.nextInt(max - min + 1);
	}

	/**
	 * 判断字符是否是url
	 * @param urlStr String
	 * @return URL
	 */
	static synchronized URL isConnect(String urlStr) {
		if (urlStr == null || urlStr.length() <= 0) return null;
		try {
			return isConnect(new URL(urlStr));
		} catch (Exception e) {
			MQLogger.loggerInfo(e);
		}
		return null;
	}

	/**
	 * 判断url是否有效
	 * @param url URL
	 * @return URL
	 */
	static synchronized URL isConnect(URL url) {
		try {
			HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
			urlconnection.connect();
			if (urlconnection.getResponseCode() == 200) {
				BufferedInputStream bis = new BufferedInputStream(urlconnection.getInputStream());
				String typeStream = HttpURLConnection.guessContentTypeFromStream(bis);
				if (urlconnection.getContentLengthLong() > MQConst.ACC_PICTURE_CAPACITY_MAX) {
					MQLogger.loggerInfo("Error file capacity(" + MQConst.ACC_PICTURE_CAPACITY_MAX + "):" + urlconnection.getContentLengthLong());
					return null;
				}
				if (typeStream == null) MQLogger.loggerInfo("Error file type:typeStream=null");
				if (typeStream.substring(0, 6).indexOf("image/") == 0) return url;
				MQLogger.loggerInfo("Error file type:" + HttpURLConnection.guessContentTypeFromStream(bis));
			}
		} catch (Exception ex) {
			MQLogger.loggerInfo(ex);
		}
		return null;
	}

	/**
	 * 得到透明的数值
	 * @param rgb int
	 * @return int
	 */
	public static final int getTransparent(int rgb) {
		return (1 << 24) | (rgb & 0x00ffffff);
	}

	/**
	 * 得到颜色的16位数值
	 * @param color Color
	 * @return int
	 */
	public static final int getColorInteger16(Color color) {
		return Integer.parseInt(toHexFromColor(color), 16);
	}

	/**
	 * 把int转成Color
	 * @param p int
	 * @return Color
	 */
	public static final Color getColor(int p) {
		int alpha = p >>> 24;
		int red = (p >> 16) & 0xFF;
		int green = (p >> 8) & 0xFF;
		int blue = p & 0xFF;
		return new Color(red, green, blue, alpha);
	}

	/**
	 * Color对象转换成字符串
	 * @param color Color对象
	 * @return 16进制颜色字符串
	 */
	public static final String toHexFromColor(Color color) {
		String r, g, b;
		StringBuilder su = new StringBuilder();
		r = Integer.toHexString(color.getRed());
		g = Integer.toHexString(color.getGreen());
		b = Integer.toHexString(color.getBlue());
		r = r.length() == 1 ? "0" + r : r;
		g = g.length() == 1 ? "0" + g : g;
		b = b.length() == 1 ? "0" + b : b;
		r = r.toUpperCase();
		g = g.toUpperCase();
		b = b.toUpperCase();
		su.append("0xFF");
		su.append(r);
		su.append(g);
		su.append(b);
		//0xFF0000FF
		return su.toString();
	}

	/**
	 * 通过groupName和key得到参数值
	 * @param request HttpServletRequest
	 * @param groupName String
	 * @param key String
	 * @param nullDef Color
	 * @return Color
	 */
	public static final Color getRequestColor(HttpServletRequest request, String groupName, String key, Color nullDef) {
		try {
			String value = getRequest(request, groupName, key, null);
			return getColor(value, nullDef);
		} catch (Exception ex) {
			return nullDef;
		}
	}

	/**
	 * String转成Color
	 * @param value String
	 * @return Color
	 */
	public static final Color getColor(String value) {
		try {
			if (value == null || value.length() == 0) return null;
			if (value.charAt(0) == '#') value = value.substring(1);
			return new Color(Integer.parseInt(value, 16));
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * String转成Color
	 * @param value String
	 * @param nullDef Color
	 * @return Color
	 */
	public static final Color getColor(String value, Color nullDef) {
		try {
			if (value == null || value.length() == 0) return nullDef;
			return new Color(Integer.parseInt(value, 16));
		} catch (Exception ex) {
			return nullDef;
		}
	}

	/**
	 * 把含有|的字符串转成颜色列表 如：ffdd00|55eeaa|0044ee
	 * @param str String
	 * @return List<Color>
	 */
	public static final List<Color> getColorList(String str) {
		List<Color> list = new ArrayList<Color>();
		if (str == null || str.length() == 0) return list;
		String[] array = str.split("\\|");
		for (int i = 0; i < array.length; i++) {
			Color c = getColor(array[i]);
			if (c != null) list.add(c);
		}
		return list;
	}

	/**
	 * 通过groupName和key得到参数值
	 * @param request HttpServletRequest
	 * @param groupName String
	 * @param key String
	 * @param nullDef boolean
	 * @return boolean
	 */
	public static final int getRequestInt(HttpServletRequest request, String groupName, String key, int nullDef) {
		try {
			String value = getRequest(request, groupName, key, null);
			if (value == null || value.length() == 0) return nullDef;
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return nullDef;
		}
	}

	/**
	 * 通过groupName和key得到参数值
	 * @param request HttpServletRequest
	 * @param groupName String
	 * @param key String
	 * @param nullDef boolean
	 * @return boolean
	 */
	public static final boolean getRequestBoolean(HttpServletRequest request, String groupName, String key, boolean nullDef) {
		String value = getRequest(request, groupName, key, null);
		if (value == null || value.length() == 0) return nullDef;
		value = value.trim().toLowerCase();
		if (value.equals("true") || value.equals("yes") || value.equals("1") || value.equals("on")) return true;
		return false;
	}

	/**
	 * 通过key和groupName得到参数值
	 * @param request HttpServletRequest
	 * @param groupName String
	 * @param key String
	 * @return String
	 */
	public static final String getRequest(HttpServletRequest request, String groupName, String key) {
		String paraName = getParaFullName(groupName, key);
		if (paraName == null || paraName.length() == 0) return null;
		return getParameter(request, paraName);
	}

	/**
	 * 通过key和groupName得到参数值
	 * @param request HttpServletRequest
	 * @param groupName String
	 * @param key String
	 * @param nullDef String
	 * @return String
	 */
	public static final String getRequest(HttpServletRequest request, String groupName, String key, String nullDef) {
		String paraName = getParaFullName(groupName, key);
		if (paraName == null || paraName.length() == 0) return nullDef;
		String value = getParameter(request, paraName);
		if (value == null || value.length() == 0) return nullDef;
		return value;
	}

	/**
	 * 通过key得到参数值<br>
	 * @param request HttpServletRequest
	 * @param key String
	 * @return String
	 */
	public static final String getRequest(HttpServletRequest request, String key) {
		String paraName = MQConst.ACC_ParaHeadKey + "_" + key;
		if (paraName == null || paraName.length() == 0) return null;
		String value = getParameter(request, paraName);
		if (value == null || value.length() == 0) return null;
		return value;
	}

	/**
	 * 通过key得到参数值<br>
	 * @param request HttpServletRequest
	 * @param key String
	 * @return String
	 */
	static final String getParameter(HttpServletRequest request, String paraName) {
		return changeUtf8(request.getParameter(paraName));
	}

	/**
	 * 把字符串转成中文
	 * @param value String
	 * @return String
	 */
	static final String changeUtf8(String value) {
		if (value == null || value.length() == 0) return value;
		try {
			if (MQEnvParaVariable.ACC_ENV == Env.DEV) return value;
			String newValue = URLDecoder.decode(value, "UTF-8");
			return newValue;
		} catch (UnsupportedEncodingException e) {
			MQLogger.loggerInfo(e);
			return value;
		}
	}

	/**
	 * 通过key与index得到属性名称<br>
	 * po_50_key
	 * @param groupName String
	 * @param key String
	 * @return String
	 */
	static String getParaFullName(String groupName, String key) {
		if (groupName == null || groupName.length() == 0 || key == null || key.length() == 0) return null;
		return MQConst.ACC_ParaHeadKey + "_" + groupName + "_" + key;
	}

	/**
	 * 判断某个参数名称是否符合关键字，并把组号存储到list中，唯一
	 * @param paramName String
	 * @param key String
	 * @return String
	 */
	public static final String getParaGroupByKey(String paramName, String key) {
		if (paramName == null || paramName.length() == 0 || key == null || key.length() == 0) return null;
		String[] array = paramName.split("_");
		if (array.length != 3) return null;
		if (!MQConst.ACC_ParaHeadKey.equals(array[0])) return null;
		if (!key.equals(array[2])) return null;
		return array[1];
	}

	/**
	 * 生成缓存文件 path:"D:/data/shopimage/image/shop/1426/poster/banner.png"
	 * @param pathfile String
	 */
	public static void toFile(BufferedImage buffImg, String pathfile) {
		try {
			File file = new File(pathfile);
			ImageIO.write(buffImg, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
