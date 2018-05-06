package cn.marioquer.dockerplatform.dao;

import cn.marioquer.dockerplatform.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupDao extends JpaRepository<GroupEntity, Integer> {
}
