package com.config;

import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
@MapperScan(basePackages = "com.mapper**")
public class MybatisConfig {

    private static final Logger logger = LoggerFactory.getLogger(MybatisConfig.class);

    @Autowired
    private Environment env;



    @Bean(name = "database21")
    @Qualifier("database21")
    @ConfigurationProperties(prefix = "spring21.datasource")
    public DataSource dataSource21() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "database114")
    @Qualifier("database114")
    @ConfigurationProperties(prefix = "spring114.datasource")
    public DataSource dataSource114() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "databaseGh")
    @Qualifier("databaseGh")
    @ConfigurationProperties(prefix = "springGh.datasource")
    public DataSource databaseGh() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "databaseAnjx")
    @Qualifier("databaseAnjx")
    @ConfigurationProperties(prefix = "springAnjx.datasource")
    public DataSource databaseAnjx() {
        return DataSourceBuilder.create().build();
    }

    /**
     * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
     * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
     */
    @Bean
    @Primary
    public DynamicDataSource dataSource(@Qualifier("database21") DataSource database21,
                                        @Qualifier("database114") DataSource database114,
                                        @Qualifier("databaseGh") DataSource databaseGh,
                                        @Qualifier("databaseAnjx") DataSource databaseAnjx) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DatabaseType.database21, database21);
        targetDataSources.put(DatabaseType.database114, database114);
        targetDataSources.put(DatabaseType.databaseGh, databaseGh);
        targetDataSources.put(DatabaseType.databaseAnjx, databaseAnjx);
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
        dataSource.setDefaultTargetDataSource(database21);// 默认的datasource设置为myTestDbDataSource

        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("database21") DataSource database21,
                                               @Qualifier("database114") DataSource database114,
                                               @Qualifier("databaseGh") DataSource databaseGh,
                                               @Qualifier("databaseAnjx") DataSource databaseAnjx) throws Exception{


        VFS.addImplClass(SpringBootVFS.class);
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(this.dataSource(database21, database114,databaseGh,databaseAnjx));
        fb.setTypeAliasesPackage(env.getProperty("mybatis.typeAliasesPackage"));
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
