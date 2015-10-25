package com.saick.base.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.saick.base.dao.api.DAO;
import com.saick.base.datesource.JDBCUtil;
import com.saick.base.entity.User;

/**
 * 原始的jdbc操作数据库的例子
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
public class JDBCUserDaoImpl implements DAO{

    @Override
    public List<User> findAll() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<User> list = new ArrayList<User>();
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.prepareStatement("select * from customer");
            rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setPassword(rs.getString("password"));
                user.setUsername(rs.getString("username"));
                list.add(user);
            }
            if (list != null) {
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.realseResourse(conn, stmt, rs);
        }
        return null;
    }

    public void save(User user) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn
                    .prepareStatement("insert into customer(id,name,gender,birthday,cellphone,email,hobby,type,description) values(?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, UUID.randomUUID().toString());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.realseResourse(conn, stmt, null);
        }

    }

    public void delete(String userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtil.getConnection();
            // boolean isExist =
            // conn.createStatement().execute("select * from customer where id='"
            // + customerID + "'");
            rs = conn.prepareStatement(
                    "select * from customer where id='" + userId + "'")
                    .executeQuery();
            if (rs.next()) {
                // stmt =conn.prepareStatement("delete from customer where id='"
                // + customerID + "'");
                stmt = conn.prepareStatement("delete from customer where id=?");
                stmt.setString(1, userId);
                // stmt = conn.prepareStatement("truncate table customer");
                stmt.executeUpdate();
            } else {
                throw new RuntimeException(" !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.realseResourse(conn, stmt, rs);
        }
    }

    public void update(User user) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn
                    .prepareStatement("update customer set name=?,gender=?,birthday=?,cellphone=?,email=?,hobby=?,type=?,description=? where id='"
                            + user.getUsername() + "'");
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.realseResourse(conn, stmt, null);
        }

    }

    public User findById(String userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.prepareStatement("select * from customer where id=?");
            stmt.setString(1, userId);
            rs = stmt.executeQuery();

            User user = new User();
            if (rs.next()) {
                user.setPassword(rs.getString("password"));
                user.setUsername(rs.getString("username"));
            }
            if (user != null) {
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.realseResourse(conn, stmt, rs);
        }
        return null;
    }

    public int getTotalRecordsNum() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.prepareStatement("select count(*) from customer");
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
                // return Integer.parseInt(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("  !");
        } finally {
            JDBCUtil.realseResourse(conn, stmt, rs);
        }
        return 0;
    }

    public List<User> findPagesUsers(int startIndex, int pageSize) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.prepareStatement("select * from customer limit ?,?");
            stmt.setInt(1, startIndex);
            stmt.setInt(2, pageSize);
            rs = stmt.executeQuery();
            List<User> list = new ArrayList<User>();
            while (rs.next()) {
                User user = new User();
                user.setPassword(rs.getString("password"));
                user.setUsername(rs.getString("username"));
                list.add(user);
            }
            if (list != null && list.size() > 0) {
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("!");
        } finally {
            JDBCUtil.realseResourse(conn, stmt, rs);
        }
        return null;
    }

}
