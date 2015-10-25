package com.saick.base.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.saick.base.datesource.C3P0Util;
import com.saick.base.entity.User;
import com.saick.base.service.api.BusinessService;
/**
 * 动态代理和读取配置文件，两种方式获取对应的service服务对象
 * 
 * @author Liubao
 * @2014年12月18日
 *
 */
public class BusinessServiceFactory {
    
    private static BusinessService service = null;
    /**
     * 方式1：读取配置文件实现
     */
    @SuppressWarnings({ "rawtypes", "static-access" })
    public BusinessServiceFactory() {
        ResourceBundle rb = ResourceBundle.getBundle("config");
        String BusinessServiceClass = rb.getString("BusinessServiceClass");
        try {
            Class c = Class.forName(BusinessServiceClass);
            BusinessService bs = (BusinessService) c.newInstance();
            this.service = bs;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 方式2：动态代理实现
     */
    @SuppressWarnings({"unchecked","deprecation"})
    public static BusinessService newProxyInstance(final User user) {
        return (BusinessService) Proxy.newProxyInstance(service.getClass()
                .getClassLoader(), service.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method,Object[] args) throws Throwable {
                        if (user == null) {
                            throw new RuntimeException(" ");
                        }
                        if (method.isAnnotationPresent(PrivilegeInfo.class)) {
                            PrivilegeInfo privilegeInfo = method.getAnnotation(PrivilegeInfo.class);
                            String needPrivilege = privilegeInfo.value();
                            QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
                            String sql = "select privilege.* from privilege,user_privilege where privilege.id = user_privilege.privilege_id and  user_privilege.user_id=?";
                            List<Privilege> privileges = (List<Privilege>) qr.query(sql,user.getUsername(),new BeanListHandler(Privilege.class));
                            for (Privilege privilege : privileges) {
                                if (privilege.getName().equals(needPrivilege)) {
                                    return method.invoke(service, args);
                                }
                            }
                            throw new RuntimeException("!");
                        } else {
                            return method.invoke(service, args);
                        }
                    }
                });
    }

}
