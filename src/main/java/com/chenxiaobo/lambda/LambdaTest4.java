package com.chenxiaobo.lambda;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: LambdaTest4
 * @Description: LambdaTest4
 * @Author <a href="mailto:chenxb1993@126.com">陈晓博</a>
 * @Date 2020-02-12 下午2:42
 * @Version V1.0
 */
public class LambdaTest4 {

    public static void main(String [] args){
        List<Dog> list = new ArrayList<>();
        list.add(new Dog(1,"张三",8));
        list.add(new Dog(2,"李四",5));
        list.add(new Dog(3,"王五",10));
        list.add(new Dog(4,"冯六",2));
        list.sort((o1, o2) -> o1.getAge() - o2.getAge());
        System.out.println(list);

    }

}
