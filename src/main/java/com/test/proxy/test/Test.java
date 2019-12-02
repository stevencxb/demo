package com.test.proxy.test;

import com.test.proxy.dao.UserDao;
import com.test.proxy.dao.UserDaoImpl;
import com.test.proxy.proxy.LogUserJDKProxy;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Title: Test
 * @Description: TODO
 * @Author <a href="mailto:chenxb1993@126.com">陈晓博</a>
 * @Date 2019-10-16 16:42
 * @Version V1.0
 */
public class Test {

    public static void main(String [] args){
//        LogUserJDKProxy proxy = new LogUserJDKProxy(new UserDaoImpl());
//        UserDao userDao = (UserDao)proxy.creatProxyObj();
//        userDao.query("asasas");

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserDaoImpl.class);
        enhancer.setCallback(new MethodInterceptor() {
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                System.out.println("before method run...");
                Object result = proxy.invokeSuper(obj, args);
                System.out.println("after method run...");
                return result;
            }
        });
        UserDaoImpl sample = (UserDaoImpl) enhancer.create();
        sample.query("ass");
    }

}
