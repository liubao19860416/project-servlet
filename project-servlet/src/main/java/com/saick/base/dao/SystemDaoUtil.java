package com.saick.base.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 通过c3p0连接池，操作数据QueryRunner
 */
public class SystemDaoUtil {
    
    //测试
    public static void main(String[] args) {
        new SystemDaoUtil().createTable("t_user0");
    }

    /**
     * 加载数据库连接信息
     */
    private ComboPooledDataSource dataSource = new ComboPooledDataSource();

    public void createTable(String tableName) {
        try {
            String sql = "create table if not exists "
                    + tableName
                    + "(id int(5) primary key auto_increment,name varchar(10) not null)";
            QueryRunner runner = new QueryRunner(dataSource);
            runner.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void dropTable(String tableName) {
        try {
            String sql = "drop table " + tableName;
            QueryRunner runner = new QueryRunner(dataSource);
            runner.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    public void updateTable(String params) {
        try {
            String sql = "update table set " ;
            QueryRunner runner = new QueryRunner(dataSource);
            runner.update(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}