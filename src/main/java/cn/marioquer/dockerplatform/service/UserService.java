package cn.marioquer.dockerplatform.service;

import cn.marioquer.dockerplatform.entity.UserEntity;
import org.springframework.stereotype.Service;

public interface UserService {
    public UserEntity queryUserById(int id);
}
