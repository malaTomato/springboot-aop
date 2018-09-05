package com.springaop.aspect;

import com.alibaba.fastjson.JSONObject;
import com.springaop.annotion.RedisCache;
import com.springaop.util.RedisUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * The type Redis cache aspect.
 *
 * @author xiongwu
 */
@Component
@Aspect
public class RedisCacheAspect {

    private RedisCache redisCache;
    /**
     * Point cut.
     */
    @Pointcut(value = "@annotation(com.springaop.annotion.RedisCache)")
    void pointCut() {
    }

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
    public Object around(ProceedingJoinPoint joinPoint) {
        System.out.println("controller before");

        String redisKey = this.getRedisKey(joinPoint);
        System.out.println(redisKey);
        Object value = RedisUtil.getString(redisKey);
        if (value == null) {
            try {
                Object proceed = joinPoint.proceed();
                this.setRedisCache(joinPoint);
                RedisUtil.set(this.getRedisKey(joinPoint), proceed, redisCache.timeOutSecond());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else {
            return value;
        }
        return null;
    }

    private void setRedisCache(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        redisCache = signature.getMethod().getAnnotation(RedisCache.class);
    }

    private String getRedisKey(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        return signature.getDeclaringType() + ":" + signature.getName() + ":" + JSONObject.toJSONString(joinPoint.getArgs()).hashCode();
    }

}
