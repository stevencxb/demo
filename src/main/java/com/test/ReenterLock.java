package com.test;



public class ReenterLock {

    public static void main(String [] args){

        Phone phone = new Phone();
        new Thread(()-> {
            phone.sendMail();
        },"t2").start();

        new Thread(()-> {
            phone.sendSMS();
        },"t1").start();





    }

}


class Phone{

    public synchronized void sendSMS() {
        System.out.println(Thread.currentThread().getName()+"======send sms");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sendMail();
    }

    public synchronized void sendMail() {
        System.out.println(Thread.currentThread().getName()+"======send mail");
    }
}