package com.chenxiaobo.lambda;

/**
 * @Title: LambdaDemo
 * @Description: Lambda
 * @Author <a href="mailto:chenxb1993@126.com">陈晓博</a>
 * @Date 2020-02-12 10:06
 * @Version V1.0
 */
public class LambdaDemo {

    public static void main(String[] args) {

        //1.接口实现
        Compare compare1 = new Compare1();

        //2.匿名内部类实现
        Compare compare2 = new Compare() {
            @Override
            public int compare(int a, int b) {
                return a - b;
            }
        };

        //3.lambda实现
        Compare compare3 = (a , b) -> a - b;

    }

}

class Compare1 implements Compare{

    @Override
    public int compare(int a, int b) {
        return a - b ;
    }
}


@FunctionalInterface
interface Compare{

    int compare(int a , int b);

}




