package com.saick.base.eltag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
/**
 * 自定义标签:Choose标签，3个组合实现if-else功能（相当于子标签的if）
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
public class ELSimpleTag8If extends SimpleTagSupport {
    private boolean flag;

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void doTag() throws JspException, IOException {
        if (flag) {
            getJspBody().invoke(null);
            ELSimpleTag8Choose parent = (ELSimpleTag8Choose) getParent();
            parent.setFlag(true);
        }
    }
}
