package com.java.springboot.datasource;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.atomikos.util.Atomikos;
import com.java.springboot.config.DBConfig1;
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
 * @Description: 读取DataSource01数据源
 * @Author: zhangyadong
 * @Date: 2021/1/16 0016 上午 11:33
 * @Version: v1.0
 */
@Configuration  //注册到spring容器中
@MapperScan(basePackages = {"com.java.springboot.test01"}, sqlSessionTemplateRef = "test01SqlSessionTemplate")
public class DataSource01Config {

    /*
        SpringBoot1.5的时候 没有注释(@Primary)默认指向数据源的时候 会报错
        springboot2.0的时候 不指定默认数据源的时候是不会报错了
     */
    /**
     * @description: 配置数据源(test01DataSource)
     * @params: []
     * @return: javax.sql.DataSource
     * @author: zhangyadong
     * @date: 2021/1/16 0016 下午 4:24
     */
    //@Primary // 表示这个数据源是默认数据源, 这个注解必须要加，因为不加的话spring将分不清楚那个为主数据源（默认数据源）
    @Bean("test01DataSource")
    public DataSource test01DataSource(DBConfig1 dbConfig1) throws SQLException {

        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setUrl(dbConfig1.getUrl());
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXADataSource.setPassword(dbConfig1.getPassword());
        mysqlXADataSource.setUser(dbConfig1.getUsername());
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);

        // 将本地事务注册到 Atomikos 全局事务管理器 (底层原理使用的是2PC)
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXADataSource);
        xaDataSource.setUniqueResourceName("test01DataSource");

        xaDataSource.setMinPoolSize(dbConfig1.getMinPoolSize());
        xaDataSource.setMaxPoolSize(dbConfig1.getMaxPoolSize());
        xaDataSource.setMaxLifetime(dbConfig1.getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(dbConfig1.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(dbConfig1.getLoginTimeout());
        xaDataSource.setTestQuery(dbConfig1.getTestQuery());
        return xaDataSource;
    }

    /**
     * @description: test01回话工厂
     * @params: [dataSource]
     * @return: org.apache.ibatis.session.SqlSessionFactory
     * @author: zhangyadong
     * @date: 2021/1/16 0016 下午 4:24
     */
    //@Primary
    @Bean("test01SqlSessionFactory")
    public SqlSessionFactory test01SqlSessionFactory(@Qualifier("test01DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        // 加载mapping文件时需要放开
        // mapper的xml形式文件位置必须要配置，不然将报错：no statement （这种错误也可能是mapper的xml中，namespace与项目的路径不一致导致）
        //bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/test01/*.xml"));
        return bean.getObject();
    }

    /**
     * @description: test01事务管理
     * @params: [dataSource]
     * @return: org.springframework.jdbc.datasource.DataSourceTransactionManager
     * @author: zhangyadong
     * @date: 2021/1/16 0016 下午 4:30
     */
    //@Primary
    /*@Bean(name = "test01TransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("test01DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }*/

    //@Primary
    @Bean("test01SqlSessionTemplate")
    public SqlSessionTemplate test01SqlSessionTemplate(@Qualifier("test01SqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
