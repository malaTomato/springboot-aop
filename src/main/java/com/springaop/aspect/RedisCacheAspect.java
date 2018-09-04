package com.springaop.aspect;

import com.alibaba.fastjson.JSONObject;
import com.springaop.annotion.RedisCache;
import com.springaop.util.RedisUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * The type Redis cache aspect.
 *
 * @author xiongwu
 */
@Aspect
@Component
public class RedisCacheAspect {

    private RedisCache redisCache;

    /**
     * Point cut.
     */
    @Pointcut(value = "@annotation(com.springaop.annotion.RedisCache)")
    void pointCut(){}

    /**
     * 1.获取注解中的值，生成redis key
     * 2.查询redis缓存中是否存在
     * 2.1 存在直接返回结果集，不再调用方法
     * 2.2 不存在 执行方法，并存储方法
     *
     * @param joinPoint the join point
     * @return object
     */
    @Around(value = "pointCut()")
    public Object around(ProceedingJoinPoint joinPoint){
        System.out.println("controller before");

        String redisKey = this.getRedisKey(joinPoint);
        Object value = RedisUtil.getObject(redisKey);
        if(value == null){
            try {
                joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else {
            return value;
        }
        return null;
    }


    /**
     * After.
     *
     * @param joinPoint the join point
     * @param obj       the obj
     */
    @AfterReturning(value = "pointCut()",returning = "obj")
    public void after(JoinPoint joinPoint,Object obj){
        RedisUtil.set(this.getRedisKey(joinPoint),obj,10000);
    }

    private String getRedisKey(JoinPoint joinPoint){

        Object[] args = joinPoint.getArgs();
        String kind = joinPoint.getKind();
        if(args.length>0){


            int hashCode = args.hashCode();
            System.out.println(hashCode);
            return kind + hashCode;
        }
        return kind;
    }

}
