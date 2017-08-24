/**
 * 
 */
package com.maqiao.was.pictureOverlay;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
public class MQPO {
	HttpServletRequest request = null;
	/**
	 * 文件名，不含路径与扩展名
	 */
	String filename = "";
	/**
	 * 文件保存路径 "/data/shopimage/image/shop/1426/poster"
	 */
	String savepath = "";

	/**
	 * 组名数组
	 */
	List<String> paraGroupKeyList = new ArrayList<String>();

	/**
	 * request中含有多少组图层
	 * @return int
	 */
	public int count() {
		return paraGroupKeyList.size();
	}

	public boolean isSafe() {
		if (request != null) return true;
		return false;
	}

	public String getGroupKey(int index) {
		if (index < 0 || index >= paraGroupKeyList.size()) return null;
		return paraGroupKeyList.get(index);
	}
	/**
	 * banner3_ys13
	 * @param index int
	 * @return String
	 */
	public String getProjectKey(int index) {
		String groupKey = getGroupKey(index);
		if (groupKey == null) return null;
		return filename + "_" + groupKey;
	}

	/**
	 * 文件保存路径 "/data/shopimage/image/shop/1426/poster"
	 * @param request HttpServletRequest
	 * @param savepath String
	 */
	public MQPO(HttpServletRequest request, String savepath) {
		this.request = request;
		this.savepath = savepath;
		if (this.request == null) return;
		initialization();
	}

	/**
	 * 得到图层列表
	 * @return List<MQAbstractLayer>
	 */
	public List<MQAbstractLayer> getMQAbstractLayer() {
		List<MQAbstractLayer> list = new ArrayList<MQAbstractLayer>();
		String path = null;
		if (savepath != null && savepath.length() > 0 && filename != null && filename.length() > 0) path = savepath + "/" + filename;
		for (int i = 0; i < this.count(); i++) {
			boolean valid = getRequestBoolean(i, MQConst.ACC_ValidKey, true);
			if (!valid) continue;
			MQAbstractLayer e = null;
			boolean iscache = getRequestBoolean(i, "iscache");
			if (iscache) {
				e = getMQAbstractLayerCache(i);
				if (e != null) {
					list.add(e);
					continue;
				}
			}
			e = getMQAbstractLayer(i);
			if (e != null && e.isAvailability()) {
				if (e.issave && path != null) e.makeOwnFile(path);
				if (iscache) {
					String projectkey = getProjectKey(i);
					if (projectkey != null && !MQConst.ACC_POCache.containsKey(projectkey)) {
						MQConst.ACC_POCache.put(projectkey, e);
					}
				}
				list.add(e);
			}
		}
		return list;
	}

	/**
	 * 从静态缓存中读取图层
	 * @param index int
	 * @return MQAbstractLayer
	 */
	public MQAbstractLayer getMQAbstractLayerCache(int index) {
		String projectkey = getProjectKey(index);
		MQAbstractLayer e = null;
		if (projectkey != null) e = MQConst.ACC_POCache.get(projectkey);
		return e;
	}

	/**
	 * 得到某个图层
	 * @param index int
	 * @return MQAbstractLayer
	 */
	public MQAbstractLayer getMQAbstractLayer(int index) {
		boolean valid = getRequestBoolean(index, MQConst.ACC_ValidKey, true);
		if (!valid) return null;
		int pictype = getRequestInt(index, MQConst.ACC_MainKey,-1);
		switch (pictype) {
		case 0:
			return new MQLayerNULL(this, index);
		case 1:
			/* 使用链接式图片 */
			return getLayerHttpByIndex(index);
		case 2:
			/* 使用上传图片 */
			return getLayerUploadByIndex(index);
		case 3:
			/* 使用文字图片 */
			return getLayerTextByIndex(index);
		case 4:
			/* 使用特殊式图片 */
			return getLayerHttpBySpecialPicture(index);
		default:
			return null;
		}
	}

	/**
	 * 使用链接式图片
	 * @param index int
	 * @return MQAbstractLayer
	 */
	private MQAbstractLayer getLayerHttpByIndex(int index) {
		MQLayerPicture e = MQLayerPicture.getMQLayerPictureByTypeHttp(this, index);
		if (e == null) return null;
		if (!e.isSafe()) return null;
		if (e.isAvailability()) return e;
		return null;
	}

	/**
	 * 使用上传图片
	 * @param index int
	 * @return MQAbstractLayer
	 */
	private MQAbstractLayer getLayerUploadByIndex(int index) {
		MQLayerPicture e = MQLayerPicture.getMQLayerPictureByTypeMultiPartFile(this, index);
		if (e == null) return null;
		if (!e.isSafe()) return null;
		if (e.isAvailability()) return e;
		return null;
	}

