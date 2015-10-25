package com.saick.base.derby;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.saick.base.datesource.JDBCUtil;

/**
 * Derby内存数据库测试类
 * 
 * @author Liubao
 * @2014年12月25日
 * 
 */
@SuppressWarnings("unused")
public class DerbyTest {
    
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
    
    private static String driverClass;
    private static String url;
    private static String user;
    private static String password;
    
    //测试
    public static void main(String[] args) throws Exception {
        DerbyTest.getConnection();
        System.out.println("over。。。");
    }

    // 静态代码快,只执行一次,防止驱动重复加载,所以加载驱动,要放在静态代码快里面;
    static {
        try {
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("derby.properties");
            Properties pop = new Properties();
            pop.load(inputStream);
            driverClass = pop.getProperty("derby.jdbc.driverClassName");
            url = pop.getProperty("derby.jdbc.url");
            user = pop.getProperty("derby.jdbc.username");
            password = pop.getProperty("derby.jdbc.password");
            // 加载驱动信息
            Class.forName(driverClass);
            //DriverManager.getConnection("jdbc:derby:dbName;create=true");
        } catch (Exception e) {
            throw new ExceptionInInitializerError("数据库驱动加载失败！！！");
        }

    }
    
    /**
     * 获取连接
     */
    public static Connection getConnection() throws Exception {
        Connection conn = threadLocal.get();
        if (conn == null) {
            conn = DriverManager.getConnection(makeDerbyTempURL("dbName"));
            //conn = DriverManager.getConnection(url, user, password);
            threadLocal.set(conn);
        }
        return conn;
    }

    /**
     * 释放资源
     */
    public static void realseResourse(Connection conn, Statement stmt,ResultSet rs) {
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

    /**
     * 释放连接
     */
    public static void releaseConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建数据库临时url路径
     */
    private static String makeDerbyTempURL(String db) {
        Path p = makeTmpDir();
        return "jdbc:derby:" + p.toUri().getPath() + "/" + db + ";create=true";
    }

    /**
     * 创建一个临时目录
     */
    private static Path makeTmpDir() {
        Path path = new File("/tmp").toPath();
        Path p = null;
        try {
            p = Files.createTempDirectory(path, "derby");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return p;
    }
    
}
