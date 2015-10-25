package com.saick.base.h2database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.Server;

public class H2DatabaseTest {
    private Server server;
    private String port = "8082";
    private String dbDir = "./database/h2db";
    private String user = "root";
    private String password = "123456";

    public static void main(String[] args) {
        System.out.println("==START==");
        H2DatabaseTest h2 = new H2DatabaseTest();
        h2.startServer();
        h2.useH2();
        h2.stopServer();
        System.out.println("==END==");
    }

    /**
     * 启动H2内存数据库
     */
    public void startServer() {
        try {
            System.out.println("正在启动h2...");
            server = Server.createTcpServer(new String[] { "-tcpPort", port });
            server.start();
        } catch (SQLException e) {
            System.err.println("启动h2出错：" + e.toString());
            throw new RuntimeException(e);
        }
        System.out.println("启动h2成功.");
    }

    /**
     * 停止H2内存数据库
     */
    public void stopServer() {
        if (server != null) {
            System.out.println("正在关闭h2...");
            server.stop();
            System.out.println("关闭成功.");
        }
    }

    /**
     * 使用H2内存数据库
     */
    public void useH2() {
        ResultSet result = null;
        Statement stat =null;
        Connection conn =null;
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:" + dbDir,user, password);
            stat = conn.createStatement();
            // insert data stat.execute("CREATE TABLE TEST(NAME VARCHAR)");
            stat.execute("CREATE TABLE `t_user` (`id` int(4) NOT NULL AUTO_INCREMENT,`name` varchar(32) DEFAULT NULL,`sex` int(2) DEFAULT NULL,PRIMARY KEY (`id`))");
            for(int i=0;i<2;i++){
                stat.execute("INSERT INTO t_user VALUES('"+i+"',"+"'liubao',"+1+")");
                Thread.sleep(1000);
             }
             //use data
            result = stat.executeQuery("select name from test  ");
            int i = 1;
            while (result.next()) {
                System.out.println(i++ + ":" + result.getString("name")+":"+result.getInt("sex"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                result.close();
                stat.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
