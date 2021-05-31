package com.ac.commonmistakes.connectionpool.datasource;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Description: 用户类
 * @Author: zhangyadong
 * @Date: 2021/5/31 11:37
 * @Version: v1.0
 */
@Entity
@Data
public class User {

    // 标记Id自动递增
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
}
