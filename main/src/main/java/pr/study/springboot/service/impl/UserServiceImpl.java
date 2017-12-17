package pr.study.springboot.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import pr.study.springboot.bean.User;
import pr.study.springboot.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public User getUserById(long id) {
        User user = new User();
        user.setId(123L);
        user.setName("helloworld");
        user.setEmail("helloworld@g.com");
        user.setCreateTime(new Date());
        return user;
    }

}
