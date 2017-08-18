/**
 * 
 */
package com.maqiao.was.pictureOverlay.tag;

/**
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
public class MQTagPictureRendering extends MQTagPicture {
	int id = 0;
	String colorarray = null;
	boolean istransparent = false;
	boolean iswhite = false;

	/*
	 * (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.tag.MQTagPicture#toHtmlString()
	 */
	@Override
	String toHtmlString() {
		StringBuilder sb = new StringBuilder(1200);
		getHtmlInputElement(sb, "rendering.id", id);
		getHtmlInputElement(sb, "rendering.colorarray", colorarray);
		getHtmlInputElement(sb, "rendering.istransparent", istransparent);
		getHtmlInputElement(sb, "rendering.iswhite", iswhite);
		return sb.toString();
	}

	public final int getId() {
		return id;
	}

	public final void setId(int id) {
		this.id = id;
	}

	public final String getColorarray() {
		return colorarray;
	}

	public final void setColorarray(String colorarray) {
		this.colorarray = colorarray;
	}

	public final boolean isIstransparent() {
		return istransparent;
	}

	public final void setIstransparent(boolean istransparent) {
		this.istransparent = istransparent;
	}

	public final boolean isIswhite() {
		return iswhite;
	}

	public final void setIswhite(boolean iswhite) {
		this.iswhite = iswhite;
	}

}
