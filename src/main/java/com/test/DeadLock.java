package com.test;

/**
 * @Title: DeadLock
 * @Description: TODO
 * @Author <a href="mailto:chenxb1993@126.com">陈晓博</a>
 * @Date 2019-02-19 9:27
 * @Version V1.0
 */
public class DeadLock {

    private static Object lock1 = new Object();
    private static Object lock2 = new Object();

    public static void main(String [] args){
        new Thread(() -> {
            synchronized (lock1){
                try {
                    System.out.println("thread1 begin");
                    Thread.sleep(5000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                synchronized (lock2){
                    System.out.println("thread2 end");
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (lock2){
                try {
                    System.out.println("thread2 begin");
                    Thread.sleep(5000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                synchronized (lock1){
                    System.out.println("thread1 end");
                }
            }
        }).start();

        System.out.println("main thread end");

    }


}
