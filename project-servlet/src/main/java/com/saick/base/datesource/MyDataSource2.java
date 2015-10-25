package com.saick.base.datesource;

import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * 自定义连接池2：动态代理实现
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
public class MyDataSource2 implements DataSource {
    // 连接池
    private static LinkedList<Connection> pool = new LinkedList<Connection>();

    static {
        for (int i = 0; i < 10; i++) {
            try {
                pool.add(JDBCUtil.getConnection());
            } catch (Exception e) {
                // pool.add(DriverManager.getConnection(url, user, password));
                throw new RuntimeException("初始化连接池异常 !");
            }
        }
    }

    
    /*public synchronized Connection getConnection() {
        if (pool.size() > 0) {
            Connection conn = pool.removeFirst();
            return conn;
        } else {
            throw new RuntimeException(" ");
        }
    }*/

    /**
     * 获取连接的方式.需要同步处理,这里使用动态代理实现;
     */
    @Override
    public synchronized Connection getConnection() throws SQLException {
        if (pool != null && pool.size() > 0) {
            final Connection con = pool.remove(0);
            Connection proxyConn = (Connection) Proxy.newProxyInstance(con
                    .getClass().getClassLoader(), con.getClass()
                    .getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args)
                        throws Throwable {
                    String name = method.getName();
                    if ("close".equals(name)) {
                        long time1 = System.nanoTime();
                        System.out.println("您调用了回收连接方法,将连接还回连接池中了!");
                        pool.add(con);
                        System.out.println(name + "放回连接用的时间为:"
                                + (System.nanoTime() - time1) + "纳秒");
                        return pool.add(con);
                    } else {
                        System.out.println("您什么也没有修改,还是使用原来定义的方法操作!");
                        // 执行原来定义的方法;这里很关键的;
                        return method.invoke(con, args);
                    }
                }
            });
            return proxyConn;
        } else {
            throw new RuntimeException("服务器忙!");
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
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        // TODO Auto-generated method stub
        return null;
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

}
