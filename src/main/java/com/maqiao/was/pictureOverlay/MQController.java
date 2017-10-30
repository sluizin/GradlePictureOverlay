/**
 * 
 */
package com.maqiao.was.pictureOverlay;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maqiao.was.envParaVariable.MQEnvParaVariable;
import com.maqiao.was.envParaVariable.MQEnvParaVariable.Env;

/**
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
@Controller
@RequestMapping("/mqpo")
public class MQController {
	/**
	 * 下载某个图片文件
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param shopid int
	 * @param filename String
	 * @return Object
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/makePO/Download/{shopid:[\\d]+}/{root}/{filename}", produces = "application/octet-stream;charset=utf-8")
	public Object download(HttpServletRequest request, HttpServletResponse response, @PathVariable int shopid, @PathVariable String root, @PathVariable String filename) throws IOException {
		MQEnvParaVariable.testing(request);
		String pathfile = getPicturePath(shopid,root, filename);
		File file = new File(pathfile);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", filename + ".png");
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
	}

	/**
	 * 下载某个图片文件 主文件
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param shopid int
	 * @param filename String
	 * @return Object
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/makePO/Download/Master/{shopid:[\\d]+}/{root}/{filename}", produces = "application/octet-stream;charset=utf-8")
	public Object downloadMaster(HttpServletRequest request, HttpServletResponse response, @PathVariable int shopid, @PathVariable String root, @PathVariable String filename) throws IOException {
		MQEnvParaVariable.testing(request);
		String pathfile = getPicturePath(shopid, filename);
		File file = new File(pathfile);
		if (!file.exists()) file = null;
		if (file == null) {
			String path = getPicturePath(shopid,root);
			String newfileExt = filename + "_master_";
			File[] array = (new File(path)).listFiles();
			for (int i = 0; i < array.length; i++) {
				File f = array[i];
				if (f.isFile()) {
					String name = f.getName();
					if (name.indexOf(newfileExt) == 0) {
						file = f;
						break;
					}
				}
			}
		}
		if (file == null || (!file.exists()) || (!file.isFile())) return null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", file.getName() + ".png");
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
	}

	/**
	 * 得到商家海报保存的实际地址(目录)
	 * @param shopid int
	 * @param root String
	 * @return String
	 */
	static final String getPicturePath(int shopid,String root) {
		String path = MQConst.outputSavePath + "/image/shop/" + shopid + "/"+root;
		if (MQEnvParaVariable.ACC_ENV == Env.DEV) path = "d:/data/shopimage/image/shop/" + shopid + "/"+root;
		File file = new File(path);
		if (!file.exists()) file.mkdirs();
		return path;
	}

	/**
	 * 得到商家海报保存的实际地址
	 * @param shopid int
	 * @param filename String
	 * @return String
	 */
	static final String getPicturePath(int shopid,String root, String filename) {
		return getPicturePath(shopid,root) + "/" + filename + MQConst.ACC_FileExt;
	}
	/**
	 * 入口端Controller
	 * @param request HttpServletRequest
	 * @param shopid int
	 * @param filename String
	 * @return String
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/makePO/{shopid:[\\d]+}", produces = "application/json;charset=utf-8")
	public String makePO_1(HttpServletRequest request, @PathVariable int shopid, @RequestParam(value = MQConst.ACC_ParaHeadKey + "_root", required = false) String root, @RequestParam(value = MQConst.ACC_ParaHeadKey + "_filename", required = false) String filename) throws IOException {
		if(root==null || root.length()==0)root="poster";
		if (filename == null) filename = "temp";
		return makefile(request, shopid,root, filename);
	}

	/**
	 * 生成图片
	 * @param request HttpServletRequest
	 * @param shopid int
	 * @param filename String
	 * @return String
	 */
	static String makefile(HttpServletRequest request, int shopid,String root, String filename) {
		ReturnJson rj = new ReturnJson();
		if (request == null) return rj.toJson();
		if (filename == null || filename.length() == 0) return rj.toJson();
		MQEnvParaVariable.testing(request);
		String path = getPicturePath(shopid,root);
		String saveFileName = MQPictureOverlay.save(path, request);
		MQLogger.loggerInfo("path" + path);
		if (saveFileName == null) rj.state = false;
		else rj.state = true;
		if (rj.state) {
			rj.filename = saveFileName;
			rj.imghttp = getURLPicturePath(shopid,root, saveFileName);
			rj.imgsource = getPicturePath(shopid,root, saveFileName);
		}
		MQLogger.loggerInfo("rj:" + rj.toJson());
		return rj.toJson();
	}

	/**
	 * 得到图片保存的域名地址
	 * @param shopid int
	 * @return String
	 */
	public static final String getURLPicturePath(int shopid,String root) {
		if (MQEnvParaVariable.ACC_ENV == Env.DEV) { return "http://192.168.1.110:94/image/shop/" + shopid + "/"+root; }
		if (MQEnvParaVariable.ACC_ENV == Env.TEST) { return MQConst.outputUrlPath + "/image/shop/" + shopid + "/"+root; }
		if (MQEnvParaVariable.ACC_ENV == Env.ONLINE) { return MQConst.outputUrlPath + "/image/shop/" + shopid + "/"+root; }
		return "http://192.168.1.110:94/image/shop/" + shopid + "/"+root;
	}

	/**
	 * 得到图片的域名地址
	 * @param shopid int
	 * @param filename String
	 * @return String
	 */
	public static final String getURLPicturePath(int shopid,String root, String filename) {
		return getURLPicturePath(shopid,root) + "/" + filename + MQConst.ACC_FileExt;
	}

	/**
	 * 返回的json状态对象
	 * @author Sunjian
	 * @version 1.0
	 * @since jdk1.7
	 */
	static class ReturnJson {
		boolean state = false;
		String filename = "";
		String imghttp = "";
		String imgsource = "";
		String remarks = "";

		String toJson() {
			StringBuilder sb = new StringBuilder();
			sb.append("{");

			sb.append("\"state\":");
			sb.append(state);
			sb.append(",");

			sb.append("\"filename\":");
			sb.append("\"" + filename + "\"");
			sb.append(",");

			sb.append("\"imghttp\":");
			sb.append("\"" + imghttp + "\"");
			sb.append(",");

			sb.append("\"imgsource\":");
			sb.append("\"" + imgsource + "\"");
			sb.append(",");

			sb.append("\"remarks\":");
			sb.append("\"" + remarks + "\"");

			sb.append("}");
			return sb.toString();
		}
	}
}
