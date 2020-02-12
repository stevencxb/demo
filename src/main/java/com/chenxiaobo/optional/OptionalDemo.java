package com.chenxiaobo.optional;

import java.util.Optional;

/**
 * @Title: OptionalDemo
 * @Description: TODO
 * @Author <a href="mailto:chenxb1993@126.com">陈晓博</a>
 * @Date 2019-10-23 11:31
 * @Version V1.0
 */
public class OptionalDemo {

    public static void main(String [] args){

        Optional<String> opt = Optional.ofNullable(null);
        System.out.println(opt.orElseGet(() -> {
            if ("a".equals(1)){
                return "";
            }else {
                return "a";
            }
        }));

    }


}
