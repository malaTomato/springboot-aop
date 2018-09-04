package com.springaop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author xiongwu
 **/
@Aspect
@Component
public class ControllerAspect {


    @Pointcut(value = "@annotation(com.springaop.annotion.SlaverDataSource)")
    public void pointCut(){}

    @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        System.out.println("controller before");

        //目标方法参数
        Object[] args = joinPoint.getArgs();
        Arrays.stream(args).forEach(System.out::println);

    }

}
