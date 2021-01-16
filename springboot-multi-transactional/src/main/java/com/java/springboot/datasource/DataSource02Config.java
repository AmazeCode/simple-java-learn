package com.java.springboot.datasource;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.java.springboot.config.DBConfig1;
import com.java.springboot.config.DBConfig2;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @Description: 读取DataSource02数据源
 * @Author: zhangyadong
 * @Date: 2021/1/16 0016 上午 11:33
 * @Version: v1.0
 */
@Configuration  //注册到spring容器中
@MapperScan(basePackages = {"com.java.springboot.test02"}, sqlSessionTemplateRef = "test02SqlSessionTemplate")
public class DataSource02Config {

    /**
     * @description: 配置数据源(test02DataSource)
     * @params: [dbConfig1]
     * @return: javax.sql.DataSource
     * @author: zhangyadong
     * @date: 2021/1/16 0016 下午 7:19
     */
    @Bean("test02DataSource")
    public DataSource test02DataSource(DBConfig2 dbConfig2) throws SQLException {

        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setUrl(dbConfig2.getUrl());
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXADataSource.setPassword(dbConfig2.getPassword());
        mysqlXADataSource.setUser(dbConfig2.getUsername());
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);

        // 将本地事务注册到 Atomikos 全局事务管理器 (底层原理使用的是2PC)
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXADataSource);
        xaDataSource.setUniqueResourceName("test02DataSource");

        xaDataSource.setMinPoolSize(dbConfig2.getMinPoolSize());
        xaDataSource.setMaxPoolSize(dbConfig2.getMaxPoolSize());
        xaDataSource.setMaxLifetime(dbConfig2.getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(dbConfig2.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(dbConfig2.getLoginTimeout());
        xaDataSource.setTestQuery(dbConfig2.getTestQuery());
        return xaDataSource;
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
    /*@Bean(name = "test02TransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("test02DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }*/

    @Bean("test02SqlSessionTemplate")
    public SqlSessionTemplate test02SqlSessionTemplate(@Qualifier("test02SqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
