package cn.marioquer.dockerplatform.dao;

import cn.marioquer.dockerplatform.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceDao extends JpaRepository<ServiceEntity, String> {
}
