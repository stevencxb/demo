package com.chenxiaobo.lambda;

import com.chenxiaobo.lambda.interfaces.*;

/**
 * @Title: LambdaTest1
 * @Description: TODO
 * @Author <a href="mailto:chenxb1993@126.com">陈晓博</a>
 * @Date 2020-02-12 11:42
 * @Version V1.0
 */
public class LambdaTest1 {

    public static void main(String [] args){

        //多个参数无返回值
        //省略参数类型，必须所有的参数类型同时省略
        NoneReturnMutiParam noneReturnMutiParam = ( a ,  b) -> {
            System.out.println("一个参数无返回值：" + a + "--" + b );
        };
        noneReturnMutiParam.test(8,9);

        //一个参数无返回值
        //如果有一个参数的话可以省略括号
        NoneReturnOneParam noneReturnOneParam = a -> {
            System.out.println("一个参数无返回值：" + a );
        };
        noneReturnOneParam.test(99);

        //无参数无返回值
        //如果方法体只有一行语，可以省略大括号
        NoneReturnNoParam noneReturnNoParam = () -> System.out.println("无参数无返回值");
        noneReturnNoParam.test();

        //多个参数一个返回值
        //如果方法体只有一行语，并且是返回语句，则可以省略大括号和return
        OneReturnMutiParam oneReturnMutiParam = (int a , int b) ->  a + b ;
        System.out.println(oneReturnMutiParam.test(2, 6));

    }

}
