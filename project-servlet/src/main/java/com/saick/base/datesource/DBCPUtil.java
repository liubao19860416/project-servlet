package com.saick.base.datesource;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
/**
 * DBCP方式获取连接池信息
 * 
 * @author Liubao
 * @2014年12月18日
 *
 */
@SuppressWarnings("static-access")
public class DBCPUtil {
    private static DataSource dataSource;
    static {
        try {
            InputStream in = DBCPUtil.class.getClassLoader().getSystemResourceAsStream("dbcpconfig.properties");
            
            Thread.currentThread().getClass().getResourceAsStream("dbcpconfig.properties");

            Properties props = new Properties();
            props.load(in);
            dataSource = BasicDataSourceFactory.createDataSource(props);
        } catch (Exception e) {
            throw new ExceptionInInitializerError("!");
        }
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(" ");
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

}
