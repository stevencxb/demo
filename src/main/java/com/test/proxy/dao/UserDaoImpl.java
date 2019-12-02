package com.test.proxy.dao;

/**
 * @Title: UserDaoImpl
 * @Description: TODO
 * @Author <a href="mailto:chenxb1993@126.com">陈晓博</a>
 * @Date 2019-10-16 16:20
 * @Version V1.0
 */
public class UserDaoImpl implements UserDao {

    public void query(String name){
        System.out.println("query name = " + name);
    }

}
