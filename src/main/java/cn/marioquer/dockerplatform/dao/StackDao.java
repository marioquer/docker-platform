package cn.marioquer.dockerplatform.dao;

import cn.marioquer.dockerplatform.entity.StackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StackDao extends JpaRepository<StackEntity, String> {
}
