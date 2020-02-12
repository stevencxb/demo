package com.chenxiaobo.lambda;

import com.chenxiaobo.lambda.interfaces.*;

/**
 * @Title: LambdaTest
 * @Description: LambdaTest
 * @Author <a href="mailto:chenxb1993@126.com">陈晓博</a>
 * @Date 2020-02-12 11:42
 * @Version V1.0
 */
public class LambdaTest {

    public static void main(String [] args){

        //无参数无返回值
        NoneReturnNoParam noneReturnNoParam = () -> {
            System.out.println("无参数无返回值");
        };
        noneReturnNoParam.test();

        //一个参数无返回值
        NoneReturnOneParam noneReturnOneParam = (int a) -> {
            System.out.println("一个参数无返回值：" + a );
        };
        noneReturnOneParam.test(99);

        //多个参数无返回值
        NoneReturnMutiParam noneReturnMutiParam = (int a , int b) -> {
            System.out.println("一个参数无返回值：" + a + "--" + b );
        };
        noneReturnMutiParam.test(8,9);

        //无参数一个返回值
        OneReturnNoParam oneReturnNoParam = () -> {
            System.out.println("无参数一个返回值");
            return 88;
        };
        System.out.println(oneReturnNoParam.test());

        //一个参数一个返回值
        OneReturnOneParam oneReturnOneParam = (int a) -> {
            System.out.println("一个参数一个返回值：" + a);
            return a;
        };
        System.out.println(oneReturnOneParam.test(7));

        //多个参数一个返回值
        OneReturnMutiParam oneReturnMutiParam = (int a , int b) -> {
            System.out.println("多个参数一个返回值：" + a + "--" + b );
            return a + b;
        };
        System.out.println(oneReturnMutiParam.test(2, 6));

    }

}
