package com.saick.base.dao.resulthandler;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;

/**
 * 结果集为一个List<Bean>映射处理器
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
@SuppressWarnings({ "unused", "rawtypes" })
public class BeanListResultSetHandler implements ResultSetHandler {
    private Class clazz;
    private String classType;

    public BeanListResultSetHandler(Class clazz) {
        this.clazz = clazz;
        this.classType = clazz.getSimpleName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object handle(ResultSet rs) throws SQLException {
        try {
            List beanList = new ArrayList();
            while (rs.next()) {
                Object bean = clazz.newInstance();
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = rsmd.getColumnName(i + 1);
                    Object columnValue = rs.getObject(columnName);
                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(bean, columnValue);
                }
                beanList.add(bean);
            }
            return beanList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
