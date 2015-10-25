package com.saick.base.eltag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
/**
 * 自定义标签:Choose标签，3个组合实现if-else功能（相当于子标签的else）
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
public class ELSimpleTag8Else extends SimpleTagSupport {
    @Override
    public void doTag() throws JspException, IOException {
        ELSimpleTag8Choose parent = (ELSimpleTag8Choose) getParent();
        if (!parent.isFlag()) {
            // getJspBody().invoke(null);
            getJspBody().invoke(getJspContext().getOut());
        }
    }
}
