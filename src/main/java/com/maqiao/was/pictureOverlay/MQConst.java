/**
 * 
 */
package com.maqiao.was.pictureOverlay;

import java.util.HashMap;
import java.util.Map;

import com.maqiao.was.pictureOverlay.pictureShape.InterfaceShape;

//import com.maqiao.was.pictureOverlay.pictureShape.InterfaceShape;

/**
 * 常量池
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
public class MQConst {
	/**
	 * 参数名称头部关键字，即 XX_1_pictype
	 */
	public static final String ACC_ParaHeadKey = "po";
	/**
	 * 换行符
	 */
	public static final String ACC_Enter = System.getProperty("line.separator", "\n");
	/**
	 * 有效图层的关键字 po_1_XXX 为真则
	 */
	static final String ACC_ValidKey = "valid";
	static final String ACC_MainKey = "pictype";
	/** 3M以内 图片文件大小 */
	public static final long ACC_PICTURE_CAPACITY_MAX = 1024 * 1024 * 3;
	/** 生成的文件的扩展名 */
	public static final String ACC_FileExt = ".png";
	public static final String path = "d:/data/po/";
	/**
	 * 是否使用静态存储
	 */
	static final boolean isState=true;
	/**
	 * /data/kgdata<br>
	 * /data/shopimage
	 */
	static String outputSavePath;
	/**
	 * http://static.99114.com/kgdata<br>
	 * http://kuaigou.99114.com/image
	 */
	static String outputUrlPath;
	static {
		if(isState){
			outputSavePath = "/data/kgdata";
			outputUrlPath = "http://static.99114.com/kgdata";
		}else{
			outputSavePath = "/data/shopimage";
			outputUrlPath = "http://kuaigou.99114.com/image";			
		}
	}
	static final String ACC_SPACING = "============================================================================================";
	/**
	 * 把画片变形放在缓存中用于保存
	 */
	public static final Map<Integer,InterfaceShape> ACC_PictureShapeMap=new HashMap<Integer,InterfaceShape>(3);
	/**
	 * 图层缓存，用于直接调出固定BufferedImage的图层信息
	 */
	public static final Map<String,MQAbstractLayer> ACC_POCache=new HashMap<String,MQAbstractLayer>();
}
