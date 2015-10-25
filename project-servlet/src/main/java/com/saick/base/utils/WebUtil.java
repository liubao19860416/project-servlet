package com.saick.base.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;

/**
 * 将请求参数中的request的参数封装到对应的Bean对象中
 * 
 * BeanUtils.populate(bean, request.getParameterMap());
 * 
 * BeanUtilsBean.getInstance().copyProperties(bean1,request.getParameterMap());
 * 
 * @author Liubao
 * @2014年12月16日
 * 
 */
public class WebUtil {

    public static <T> T fillFormBean(HttpServletRequest request, Class<T> class1) {
        try {
            T bean = class1.newInstance();
            T bean1 = class1.newInstance();
            BeanUtils.populate(bean, request.getParameterMap());
            BeanUtilsBean.getInstance().copyProperties(bean1,request.getParameterMap());
            return bean;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
