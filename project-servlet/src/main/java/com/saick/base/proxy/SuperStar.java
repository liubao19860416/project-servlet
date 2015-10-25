package com.saick.base.proxy;

public class SuperStar implements Human {

    @Override
    public void sing(float money) {
        System.out.println("superstar。。。sing" + money);
    }

    @Override
    public void dance(float money) {
        System.out.println("superstar。。。dance" + money);

    }

}
