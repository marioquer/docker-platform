package cn.marioquer.dockerplatform.dao;

import cn.marioquer.dockerplatform.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


public interface UserDao extends JpaRepository<UserEntity, Integer> {
    public UserEntity findByUsername(String username);
    public boolean existsByUsername(String username);
}
