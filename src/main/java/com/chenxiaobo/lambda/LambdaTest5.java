package com.chenxiaobo.lambda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Title: LambdaTest5
 * @Description: LambdaTest5
 * @Author <a href="mailto:chenxb1993@126.com">陈晓博</a>
 * @Date 2020-02-12 下午3:36
 * @Version V1.0
 */
public class LambdaTest5 {

    public static void main(String [] args){
        List<Integer> list = new ArrayList<>();
        Collections.addAll(list,3,4,5,2,4,6,7,8,11,1);

        //遍历全部
        list.forEach(System.out::println);

        System.out.println("========");

        //遍历偶数
        list.forEach(ele -> {
            if (ele % 2 == 0){
                System.out.println(ele);
            }
        });

    }

}
