package com.saick.base.datesource;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.ResultSetHandler;
import org.junit.Test;

import com.saick.base.dao.resulthandler.BeanListResultSetHandler;
import com.saick.base.entity.User;

/**
 * 数据库操作帮助类
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
public class MyDBAssist {

    private DataSource dataSource;

    public MyDBAssist(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testUpdateDB() {
        MyDBAssist dba = new MyDBAssist(DBCPUtil.getDataSource());
        String sql = "select * from person where name=?";
        Object[] pramas = null;
        List<User> list = (List<User>) dba.queryAll(sql,new BeanListResultSetHandler(User.class), pramas);
        for (User user : list) {
            System.out.println(user);
        }
    }

    /**
     * 释放资源
     */
    public void release(ResultSet rs, PreparedStatement stmt, Connection conn) {
        if (rs != null) {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                stmt = null;
                rs = null;
                conn = null;
            }
        }

    }

    /**
     * 更新操作
     * @param sql
     * @param params
     */
    public void updateDB(String sql, Object[] params) {
        // public void updateDB(String sql,Object ...params){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ParameterMetaData pmd = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            pmd = stmt.getParameterMetaData();
            int parameterCount = pmd.getParameterCount();
            if (params == null || params.length == 0) {
                throw new RuntimeException(" !");
            }
            if (parameterCount != params.length) {
                throw new RuntimeException(" !");
            }
            for (int i = 0; i < parameterCount; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            release(rs, stmt, conn);
        }

    }

    /**
     * 执行查询操作
     * @param sql
     * @param handler
     * @param pramas
     * @return
     */
    public Object queryOne(String sql, ResultSetHandler handler,Object... pramas) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ParameterMetaData pmd = null;
        try {
            conn = DBCPUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            pmd = stmt.getParameterMetaData();
            int parameterCount = pmd.getParameterCount();
            ResultSet rs = null;
            if (pramas == null || pramas.length == 0) {
                throw new RuntimeException(" !");
            }
            if (pramas.length != parameterCount) {
                throw new RuntimeException(" !");
            }
            for (int i = 0; i < parameterCount; i++) {
                stmt.setObject(i + 1, pramas[i]);
            }
            rs = stmt.executeQuery();
            Object obj = handler.handle(rs);
            return obj;
        } catch (SQLException e) {
            throw new RuntimeException(" !");
        }
    }

    /**
     * 查询一个列表
     * @param sql
     * @param handler
     * @param pramas
     * @return
     */
    @SuppressWarnings("unused")
    public Object queryAll(String sql, ResultSetHandler handler,
            Object... pramas) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ParameterMetaData pmd = null;
        ResultSetMetaData md = null;
        try {
            conn = DBCPUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            pmd = stmt.getParameterMetaData();
            int parameterCount = pmd.getParameterCount();
            ResultSet rs = null;
            if (pramas == null || pramas.length == 0) {
                throw new RuntimeException("!");
            }
            if (pramas.length != parameterCount) {
                throw new RuntimeException(" !");
            }
            for (int i = 0; i < parameterCount; i++) {
                stmt.setObject(i + 1, pramas[i]);
            }
            rs = stmt.executeQuery();
            Object obj = handler.handle(rs);
            return obj;
        } catch (SQLException e) {
            throw new RuntimeException(" !");
        }finally{
        }
    }
}
