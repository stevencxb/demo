package com.chenxiaobo;

/**
 * @Title: Lambada
 * @Description: TODO
 * @Author <a href="mailto:chenxb1993@126.com">陈晓博</a>
 * @Date 2019-02-26 15:10
 * @Version V1.0
 */
public class SimpleLambda {

    public static void main(String [] args){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        }).start();

        new Thread(() ->System.out.println("hello")).start();
    }



}
