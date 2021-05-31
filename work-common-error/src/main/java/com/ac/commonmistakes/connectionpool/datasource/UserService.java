package com.ac.commonmistakes.connectionpool.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.TimeUnit;

/**
 * @Description: Service
 * @Author: zhangyadong
 * @Date: 2021/5/31 11:41
 * @Version: v1.0
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 事物处理
    @Transactional
    public User register() {
        User user = new User();
        user.setName("new-user-" + System.currentTimeMillis());
        userRepository.save(user);
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }

}
