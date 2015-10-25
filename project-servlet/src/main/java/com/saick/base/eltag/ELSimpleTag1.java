package com.saick.base.eltag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 自定义标签:输出指定内容到该标签内!
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
public class ELSimpleTag1 extends SimpleTagSupport {

    // private static final String str="输出指定内容到该标签内!";

    private String displayStr;

    public void setDisplayStr(String displayStr) {
        this.displayStr = displayStr;
    }

    public void doTag() throws JspException, IOException {
        getJspContext().getOut().write(displayStr);
        // System.out.println(getJspBody().toString());
    }

}
