package com.springaop.aspect;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author xiongwu
 **/
@Component
@Aspect
public class WriteLogAspect {

    private static final Logger LOG = LoggerFactory.getLogger(WriteLogAspect.class);

    @Pointcut(value = "@annotation(com.springaop.annotion.WriteLog)")
    public void pointCut(){}

    @Before("pointCut()")
    public void printRequestParameter(JoinPoint joinPoint){

    }

    @AfterReturning(value = "pointCut()",returning = "keys")
    public void printReturnValue(JoinPoint joinPoint,Object keys){
        Signature signature = joinPoint.getSignature();
        LOG.info("-*---------------------------");
        LOG.info(" | "+signature.getDeclaringTypeName()+"."+signature.getName()+"()");
        LOG.info(" | 返回值："+ JSONObject.toJSON(keys));
        LOG.info("-*---------------------------");
    }



    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint){
        Object result = null;
        Signature signature = joinPoint.getSignature();
        LOG.info("-*---------------------------");
        LOG.info(" | "+signature.getDeclaringTypeName()+"."+signature.getName()+"()");
        Object[] args = joinPoint.getArgs();
        final int[] i = {0};
        Arrays.stream(args).forEach(v->{

            if(v instanceof String){
                System.out.println("这是一个string");
                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {


                }
            }



            LOG.info(" | 参数 " + i[0] +":" +  JSONObject.toJSON(v));
            i[0]++;
        });
        LOG.info("-*---------------------------");
        return result;
    }

}
