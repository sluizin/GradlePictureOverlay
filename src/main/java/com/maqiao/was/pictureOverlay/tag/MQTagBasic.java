/**
 * 
 */
package com.maqiao.was.pictureOverlay.tag;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.maqiao.was.pictureOverlay.MQConst;

/**
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
public class MQTagBasic extends SimpleTagSupport {
	protected String group = null;
	/** 是否添加id */
	protected boolean useid = false;
	private boolean valid = true;
	private boolean issave = false;
	private boolean iscache = false;
	private boolean isBG = false;
	private Integer x = 0;
	private Integer y = 0;
	private Integer width = 0;
	private Integer height = 0;
	private Integer align = 0;
	private Integer valign = 0;
	private String color = null;
	private String bgcolor = null;

	JspContext pc;

	public JspContext getJspContext() {
		return pc;
	}

	public void setJspContext(JspContext pc) {
		this.pc = pc;
	}

	@Override
	//当遇到标签时就会执行这个方法
	public void doTag() throws JspException, IOException {
		pc.getOut().write(toHtmlString());
		getJspBody().invoke(null);
	}

	JspTag parent2;

	@Override
	public void setParent(JspTag parent) {
		this.parent2 = parent;
	}

	String toHtmlString() {
		StringBuilder sb = new StringBuilder(1200);
		getHtmlInputElement(sb, "valid", valid);
		getHtmlInputElement(sb, "issave", issave);
		getHtmlInputElement(sb, "iscache", iscache);
		getHtmlInputElement(sb, "isBG", isBG);
		getHtmlInputElement(sb, "x", x);
		getHtmlInputElement(sb, "y", y);
		getHtmlInputElement(sb, "width", width);
		getHtmlInputElement(sb, "height", height);
		getHtmlInputElement(sb, "align", align);
		getHtmlInputElement(sb, "valign", valign);
		if (color != null && color.length() > 0) {
			getHtmlInputElement(sb, "color", color);
		}
		if (bgcolor != null && bgcolor.length() > 0) {
			getHtmlInputElement(sb, "bgcolor", bgcolor);
		}
		return sb.toString();

	}

	protected final void getHtmlInputElement(StringBuilder sb, String key, Object obj) {
		if (obj == null) return;
		sb.append(getHtmlInput(key, obj));
		sb.append(MQConst.ACC_Enter);
	}

	private final StringBuilder getHtmlInput(String key, Object obj) {
		StringBuilder sb = new StringBuilder(100);
		if (group == null || group.length() == 0 || key == null || key.length() == 0 || obj == null) return sb;
		String inputKey = MQConst.ACC_ParaHeadKey + "_" + group + "_" + key;
		sb.append("<input type=\"hidden\"");
		sb.append(" name=\"" + inputKey + "\"");
		if (useid) sb.append(" id=\"" + inputKey + "\"");
		sb.append(" value=\"" + obj + "\" >");
		return sb;
	}

	public final String getGroup() {
		return group;
	}

	public final void setGroup(String group) {
		this.group = group;
	}

	public final boolean isUseid() {
		return useid;
	}

	public final void setUseid(boolean useid) {
		this.useid = useid;
	}

	public final Integer getX() {
		return x;
	}

	public final void setX(Integer x) {
		this.x = x;
	}

	public final Integer getY() {
		return y;
	}

	public final void setY(Integer y) {
		this.y = y;
	}

	public final Integer getWidth() {
		return width;
	}

	public final void setWidth(Integer width) {
		this.width = width;
	}

	public final Integer getHeight() {
		return height;
	}

	public final void setHeight(Integer height) {
		this.height = height;
	}

	public final Integer getAlign() {
		return align;
	}

	public final void setAlign(Integer align) {
		this.align = align;
	}

	public final Integer getValign() {
		return valign;
	}

	public final void setValign(Integer valign) {
		this.valign = valign;
	}

	public final String getColor() {
		return color;
	}

	public final void setColor(String color) {
		this.color = color;
	}

	public final String getBgcolor() {
		return bgcolor;
	}

	public final void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}

	public final boolean isValid() {
		return valid;
	}

	public final void setValid(boolean valid) {
		this.valid = valid;
	}

	public final boolean isIssave() {
		return issave;
	}

	public final void setIssave(boolean issave) {
		this.issave = issave;
	}

	public final boolean isIscache() {
		return iscache;
	}

	public final void setIscache(boolean iscache) {
		this.iscache = iscache;
	}

	public final boolean isBG() {
		return isBG;
	}

	public final void setIsBG(boolean isBG) {
		this.isBG = isBG;
	}

	public final void setisBG(boolean isBG) {
		this.isBG = isBG;
	}

}
