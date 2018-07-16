package com.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: HeQi
 * @Date:Create in 10:24 2018/7/16
 */
@Configuration
@MapperScan(basePackages = "com.mapper")
public class MybatisConfig {

    private static final Logger logger = LoggerFactory.getLogger(MybatisConfig.class);

    @Autowired
    private Environment env;

    @Autowired
    private DatasourceProperties21 datasourceProperties21;

    @Autowired
    private DatasourceProperties114 datasourceProperties114;





    @Bean(name = "database21")
    @Qualifier("database21")
    public DataSource dataSource21() {
        DruidDataSource druidDataSource = new DruidDataSource();
        try {
            druidDataSource.setDriverClassName(datasourceProperties21.getDriverClassName());
            druidDataSource.setUrl(datasourceProperties21.getUrl());
            druidDataSource.setUsername(datasourceProperties21.getUsername());
            druidDataSource.setPassword(datasourceProperties21.getPassword());
            druidDataSource.setInitialSize(datasourceProperties21.getInitialSize());
            druidDataSource.setMinIdle(datasourceProperties21.getMinIdle());
            druidDataSource.setMaxActive(datasourceProperties21.getMaxActive());
            druidDataSource.setTimeBetweenEvictionRunsMillis(datasourceProperties21.getTimeBetweenEvictionRunsMillis());
            druidDataSource.setMinEvictableIdleTimeMillis(datasourceProperties21.getMinEvictableIdleTimeMillis());
            druidDataSource.setValidationQuery(datasourceProperties21.getValidationQuery());
            druidDataSource.setTestWhileIdle(datasourceProperties21.getTestWhileIdle());
            druidDataSource.setTestOnBorrow(datasourceProperties21.getTestOnBorrow());
            druidDataSource.setTestOnReturn(datasourceProperties21.getTestOnReturn());
//            druidDataSource.setPoolPreparedStatements(datasourceProperties.getPoolPreparedStatements());
//            druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(datasourceProperties.getMaxPoolPreparedStatementPerConnectionSize());
            druidDataSource.setFilters(datasourceProperties21.getFilters());
            druidDataSource.setConnectionProperties(datasourceProperties21.getConnectionProperties());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return druidDataSource;
    }

    @Bean(name = "database114")
    @Qualifier("database114")
    public DataSource dataSource114() {
        DruidDataSource druidDataSource = new DruidDataSource();
        try {
            druidDataSource.setDriverClassName(datasourceProperties114.getDriverClassName());
            druidDataSource.setUrl(datasourceProperties114.getUrl());
            druidDataSource.setUsername(datasourceProperties114.getUsername());
            druidDataSource.setPassword(datasourceProperties114.getPassword());
            druidDataSource.setInitialSize(datasourceProperties114.getInitialSize());
            druidDataSource.setMinIdle(datasourceProperties114.getMinIdle());
            druidDataSource.setMaxActive(datasourceProperties114.getMaxActive());
            druidDataSource.setTimeBetweenEvictionRunsMillis(datasourceProperties114.getTimeBetweenEvictionRunsMillis());
            druidDataSource.setMinEvictableIdleTimeMillis(datasourceProperties114.getMinEvictableIdleTimeMillis());
            druidDataSource.setValidationQuery(datasourceProperties114.getValidationQuery());
            druidDataSource.setTestWhileIdle(datasourceProperties114.getTestWhileIdle());
            druidDataSource.setTestOnBorrow(datasourceProperties114.getTestOnBorrow());
            druidDataSource.setTestOnReturn(datasourceProperties114.getTestOnReturn());
//            druidDataSource.setPoolPreparedStatements(datasourceProperties.getPoolPreparedStatements());
//            druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(datasourceProperties.getMaxPoolPreparedStatementPerConnectionSize());
            druidDataSource.setFilters(datasourceProperties114.getFilters());
            druidDataSource.setConnectionProperties(datasourceProperties114.getConnectionProperties());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return druidDataSource;
    }


    /**
     * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
     * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
     */
    @Bean
    @Primary
    public DynamicDataSource dataSource(@Qualifier("database21") DataSource database21,
                                        @Qualifier("database114") DataSource database114) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DatabaseType.database21, database21);
        targetDataSources.put(DatabaseType.database114, database114);

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
        dataSource.setDefaultTargetDataSource(database21);// 默认的datasource设置为myTestDbDataSource

        return dataSource;
    }

    /**
     * 根据数据源创建SqlSessionFactory
     */
/*
    @Bean
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource ds) throws Exception {
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(ds);// 指定数据源(这个必须有，否则报错)
        // 下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
       // fb.setTypeAliasesPackage(env.getProperty("mybatis.typeAliasesPackage"));// 指定基包
        fb.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapper-locations")));//

        return fb.getObject();
    }
*/


    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("database21") DataSource myTestDbDataSource,
                                               @Qualifier("database114") DataSource myTestDb2DataSource) throws Exception{
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(this.dataSource(myTestDbDataSource, myTestDb2DataSource));
        //fb.setTypeAliasesPackage(env.getProperty("mybatis.typeAliasesPackage"));
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapper-locations")));
        return fb.getObject();
    }

    /**
     * 配置事务管理器
     */
  /*  @Bean
    public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }*/
}
