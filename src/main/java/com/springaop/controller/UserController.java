package com.springaop.controller;

import com.springaop.annotion.RedisCache;
import com.springaop.annotion.WriteLog;
import com.springaop.domain.User;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * @author xiongwu
 **/

@Controller
public class UserController {


    @WriteLog
    @RedisCache(timeOutSecond = 100)
    public void test(String name,Integer age){
        System.out.println("this is a function");
    }

    @WriteLog
    @RedisCache(timeOutSecond = 100)
    public void test(){
        System.out.println("this is a function");
    }

    @WriteLog
    public void test(List<String> list){
        System.out.println("this is a function");
    }


    @WriteLog
    @RedisCache(timeOutSecond = 100)
    public String testList(List<User> list){
        System.out.println("execute is a function");
        return "Ok";
    }
}
