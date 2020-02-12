package com.chenxiaobo.lambda;

/**
 * @Title: LambdaTest3
 * @Description: LambdaTest3
 * @Author <a href="mailto:chenxb1993@126.com">陈晓博</a>
 * @Date 2020-02-12 下午2:31
 * @Version V1.0
 */
public class LambdaTest3 {

    public static void main(String [] args){

        //重写匿名内部类
        DogCreator dogCreator = () -> new Dog();
        dogCreator.getDog();

        //无参构造方法引用
        DogCreator dogCreator1 = Dog::new;
        dogCreator1.getDog();

        //有参构造方法引用
        DogCreator2 dogCreator2 = Dog::new;
        dogCreator2.getDog(1,"小狗" , 8);
    }

}

@FunctionalInterface
interface DogCreator{

    Dog getDog();

}

@FunctionalInterface
interface DogCreator2{

    Dog getDog(Integer id , String name , Integer age);

}