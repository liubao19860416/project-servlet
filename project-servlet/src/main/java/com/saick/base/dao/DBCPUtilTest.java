package com.saick.base.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Clob;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialClob;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

import com.saick.base.datesource.DBCPUtil;

/**
 * 直接操作数据库测试类
 * 
 * 写入大文本文件Clob
 * 
 * 写入Blob本图片文件
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
public class DBCPUtilTest {

    private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());

    @Test
    public void test01() throws Exception {
        String sql0 = "insert into t_servlet values(001,'zhangsan','123456')";
        qr.update(sql0, null);
        String sql1 = "insert into t_servlet values(?,?,?)";
        Object[] pramas = new Object[] { 002, "wangwu", "123456" };
        qr.update(sql1, pramas);
    }

    @Test
    public void test02() throws Exception {
        String sql = "update person set salary=9999 where id in(?,?,?,?,?)";
        Object[] pramas = new Object[] { 8, 7, 9, 10, 19 };
        qr.update(sql, pramas);
    }

    @Test
    public void test03() throws Exception {
        String sql0 = "delete from person where id in(?,?,?,?,?)";
        Object[] pramas = new Object[] { 8, 7, 9, 10, 19 };
        qr.update(sql0, pramas);
        String sql1 = "delete from person where id=3";
        qr.update(sql1);
    }

    /**
     * 写入大文本文件Clob
     */
    @Test
    public void test06() throws Exception {
        String filePath="D:\\eclipse-20141015\\workspace\\project-servlet\\src\\main\\resources\\dbcpconfig.properties";
        File file = new File(filePath);
        //InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("dbcpconfig.properties");
        FileReader reader = new FileReader(file);
        char buf[] = new char[(int) file.length()];
        reader.read(buf);
        reader.close();
        Clob clob = new SerialClob(buf);
        Object[] pramas = new Object[] { 003, "wangwu", "123456" ,clob,clob,clob};
        qr.update("insert into t_servlet values(?,?,?,?,?,?)", pramas);
        //qr.update("insert into t_servlet values(?,?,?,?)", pramas);
    }

    /**
     * 写入Blob本图片文件
     */
    @Test
    public void test07() throws Exception {
        File file = new File("D://temp//1.jpg");
        InputStream reader = new FileInputStream(file);
        byte[] buf = new byte[reader.available()];
        reader.read(buf);
        reader.close();
        Blob blob = new SerialBlob(buf);
        Object[] pramas = new Object[] { 003, "wangwu", "123456" ,blob};
        qr.update("insert into t_servlet values(?,?,?,?)", pramas);
        //qr.update("alter table t5 modify content longblob");
    }

    /**
     * 联系写多个文件
     */
    @Test
    public void test08() throws Exception {
        Object params[][] = new Object[5][];
        System.out.println(params.length);
        for (int i = 0; i < params.length; i++) {
            params[i] = new Object[] { i + 1, "ok" + i + 1 };
        }
        qr.batch("insert into t5 values(?,?)", params);

    }

}
