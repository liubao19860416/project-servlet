package com.saick.base.datesource;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
/**
 * 注解驱动方式加载数据库信息
 * @author Liubao
 * @2014年12月18日
 *
 */
public class JDBCUtil2 {
    
    @JDBCInfo(driverClass = "com.mysql.jdbc.Driver", password = "root", url = "jdbc:mysql://localhost:3306/grape20", user = "root")
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Connection getConnection() {
        Class c = JDBCUtil2.class;
        try {
            Method method = c.getMethod("getConnection", null);
            if (method != null) {
                if (method.isAnnotationPresent(JDBCInfo.class)) {
                    JDBCInfo jdbcInfo = method.getAnnotation(JDBCInfo.class);
                    String url = jdbcInfo.url();
                    String driverClass = jdbcInfo.driverClass();
                    String user = jdbcInfo.user();
                    String password = jdbcInfo.password();
                    Class.forName(driverClass);
                    Connection connection = DriverManager.getConnection(url,user, password);
                    return connection;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("初始化数据库驱动失败!");
        }
        return null;
    }

}
