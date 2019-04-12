package com.test.lambda;

import java.util.Arrays;
import java.util.List;

/**
 * @Title: LambdaDemo
 * @Description: TODO
 * @Author <a href="mailto:chenxb1993@126.com">陈晓博</a>
 * @Date 2019-04-09 16:05
 * @Version V1.0
 */
public class LambdaDemo {

    public static void main(String [] args){
        String[] atp = {"Rafael Nadal", "Novak Djokovic",
                "Stanislas Wawrinka", "David Ferrer",
                "Roger Federer", "Andy Murray",
                "Tomas Berdych", "Juan Martin Del Potro",
                "Richard Gasquet", "John Isner"};

        List<String> players =  Arrays.asList(atp);

//        players.forEach((player) -> System.out.println(player));
        players.forEach(System.out::println);

        new Thread(()->System.out.println("1")).start();

        Runnable runnable = ()->System.out.println("1111");

        runnable.run();

    }






}
