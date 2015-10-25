package com.saick.base.datesource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 * C3P0方式获取连接池信息
 * 
 * @author Liubao
 * @2014年12月18日
 *
 */
public class C3P0Util {

    private static ComboPooledDataSource dataSource = new ComboPooledDataSource();

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(" ");
        }
    }

}
