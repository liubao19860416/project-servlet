package com.saick.base.eltag;

import java.io.StringWriter;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 自定义标签:将标签内的内容转换为大写字母进行显示
 * 
 * 静态调用的方式
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
public class StaticELSimpleTag extends SimpleTagSupport {
    public void doTag() {
        try {
            StringWriter sw = new StringWriter();
            getJspBody().invoke(sw);
            JspWriter out = getJspContext().getOut();
            out.write(sw.getBuffer().toString().toLowerCase());
            // out.write(sw.getBuffer().toString().toUpperCase());
        } catch (Exception e) {
            e.printStackTrace();
        }

        getJspBody();
    }
}
