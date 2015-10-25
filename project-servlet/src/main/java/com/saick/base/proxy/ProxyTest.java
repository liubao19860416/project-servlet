package com.saick.base.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理调用测试类
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
public class ProxyTest {

    public static void main(String[] args) {

         Human human=new ProxyHuman(new SuperStar());
         human.sing(100);
         human.dance(200);

        final Human superStar = new SuperStar();
        Human proxyMan = (Human) Proxy.newProxyInstance(superStar.getClass()
                .getClassLoader(), superStar.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method,
                            Object[] args) throws Throwable {
                        if ("sing".equals(method.getName())) {
                            float money = Float.parseFloat(args[0].toString());
                            if (money < 1000) {
                                throw new RuntimeException(" !");
                            }
                            money = money / 2;
                            method.invoke(superStar, money);
                        } else if ("dance".equals(method.getName())) {
                            float money = Float.parseFloat(args[0].toString());
                            if (money < 1000) {
                                throw new RuntimeException(" !");
                            }
                            money = money / 2;
                            method.invoke(superStar, money);
                        }
                        return null;
                        //return method.invoke(superStar, args);
                    }
                });
        proxyMan.sing(10000);
        proxyMan.dance(20000);
    }
}
