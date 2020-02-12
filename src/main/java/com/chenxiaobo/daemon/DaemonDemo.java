package com.chenxiaobo.daemon;

/**
 * @Title: DaemonDemo
 * @Description: TODO
 * @Author <a href="mailto:chenxb1993@126.com">陈晓博</a>
 * @Date 2019-11-15 17:22
 * @Version V1.0
 */
public class DaemonDemo {

    public static class DeamonT extends Thread{
        @Override
        public void run() {
            while (true){
                System.out.println("alive");
                System.out.println("alive " + System.currentTimeMillis());
                try {

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String [] args){
            Thread t = new DeamonT();
            t.setDaemon(true);
            t.start();
        try {
            System.out.println("main " + System.currentTimeMillis());
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
