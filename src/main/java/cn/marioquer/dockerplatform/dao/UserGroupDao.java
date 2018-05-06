package cn.marioquer.dockerplatform.dao;

import cn.marioquer.dockerplatform.entity.UserGroupEntity;
import cn.marioquer.dockerplatform.entity.UserGroupEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupDao extends JpaRepository<UserGroupEntity, UserGroupEntityPK> {
}
