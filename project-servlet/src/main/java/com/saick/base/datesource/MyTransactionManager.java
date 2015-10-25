package com.saick.base.datesource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 自定义事务管理器
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
public class MyTransactionManager {

    // 线程局部变量
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

    /**
     * 从当前线程对象上面获取一个连接
     */
    public static Connection getConnection() throws SQLException {
        Connection conn = threadLocal.get();
        if (conn == null) {
            conn = DBCPUtil.getConnection();
            threadLocal.set(conn);
        }
        return conn;
    }

    /**
     * 开启事务
     */
    public static void startTransaction() throws SQLException {
        Connection conn = getConnection();
        conn.setAutoCommit(false);
    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction() throws SQLException {
        Connection conn = getConnection();
        conn.rollback();
    }

    /**
     * 提交事务
     */
    public static void commitTransaction() throws SQLException {
        Connection conn = getConnection();
        conn.commit();
    }

    /**
     * 释放资源
     */
    public static void closeResource() throws SQLException {
        Connection conn = getConnection();
        conn.close();
        threadLocal.remove();
    }

}
