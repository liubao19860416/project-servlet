package com.saick.base.datesource;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 手动创建JDBC连接的方法,使用了ThreadLocal线程局部变量
 * 
 * @author Liubao
 * @2014年12月16日
 * 
 */
public class JDBCUtil {

    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

    private static String url;
    private static String driverClass;
    private static String user;
    private static String password;

    // 静态代码快,只执行一次,防止驱动重复加载,所以加载驱动,要放在静态代码快里面;
    static {
        try {
            InputStream in = JDBCUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");

            Thread.currentThread().getClass().getResourceAsStream("jdbc.properties");

            Properties pop = new Properties();
            pop.load(in);
            driverClass = pop.getProperty("orm.mysql.jdbc.driverClassName");
            url = pop.getProperty("orm.mysql.jdbc.url");
            user = pop.getProperty("orm.mysql.jdbc.username");
            password = pop.getProperty("orm.mysql.jdbc.password");
            // 加载
            Class.forName(driverClass);
        } catch (Exception e) {
            throw new ExceptionInInitializerError("数据库驱动加载失败！！！");
        }

    }

    /**
     * 获取一个连接
     */
    public static Connection getConnection() throws Exception {
        Connection conn = threadLocal.get();
        if (conn == null) {
            conn = DriverManager.getConnection(url, user, password);
            threadLocal.set(conn);
        }
        return conn;
    }

    /**
     * 释放资源
     */
    public static void realseResourse(Connection conn, Statement stmt,
            ResultSet rs) {
        try {
            if (conn != null) {
                conn.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn = null;
            stmt = null;
            rs = null;
            threadLocal.remove();
        }
    }

    public static void releaseConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
