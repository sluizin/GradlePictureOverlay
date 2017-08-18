/**
 * 
 */
package com.maqiao.was.pictureOverlay.tag;

/**
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
public class MQTagPicturePict extends MQTagPicture {
	Integer shape = 0;
	boolean autoscale = false;
	boolean intercept = false;
	String url = null;
	boolean isurl = false;

	/*
	 * (non-Javadoc)
	 * @see com.maqiao.was.pictureOverlay.tag.MQTagPictureAbstract#toHtmlString()
	 */
	String toHtmlString() {
		if (isurl) this.pictype = 1;
		else this.pictype = 2;
		StringBuilder sb = new StringBuilder(1200);
		getHtmlInputElement(sb, "pictype", pictype);
		getHtmlInputElement(sb, "shape", shape);
		getHtmlInputElement(sb, "autoscale", autoscale);
		getHtmlInputElement(sb, "intercept", intercept);
		getHtmlInputElement(sb, "url", url);
		return sb.toString();
	}

	public final Integer getShape() {
		return shape;
	}

	public final void setShape(Integer shape) {
		this.shape = shape;
	}

	public final boolean isAutoscale() {
		return autoscale;
	}

	public final void setAutoscale(boolean autoscale) {
		this.autoscale = autoscale;
	}

	public final boolean isIntercept() {
		return intercept;
	}

	public final void setIntercept(boolean intercept) {
		this.intercept = intercept;
	}

	public final String getUrl() {
		return url;
	}

	public final void setUrl(String url) {
		this.url = url;
	}

	public final boolean isIsurl() {
		return isurl;
	}

	public final void setIsurl(boolean isurl) {
		this.isurl = isurl;
	}

}
