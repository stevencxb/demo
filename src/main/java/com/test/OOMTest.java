package com.test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Title: OOMTest
 * @Description: TODO
 * @Author <a href="mailto:chenxb1993@126.com">陈晓博</a>
 * @Date 2018-11-07 15:15
 * @Version V1.0
 */

//内存溢出测试
public class OOMTest {

    public static void main(String [] args){
        List<User> list = new ArrayList<User>();
        int i = 0 ;
        while (true){
            list.add(new User(i++, UUID.randomUUID().toString().replace("-","")));
        }
    }

}
