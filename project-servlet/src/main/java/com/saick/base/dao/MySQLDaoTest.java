package com.saick.base.dao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.saick.base.dao.api.DAO;
import com.saick.base.entity.User;

/**
 * 动态代理的演化过程及比较
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
public class MySQLDaoTest {

    /**
     * 方式1：直接调用DAO对象方法
     * 方式2：构造方法注入的方式
     * 方式3：动态代理方式
     */
    @Test
    public void test01() {
        JDBCUserDaoImpl dao = new JDBCUserDaoImpl();
        List<User> list = dao.findAll();
        Assert.assertNotNull(list);
    }

    @Test
    public void test04() {
        final JDBCUserDaoImpl dao = new JDBCUserDaoImpl();
        DAO proxyDao = (DAO) Proxy.newProxyInstance(dao.getClass().getClassLoader(), dao.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method,Object[] args) throws Throwable {
                        if (true) {
                            method.invoke(dao, args);
                        }
                        return null;
                        // return method.invoke(dao, args);
                    }
                });
        //调用代理对象对应的方法
        proxyDao.findAll();
    }

}
