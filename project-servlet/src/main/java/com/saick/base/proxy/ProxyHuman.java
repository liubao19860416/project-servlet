package com.saick.base.proxy;

/**
 * Human的动态代理类
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
public class ProxyHuman implements Human {
    private Human h;

    public ProxyHuman(Human h) {
        this.h = h;
    }

    @Override
    public void sing(float money) {
        money = money / 2;
        h.sing(money);
    }

    @Override
    public void dance(float money) {
        money = money / 2;
        h.dance(money);
    }

}
