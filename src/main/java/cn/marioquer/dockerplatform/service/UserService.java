package cn.marioquer.dockerplatform.service;

import cn.marioquer.dockerplatform.vo.UserLoginVO;

public interface UserService {
    public UserLoginVO login(String username, String password);

    public String signUp(String username, String password);
}
