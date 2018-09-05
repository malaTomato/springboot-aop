package com.springaop.aspect;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author xiongwu
 **/
@Component
@Aspect
public class WriteLogAspect {

    private static final Logger LOG = LoggerFactory.getLogger(WriteLogAspect.class);

    @Pointcut(value = "@annotation(com.springaop.annotion.WriteLog)")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void printRequestParameter(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        LOG.info("-*---------------------------");
        LOG.info(" | " + signature.getDeclaringTypeName() + "." + signature.getName() + "()");
        Object[] args = joinPoint.getArgs();
        LOG.info(" | 参数 :" + JSONObject.toJSON(args));
        LOG.info("-*---------------------------");
    }

    @AfterReturning(value = "pointCut()", returning = "keys")
    public void printReturnValue(JoinPoint joinPoint, Object keys) {
        Signature signature = joinPoint.getSignature();
        LOG.info("-*---------------------------");
        LOG.info(" | " + signature.getDeclaringTypeName() + "." + signature.getName() + "()");
        LOG.info(" | 返回值：" + JSONObject.toJSON(keys));
        LOG.info("-*---------------------------");
    }



}
