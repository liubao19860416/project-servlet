package com.saick.base.datesource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解jdbc相关驱动信息
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JDBCInfo {
    String url();

    String driverClass();

    String user();

    String password();

}
