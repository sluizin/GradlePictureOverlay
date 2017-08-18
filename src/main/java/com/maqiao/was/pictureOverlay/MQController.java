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
	@ResponseBody
	@RequestMapping(value = "/makePO/Download/{shopid:[\\d]+}/{filename}", produces = "application/octet-stream;charset=utf-8")
	public Object download(HttpServletRequest request, HttpServletResponse response, @PathVariable int shopid, @PathVariable String filename) throws IOException {
		MQEnvParaVariable.testing(request);
		String pathfile = getPicturePath(shopid, filename);
		File file = new File(pathfile);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", filename + ".png");
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
	}
	static final String getPicturePath(int shopid) {
		String path = "/data/shopimage/image/shop/" + shopid + "/poster";
		if (MQEnvParaVariable.ACC_ENV == Env.DEV) path = "d:/data/shopimage/image/shop/" + shopid + "/poster";
		File file = new File(path);
		if (!file.exists()) file.mkdirs();
		return path;
	}

	static final String getPicturePath(int shopid, String filename) {
		return getPicturePath(shopid) + "/" + filename + MQConst.ACC_FileExt;
	}

	@ResponseBody
	@RequestMapping(value = "/makePO/{shopid:[\\d]+}/{filename}", produces = "application/json;charset=utf-8")
	public String makePO(HttpServletRequest request, @PathVariable int shopid, @PathVariable String filename) throws IOException {
		return makefile(request, shopid, filename);
	}

	@ResponseBody
	@RequestMapping(value = "/makePO/{shopid:[\\d]+}", produces = "application/json;charset=utf-8")
	public String makePO_1(HttpServletRequest request, @PathVariable int shopid, @RequestParam(value = "po_filename", required = false) String filename) throws IOException {
		if (filename == null) filename = "temp";
		return makefile(request,  shopid, filename);
	}

	static String makefile(HttpServletRequest request, int shopid, String filename) {
		ReturnJson rj = new ReturnJson();
		if (request == null) return rj.toJson();
		if (filename == null || filename.length() == 0) return rj.toJson();
		MQEnvParaVariable.testing(request);
		String path = getPicturePath(shopid);
		boolean result = MQPictureOverlay.save(path, request);
		rj.state = result;
		if (result) {
			rj.imghttp = getURLPicturePath(shopid, filename);
			rj.imgsource = getPicturePath(shopid,filename);
		}
		MQLogger.loggerInfo("rj:" + rj.toJson());
		return rj.toJson();
	}

	public static final String getURLPicturePath(int shopid) {
		if (MQEnvParaVariable.ACC_ENV == Env.DEV) { return "http://192.168.1.110:94/image/shop/" + shopid + "/poster"; }
		if (MQEnvParaVariable.ACC_ENV == Env.TEST) { return "http://kuaigoucs.99114.com/image/image/shop/" + shopid + "/poster"; }
		if (MQEnvParaVariable.ACC_ENV == Env.ONLINE) { return "http://kuaigou.99114.com/image/image/shop/" + shopid + "/poster"; }
		return "http://192.168.1.110:94/image/shop/" + shopid + "/poster";
	}

	public static final String getURLPicturePath(int shopid, String filename) {
		return getURLPicturePath(shopid) + "/" + filename + MQConst.ACC_FileExt;
	}
	static class ReturnJson {
		boolean state = false;
		String imghttp = "";
		String imgsource = "";
		String remarks = "";

		String toJson() {
			StringBuilder sb = new StringBuilder();
			sb.append("{");

			sb.append("\"state\":");
			sb.append(state);
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
