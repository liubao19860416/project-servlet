package com.saick.base.eltag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 自定义标签:当标记flag为true的时候，显示标签内的内容；为false时不显示；相当于实现if标签的功能
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
public class ELSimpleTag7 extends SimpleTagSupport {
    private boolean flag;

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void doTag() throws JspException, IOException {
        if (flag) {
            getJspBody().invoke(getJspContext().getOut());
            // getJspBody().invoke(null);
        }

    }
}
