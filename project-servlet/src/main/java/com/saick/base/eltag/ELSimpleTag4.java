package com.saick.base.eltag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 自定义标签:将标签之后的内容跳过不显示
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
public class ELSimpleTag4 extends SimpleTagSupport {

    public void doTag() throws JspException, IOException {
        throw new SkipPageException();
    }
}
