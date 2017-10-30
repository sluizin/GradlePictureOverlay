/**
 * 
 */
package com.maqiao.was.pictureOverlay;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
public class MQPictureOverlay {

	/**
	 * 合并元素单元
	 * @param biArray LayerElement[]
	 * @return BufferedImage
	 */
	public static BufferedImage merge(MQAbstractLayer... biArray) {
		if (biArray == null) return null;
		int arrayLen = biArray.length;
		if (arrayLen == 0) return null;
		List<MQAbstractLayer> list = new ArrayList<MQAbstractLayer>(arrayLen);
		/* 把数组过滤非法图层保存到列表中 */
		for (int i = 0; i < biArray.length; i++) {
			MQAbstractLayer e = biArray[i];
			if (e == null) continue;
			if (e != null && e.isAvailability() && (e.valid)) list.add(e);
		}
		int listSize = list.size();
		if (listSize == 0) return null;
		int point = getMQAbstractLayerBackGround(list);
		if (point == -1) return null;
		BufferedImage buffImgBG = list.get(point).getBufferedImage();
		MQPO mqpo = list.get(point).getMqpo();
		/* 复合图层 */
		MQLogger.loggerInfo(MQConst.ACC_SPACING);

		String path = mqpo.getSaveCacheFileMerger();
		for (int i = 0; i < listSize; i++)
			if (point != i && (list.get(i).laterStage == 0)) {
				MQAbstractLayer e = list.get(i);
				MQLogger.loggerInfo("[merge]mergelist.get(" + i + "):[" + e.getMainkey() + "]GroupKey:" + e.getMqpo().getGroupKey(e.index));
				buffImgBG = e.merge(buffImgBG);
				/** 保存合并以后的图层 */
				if (e.issavemerge && path != null) MQUtils.toFile(buffImgBG, path);
			}
		for (int i = 0; i < listSize; i++)
			if (list.get(i).laterStage > 0) {
				MQLogger.loggerInfo("[laterStage]mergelist.get(" + i + "):[" + list.get(i).getMainkey() + "]GroupKey:" + list.get(i).getMqpo().getGroupKey(i));
				buffImgBG = list.get(i).merge(buffImgBG);
			}
		MQLogger.loggerInfo(MQConst.ACC_SPACING);
		return buffImgBG;
	}

	/**
	 * 得到列表中第一个背景属性为真的图层index{针对list下标}<br>
	 * 如果没有查到，则返回-1
	 * @param list List<MQAbstractLayer>
	 * @return int
	 */
	static int getMQAbstractLayerBackGround(List<MQAbstractLayer> list) {
		int listSize = list.size();
		if (listSize == 0) return -1;
		int point = -1;
		/* 查看各图层中是否含有背景关键字的图层 */
		for (int i = 0; i < listSize; i++)
			if (list.get(i).isBackground && (list.get(i).laterStage == 0)) return i;
		/* 如果各图层中没有含有背景关键字的图层，则选择第一个非文字图层 */
		if (point == -1) for (int i = 0; i < listSize; i++)
			if (list.get(i).isState() == 1 && (list.get(i).laterStage == 0)) return i;
		return -1;
	}

	/**
	 * 把图片数组单元保存到文件中
	 * @param pathfile String "E:/picture/result.png"
	 * @param formatName String
	 * @param array LayerElement[]
	 */
	public static boolean save(String pathfile, MQAbstractLayer... array) {
		long time1, time2;
		time1 = System.currentTimeMillis();
		BufferedImage outBuff = merge(array);
		time2 = System.currentTimeMillis();
		MQLogger.loggerInfo("合成图片共用时:" + (time2 - time1));
		if (outBuff == null) return false;
		try {
			String formatName = pathfile.substring(pathfile.lastIndexOf(".") + 1);
			ImageIO.write(outBuff, formatName, new File(pathfile));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 把图层列表保存成文件
	 * @param pathfile String
	 * @param list List<MQAbstractLayer>
	 * @return boolean
	 */
	public static boolean save(String pathfile, List<MQAbstractLayer> list) {
		MQAbstractLayer[] e = {};
		MQAbstractLayer[] array = list.toArray(e);
		return save(pathfile, array);
	}

	/**
	 * 保存并生成图片<br>
	 * 并配置图层文件保存信息
	 * @param makeFile MakeFile
	 * @param savepathfile String
	 * @param request HttpServletRequest
	 * @return String
	 */
	public static String save(String savepathfile, HttpServletRequest request) {
		if (request == null) return null;
		long time1 = System.currentTimeMillis();
		MQPO mqpo = new MQPO(request, savepathfile);
		List<MQAbstractLayer> list = mqpo.getMQAbstractLayer();
		MQLogger.loggerInfo("提取图层共用时:" + (System.currentTimeMillis() - time1));
		MQAbstractLayer[] e = {};
		MQAbstractLayer[] array = list.toArray(e);
		mqpo.delAllFile();
		boolean t = save(mqpo.getSavePathFile(), array);
		MQLogger.loggerInfo("提取生成共用时:" + (System.currentTimeMillis() - time1));
		if(t)return mqpo.getSaveFileName();
		return null;
	}
}
