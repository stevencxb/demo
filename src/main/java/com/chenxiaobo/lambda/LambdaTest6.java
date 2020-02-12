package com.chenxiaobo.lambda;

import java.util.function.Supplier;

/**
 * @Title: LambdaTest6
 * @Description: 闭包
 * @Author <a href="mailto:chenxb1993@126.com">陈晓博</a>
 * @Date 2020-02-12 下午3:45
 * @Version V1.0
 */
public class LambdaTest6 {

    public static void main(String [] args){

        int num = 10;

        Supplier<Integer> supplier = () -> {
//            num = num + 1;
            return num;
        };

        System.out.println(supplier.get());
    }

}
