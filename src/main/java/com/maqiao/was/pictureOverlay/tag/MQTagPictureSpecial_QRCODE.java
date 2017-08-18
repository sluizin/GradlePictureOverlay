/**
 * 
 */
package com.maqiao.was.pictureOverlay.tag;

/**
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
public class MQTagPictureSpecial_QRCODE extends MQTagPicture {
	int spid = 0;
	int size = 0;
	String url = null;
	int margin = 0;
	int style = 0;
	String colorarray = null;

	/*
	 * (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.tag.MQTagPicture#toHtmlString()
	 */
	@Override
	String toHtmlString() {
		this.pictype = 4;
		this.spid = 2;
		StringBuilder sb = new StringBuilder(1200);
		getHtmlInputElement(sb, "pictype", pictype);
		getHtmlInputElement(sb, "sp.id", spid);
		getHtmlInputElement(sb, "sp.qrcode.size", size);
		getHtmlInputElement(sb, "sp.qrcode.url", url);
		getHtmlInputElement(sb, "sp.qrcode.margin", margin);
		getHtmlInputElement(sb, "sp.qrcode.style", style);
		getHtmlInputElement(sb, "sp.qrcode.colorarray", colorarray);
		return sb.toString();
	}

	public final int getSpid() {
		return spid;
	}

	public final void setSpid(int spid) {
		this.spid = spid;
	}

	public final int getSize() {
		return size;
	}

	public final void setSize(int size) {
		this.size = size;
	}

	public final String getUrl() {
		return url;
	}

	public final void setUrl(String url) {
		this.url = url;
	}

	public final int getMargin() {
		return margin;
	}

	public final void setMargin(int margin) {
		this.margin = margin;
	}

	public final int getStyle() {
		return style;
	}

	public final void setStyle(int style) {
		this.style = style;
	}

	public final String getColorarray() {
		return colorarray;
	}

	public final void setColorarray(String colorarray) {
		this.colorarray = colorarray;
	}

}