	/**
	 * 使用文字图片
	 * @param index int
	 * @return MQAbstractLayer
	 */
	private MQAbstractLayer getLayerTextByIndex(int index) {
		MQLayerText e = new MQLayerText(this, index);
		if (!e.isSafe()) return null;
		if (e.isAvailability()) return e;
		return null;
	}

	/**
	 * 使用特殊式图片
	 * @param index int
	 * @return MQAbstractLayer
	 */
	private MQAbstractLayer getLayerHttpBySpecialPicture(int index) {
		int specialid = this.getRequestInt(index, "sp.id");
		if (specialid <= 0) return null;
		MQLayerSpecialPicture e = MQLayerSpecialPicture.getMQLayerSpecialPicture(this, index);
		if (e == null) return null;
		if (!e.isSafe()) return null;
		if (e.isAvailability()) return e;
		return null;
	}

	/**
	 * 初始化，并保存组号<br>
	 * po_XXX_pictype 方式
	 */
	private void initialization() {
		this.filename = MQUtils.getRequest(request, "filename");/* 设置项目文件名称[不含扩展名] */
		Enumeration<?> paramNames = request.getParameterNames();
		//boolean valid;
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String groupkey = MQUtils.getParaGroupByKey(paramName, MQConst.ACC_MainKey);
			if (groupkey == null || groupkey.length() == 0) continue;
			//valid = MQUtils.getRequestBoolean(request, groupkey, MQConst.ACC_ValidKey, true);
			//if(!valid)continue;
			/* MQLogger.loggerInfo("groupkey:" + groupkey); */
			if (!paraGroupKeyList.contains(groupkey)) paraGroupKeyList.add(groupkey);
		}
		if (paraGroupKeyList.size() == 0) return;
		/* 进行排序 */
		Collections.sort(paraGroupKeyList, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				if (o1 == null || o2 == null) { return -1; }
				if (o1.length() > o2.length()) { return 1; }
				if (o1.length() < o2.length()) { return -1; }
				if (o1.compareTo(o2) > 0) { return 1; }
				if (o1.compareTo(o2) < 0) { return -1; }
				if (o1.compareTo(o2) == 0) { return 0; }
				return 0;
			}
		});
	}

	public final Color getRequestColor(int index, String key) {
		return getRequestColor(index, key, Color.BLACK);
	}

	public final Color getRequestColor(int index, String key, Color nullDef) {
		if (index < 0 || index >= this.count()) return nullDef;
		return MQUtils.getRequestColor(request, this.paraGroupKeyList.get(index), key, nullDef);
	}

	public final List<Color> getRequestColorList(int index, String key) {
		String colorarray = getRequest(index, key);
		List<Color> colorList = MQUtils.getColorList(colorarray);
		return colorList;
	}

	public final int getRequestInt(int index, String key) {
		return getRequestInt(index, key, 0);
	}

	public final int getRequestInt(int index, String key, int nullDef) {
		if (index < 0 || index >= this.count()) return nullDef;
		return MQUtils.getRequestInt(request, this.paraGroupKeyList.get(index), key, nullDef);
	}

	public final boolean getRequestBoolean(int index, String key) {
		return getRequestBoolean(index, key, false);
	}

	public final boolean getRequestBoolean(int index, String key, boolean nullDef) {
		if (index < 0 || index >= this.count()) return nullDef;
		return MQUtils.getRequestBoolean(request, this.paraGroupKeyList.get(index), key, nullDef);
	}

	public final String getRequest(int index, String key) {
		return getRequest(index, key, null);
	}

	public final String getRequest(int index, String key, String nullDef) {
		if (index < 0 || index >= this.count()) return nullDef;
		return MQUtils.getRequest(request, this.paraGroupKeyList.get(index), key, nullDef);
	}

	public final String getParaName(int index, String key) {
		if (index < 0 || index >= this.count()) return null;
		return MQUtils.getParaFullName(this.paraGroupKeyList.get(index), key);
	}

	/**
	 * 得到本系统生成主图片
	 * @return String
	 */
	public String getSavePathFile() {
		return savepath + "/" + filename + MQConst.ACC_FileExt;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MQPO [request=");
		builder.append(request);
		builder.append(", filename=");
		builder.append(filename);
		builder.append(", savepath=");
		builder.append(savepath);
		builder.append(", paraGroupKeyList=");
		builder.append(paraGroupKeyList);
		builder.append("]");
		return builder.toString();
	}

}
