package com.saick.base.eltag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 自定义标签:将标签后的原来的内容显示为空!
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
public class ELSimpleTag3 extends SimpleTagSupport {
    @SuppressWarnings("unused")
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        JspWriter out = pageContext.getOut();
        JspFragment jspBody = getJspBody();

        // 不显示
        jspBody.invoke(null);
        // 显示
        // jspBody.invoke(out);
    }

}
