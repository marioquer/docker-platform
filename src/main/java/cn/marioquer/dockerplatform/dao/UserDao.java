package cn.marioquer.dockerplatform.dao;

import cn.marioquer.dockerplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserDao extends JpaRepository<User, Integer> {
}
