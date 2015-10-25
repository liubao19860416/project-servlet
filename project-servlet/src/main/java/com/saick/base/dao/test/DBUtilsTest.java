package com.saick.base.dao.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.junit.Test;

import com.saick.base.datesource.DBCPUtil;
import com.saick.base.entity.User;

public class DBUtilsTest {
    @Test
    public void demo4() throws SQLException {
        // 插入account 一条记录
        QueryRunner queryRunner = new QueryRunner(DBCPUtil.getDataSource());
        String sql = "insert into account values(null,?,?)";
        queryRunner.update(sql, new Object[] { "小虎", 10000 });
    }

    @Test
    public void demo3() throws SQLException {
        // 转账 小刘 转给 小王1000 ----- 手动管理事务
        QueryRunner queryRunner = new QueryRunner();// 不传递连接池给框架
        Connection conn = DBCPUtil.getConnection();
        // 管理事务，将事务自动提交关闭
        conn.setAutoCommit(false);
        try {
            queryRunner.update(conn,
                    "update account set money = money-1000 where name='小刘'");
            int d = 1 / 0;
            queryRunner.update(conn,
                    "update account set money = money+1000 where name='小王'");
            // 事务正常 提交
            System.out.println("事务提交！");
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("事务回滚");
            conn.rollback();
        } finally {
            // 关闭连接
            DbUtils.closeQuietly(conn);
        }
    }

    @Test
    public void demo2() throws SQLException {
        // 转账 小刘 转给 小王1000
        // 事务框架来管理，一条sql 一个事务
        QueryRunner queryRunner = new QueryRunner(DBCPUtil.getDataSource());
        queryRunner
                .update("update account set money = money-1000 where name='小刘'");
        int d = 1 / 0;
        queryRunner
                .update("update account set money = money+1000 where name='小王'");
    }

    @Test
    public void demo1() throws SQLException {
        // 准备结果集响应器,查询所有user信息
        ResultSetHandler rsHandler = new ResultSetHandler() {
            @Override
            public List<User> handle(ResultSet rs) throws SQLException {
                List<User> users = new ArrayList<User>();
                while (rs.next()) {
                    User user = new User();
                    user.setUsername(rs.getString("name"));
                    users.add(user);
                }
                return users;
            }
        };

        QueryRunner queryRunner = new QueryRunner(DBCPUtil.getDataSource());

        List<User> users = (List<User>) queryRunner.query("select * from user",
                rsHandler);

        for (User user : users) {
            System.out.println(user.getPassword() + "," + user.getUsername());
        }
    }
}