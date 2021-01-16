package com.java.springboot.datasource.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Description: 读取DataSource02数据源
 * @Author: zhangyadong
 * @Date: 2021/1/16 0016 上午 11:33
 * @Version: v1.0
 */
@Configuration  //注册到spring容器中
@MapperScan(basePackages = {"com.java.springboot.datasource.test02"}, sqlSessionFactoryRef = "test02SqlSessionFactory")
public class DataSource02Config {

    /**
     * @description: 配置数据库
     * @params: []
     * @return: javax.sql.DataSource
     * @author: zhangyadong
     * @date: 2021/1/16 0016 下午 4:24
     */
    @Bean("test02DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.test02") //读取application.yml中的配置参数映射成为一个对象
    public DataSource getDb1DataSource(){
        return DataSourceBuilder.create().build();
    }

    /**
     * @description: test02回话工厂
     * @params: [dataSource]
     * @return: org.apache.ibatis.session.SqlSessionFactory
     * @author: zhangyadong
     * @date: 2021/1/16 0016 下午 4:24
     */
    @Bean("test02SqlSessionFactory")
    public SqlSessionFactory test02SqlSessionFactory(@Qualifier("test02DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        // 加载mapping文件时需要放开
        // mapper的xml形式文件位置必须要配置，不然将报错：no statement （这种错误也可能是mapper的xml中，namespace与项目的路径不一致导致）
        //bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/test02/*.xml"));
        return bean.getObject();
    }

    /**
     * @description: test02事务管理
     * @params: [dataSource]
     * @return: org.springframework.jdbc.datasource.DataSourceTransactionManager
     * @author: zhangyadong
     * @date: 2021/1/16 0016 下午 4:30
     */
    @Bean(name = "test02TransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("test02DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean("test02SqlSessionTemplate")
    public SqlSessionTemplate test02SqlSessionTemplate(@Qualifier("test02SqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
