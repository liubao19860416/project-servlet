package com.saick.base.i18n;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Properties文件国际化测试小例子
 * 货币格式国际化
 * 日期格式国际化
 * @author Liubao
 * @2014年12月16日
 * 
 */
public class I18NTestDemo {

    public static void main(String[] args) throws Exception {
        dateToString();
        stringToNumber();
        /**
         * baseName:表示基名，包含全路径 locale：表示找哪个国家的语言
         * 但存在_CN结尾的文件名的时候，首先被访问；然后才能访问不带后缀名称的文件；
         * 文件的后缀，它会自动进行获取的，如_US等propeties文件后缀信息
         */
        ResourceBundle rb = ResourceBundle.getBundle("messages", Locale.CHINA);
        Object object = rb.getObject("mobile.is.null");
        System.out.println(object);
        rb = ResourceBundle.getBundle("messages", Locale.US);
        object = rb.getObject("name.is.null");
        System.out.println(object);
    }

    /**
     * 测试Local的使用，显示当地日期格式，日期格式国际化
     */
    private static void dateToString() {
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL,
                DateFormat.DEFAULT, Locale.CHINA);
        // 将日期时间转成字符串
        String str = df.format(new Date());
        System.out.println(str);
    }

    /**
     * 货币格式国际化
     */
    private static void stringToNumber() throws ParseException {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.CHINA);
        Double d = (Double) nf.parse("￥3.14");
        System.out.println(d);
        nf = NumberFormat.getCurrencyInstance(Locale.US);
        String str = nf.format(3.14);
        System.out.println(str);
    }

}
