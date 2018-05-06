package cn.marioquer.dockerplatform.dao;

import cn.marioquer.dockerplatform.entity.ImagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesDao extends JpaRepository<ImagesEntity,String> {
}
