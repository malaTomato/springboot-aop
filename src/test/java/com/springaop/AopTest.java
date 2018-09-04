package com.springaop;

import com.springaop.controller.UserController;
import com.springaop.domain.User;
import com.springaop.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * @author xiongwu
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class AopTest {


    @Autowired
    private UserController controller;

    @Test
    public void controllerTest(){
        controller.test("Tony",12);
    }

    @Test
    public void controller1Test(){
        controller.test(Arrays.asList("111"));
    }

    @Test
    public void controller2Test(){
        User user = new User("xw",1);
        controller.testList(Arrays.asList(user));
    }

    @Test
    public void testRedis(){
        RedisUtil.set("ttt","123",10000);
    }
}
