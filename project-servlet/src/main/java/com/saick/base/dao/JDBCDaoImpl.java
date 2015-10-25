package com.saick.base.dao;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.saick.base.datesource.JDBCUtil;
/**
 * 原始的jdbc操作数据库的例子
 * @author Liubao
 * @2014年12月18日
 *
 */
public class JDBCDaoImpl {

    /**
     * 将文件写入到数据库
     */
    public static void writeFileToDatabase() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.prepareStatement("insert into t1(id,content) values(?,?)");
            // 设置参数，角标从0开始
            stmt.setInt(1, 3);
            File file = new File("test/jdbc.properties");
            // InputStream in = new FileInputStream(file);
            FileReader reader = new FileReader(file);
            // 设置文件写入
            stmt.setCharacterStream(2, reader, (int) file.length());
            // 执行更新
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.realseResourse(conn, stmt, null);
        }
    }

    /**
     * 从数据库读取文本文件
     */
    public static void readFileFromDatabase() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Reader reader = null;
        FileWriter writer = null;
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.prepareStatement("select * from t1 where id='1'");
            rs = stmt.executeQuery();
            if (rs.next()) {
                reader = rs.getCharacterStream("content");
                writer = new FileWriter("test/jdbc-copy.properties");
                char[] buf = new char[1024];
                int len = -1;
                while ((len = reader.read(buf)) != -1) {
                    writer.write(buf, 0, len);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.realseResourse(conn, stmt, rs);
            try {
                if (writer != null) {
                    writer.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                writer = null;
                reader = null;
            }
        }
    }

    /**
     * 批量执行sql
     */
    public static int[] BatchInsert() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = JDBCUtil.getConnection();
            /**
             * 方式1
             */
            stmt = conn.prepareStatement("select * from t1");
            ResultSet resultSet = stmt.executeQuery();
            
            /**
             * 方式2
             */
            String sq1 = "insert into t1(id,content) values(1,'qwertyt1')";
            String sq2 = "insert into t1(id,content) values(2,'qwertyt2')";
            String sq3 = "select * from t1";
            stmt.addBatch(sq1);
            stmt.addBatch(sq2);
            stmt.addBatch(sq3);
            int[] result = stmt.executeBatch();
            stmt.clearBatch();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.realseResourse(conn, stmt, null);
        }
        return null;
    }

}
