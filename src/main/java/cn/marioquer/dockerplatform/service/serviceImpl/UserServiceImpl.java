package cn.marioquer.dockerplatform.service.serviceImpl;

import cn.marioquer.dockerplatform.dao.UserDao;
import cn.marioquer.dockerplatform.entity.User;
import cn.marioquer.dockerplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User queryUserById(int id) {
        return userDao.getOne(id);
    }
}