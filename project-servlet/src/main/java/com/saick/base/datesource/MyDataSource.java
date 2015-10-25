package com.saick.base.datesource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.sql.DataSource;
/**
 * 自定义连接池1：直接获取实现
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
public class MyDataSource implements DataSource {

    private static LinkedList<Connection> pool = new LinkedList<Connection>();

    static {
        for (int i = 0; i < 10; i++) {
            try {
                Connection conn = JDBCUtil.getConnection();
                pool.add(conn);
            } catch (Exception e) {
                throw new ExceptionInInitializerError(" !");
            }
        }
    }

    public MyDataSource(Connection con, LinkedList<Connection> pool2) {
    }

    @Override
    public synchronized Connection getConnection() throws SQLException {
        if (pool != null && pool.size() > 0) {
            Connection con = pool.remove(0);
            //MyDataSource conn = new MyDataSource(con, pool);
            return con;
        } else {
            throw new RuntimeException("!");
        }
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Connection getConnection(String username, String password)
            throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        // TODO Auto-generated method stub
        return null;
    }

}
