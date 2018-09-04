package com.springaop.domain;

/**
 * @author xiongwu
 **/
public class User {
    private String name;
    private Integer sex;

    public User() {
    }

    public User(String name, Integer sex) {
        this.name = name;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }


    public static void main(String[] args) {
        User user = new User();
        user.setName("xx");
        user.setSex(1);
        String s = user.getName() + user.getSex();
        System.out.println("hash code :"+ s.hashCode());

        User user1 = new User();
        user1.setName("xx");
        user1.setSex(1);
        String s1 = user1.getName() + user1.getSex();
        System.out.println("hash code :"+ s1.hashCode());
    }
}
