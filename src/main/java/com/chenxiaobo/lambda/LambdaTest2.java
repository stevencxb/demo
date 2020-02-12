package com.chenxiaobo.lambda;

import com.chenxiaobo.lambda.interfaces.OneReturnMutiParam;

/**
 * @Title: LambdaTest2
 * @Description: LambdaTest2
 * @Author <a href="mailto:chenxb1993@126.com">陈晓博</a>
 * @Date 2020-02-12 13:22
 * @Version V1.0
 */
public class LambdaTest2 {

    public static void main(String [] args){

        //静态方法发隶属者为当前类
        OneReturnMutiParam oneReturnMutiParam = LambdaTest2::add;
        System.out.println(oneReturnMutiParam.test(5, 6));

        //普通方法的隶属者为类的对象
        LambdaTest2 lambdaTest2 = new LambdaTest2();
        OneReturnMutiParam oneReturnMutiParam1 = lambdaTest2::sub;
        System.out.println(oneReturnMutiParam1.test(9, 6));

    }

    private static int add(int a , int b){
        return a + b ;
    }

    private int sub(int a , int b){
        return a - b ;
    }
}
