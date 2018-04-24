package cn.marioquer.dockerplatform.dao;

import cn.marioquer.dockerplatform.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserDao extends JpaRepository<UserEntity, Integer> {
}
