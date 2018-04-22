package cn.marioquer.dockerplatform.service;

import cn.marioquer.dockerplatform.entity.User;
import org.springframework.stereotype.Service;

public interface UserService {
    public User queryUserById(int id);
}
