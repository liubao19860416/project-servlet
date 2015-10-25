package com.saick.base.eltag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 自定义标签:Choose标签，3个组合实现if-else功能（相当于父标签）
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
public class ELSimpleTag8Choose extends SimpleTagSupport {
    private boolean flag = false;

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void doTag() throws JspException, IOException {
        getJspBody().invoke(null);
    }
}
