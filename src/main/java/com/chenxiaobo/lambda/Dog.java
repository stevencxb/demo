package com.chenxiaobo.lambda;

/**
 * @Title: Dog
 * @Description: Dog
 * @Author <a href="mailto:chenxb1993@126.com">陈晓博</a>
 * @Date 2020-02-12 下午2:28
 * @Version V1.0
 */
public class Dog {

    private Integer id;

    private String name;

    private Integer age;

    public Dog(){
        System.out.println("无参构造方法执行");
    }

    public Dog(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
        System.out.println("有参构造方法执行");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
