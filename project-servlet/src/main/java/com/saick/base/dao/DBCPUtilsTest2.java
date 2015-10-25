package com.saick.base.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import com.saick.base.datesource.DBCPUtil;
import com.saick.base.entity.User;

/**
 * 直接操作数据库测试类
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
public class DBCPUtilsTest2 {

    private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());

    @Test
    public void test01() throws Exception {
        String sql0 = "select *  from person where id in(?,?,?,?,?)";
        Object[] pramas = new Object[] { 8, 7, 9, 10, 19 };
        User per = (User) qr.query(sql0, new BeanHandler(User.class),pramas);
        System.out.println(per);
    }

    @SuppressWarnings({ "unchecked"})
    @Test
    public void test02() throws Exception {
        String sql0 = "select *  from person where id in(?,?,?,?,?)";
        Object[] pramas = new Object[] { 8, 7, 9, 10, 19 };
        List<User> list = (List<User>) qr.query(sql0, new BeanListHandler(User.class), pramas);
        for (User user : list)
            System.out.println(user);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void test03() throws Exception {
        String sql0 = "select *  from t_coupon where id=4";
        Object[] pramas = new Object[] { 8, 7, 9, 10, 19 };
        Object[] pers = (Object[]) qr.query(sql0, pramas, new ArrayHandler());
        for (Object per : pers)
            System.out.println(per);
    }

    @SuppressWarnings({ "unchecked", "unused" })
    @Test
    public void test04() throws Exception {
        String sql0 = "select *  from person";
        Object[] pramas = new Object[] { 8, 7, 9, 10, 19 };
        List<Object[]> list = (List<Object[]>) qr.query(sql0,
                new ArrayListHandler());
        for (Object[] user : list) {
            System.out.println(user);
        }
    }

    @SuppressWarnings({ "unused", "unchecked" })
    @Test
    public void test05() throws Exception {
        String sql0 = "select *  from person";
        Object[] pramas = new Object[] { 8, 7, 9, 10, 19 };
        List<Object> list = (List<Object>) qr.query(sql0,
                new ColumnListHandler("name"));
        // List<Object> list = qr.query(sql0, new ColumnListHandler(2));
        for (Object user : list) {
            System.out.println(user);
        }
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
    @Test
    public void test06() throws Exception {
        String sql0 = "select *  from person where id=?";
        Map<String, Object> map = (Map<String, Object>) qr.query(sql0, 4,
                new MapHandler());
        for (Object me : map.entrySet()) {
            System.out.println(me);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test07() throws Exception {
        String sql0 = "select *  from person";
        List<Map<String, Object>> listmap = (List<Map<String, Object>>) qr
                .query(sql0, new MapListHandler());
        for (Map<String, Object> map : listmap) {
            System.out.println(map.get("id") + ":" + map.get("name") + ":"
                    + map.get("age") + ":" + map.get("salary"));
        }
    }

    @Test
    public void test08() throws Exception {
        String sql0 = "select count(*)  from person where id>100";
        Object object = qr.query(sql0, new ScalarHandler());
        System.out.println(object);
    }

    @SuppressWarnings({ "unchecked", "unused" })
    @Test
    public void test09() throws Exception {
        String sql0 = "select *  from person";
        Map<Object, Map<String, Object>> mapMap = (Map<Object, Map<String, Object>>) qr
                .query(sql0, new KeyedHandler("id"));
        for (Map.Entry<Object, Map<String, Object>> lmap : mapMap.entrySet()) {
            Object key = lmap.getKey();
            Map<String, Object> map = lmap.getValue();
            System.out.println(key + ":[" + map.get("id") + ","
                    + map.get("name") + "," + map.get("age") + ","
                    + map.get("salary") + "]");
        }
        for (Map.Entry<Object, Map<String, Object>> lmap : mapMap.entrySet()) {
            Object key = lmap.getKey();
            Map<String, Object> map = lmap.getValue();
            for (Object me : map.entrySet()) {
                System.out.print(me + ",");
            }
            System.out.println("\r\n------------------------");
        }
    }

}
