package com.saick.base.dao.api;

import java.util.List;

import com.saick.base.entity.User;

/**
 * 测试总的DAO接口
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
public interface DAO {
    public List<User> findAll();
}
