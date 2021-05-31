package com.ac.commonmistakes.connectionpool.datasource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description: DAO
 * @Author: zhangyadong
 * @Date: 2021/5/31 11:39
 * @Version: v1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
