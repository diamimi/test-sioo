package com.config;

import com.mapper.Mapper114;
import com.mapper.Mapper21;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @Author: HeQi
 * @Date:Create in 10:30 2018/7/16
 */
@Aspect
@Component
public class DataSourceAspect {
    @Before("execution(* com.mapper.*.*(..))")
    public void setDataSourceKey(JoinPoint point){
        //连接点所属的类实例是ShopDao
        if(point.getTarget() instanceof Mapper21){
            DatabaseContextHolder.setDatabaseType(DatabaseType.database21);
        }else if(point.getTarget() instanceof Mapper114){//连接点所属的类实例是UserDao（当然，这一步也可以不写，因为defaultTargertDataSource就是该类所用的mytestdb）
            DatabaseContextHolder.setDatabaseType(DatabaseType.database114);
        }
    }
}
