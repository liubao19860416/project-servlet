package com.saick.base.eltag;

import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 自定义标签:将标签内的内容循环指定次数显示
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
public class ELSimpleTag5 extends SimpleTagSupport {
    private int count;
    public void setCount(int count) {
        this.count = count;
    }

    public void doTag() {
        for (int i = 0; i < count; i++) {
            try {
                getJspBody().invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
