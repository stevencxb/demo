package com.chenxiaobo.proxy.proxy;

import com.chenxiaobo.proxy.dao.UserDaoImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Title: LogUserProxy
 * @Description: TODO
 * @Author <a href="mailto:chenxb1993@126.com">陈晓博</a>
 * @Date 2019-10-16 16:41
 * @Version V1.0
 */
public class LogUserJDKProxy implements InvocationHandler {

    private Object target;

    public LogUserJDKProxy(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("log...");
        Object result = method.invoke(target, args);
        return result;
    }

    // 生成代理类
    public Object creatProxyObj(){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }
}
