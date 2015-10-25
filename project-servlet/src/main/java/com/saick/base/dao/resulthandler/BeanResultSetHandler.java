package com.saick.base.dao.resulthandler;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;

/**
 * 结果集为一个Bean对象
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
@SuppressWarnings("rawtypes")
public class BeanResultSetHandler implements ResultSetHandler {

    private Class clazz;

    public BeanResultSetHandler(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object handle(ResultSet rs) throws SQLException {
        try {
            if (rs.next()) {
                Object bean = clazz.newInstance();
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = rsmd.getColumnName(i + 1);
                    Object columnValue = rs.getObject(columnName);
                    // Object columnValue = rs.getObject(i + 1);
                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(bean, columnValue);
                }
                return bean;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
