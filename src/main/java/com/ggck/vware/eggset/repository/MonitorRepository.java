package com.ggck.vware.eggset.repository;

import com.ggck.vware.eggset.entity.KeyBoardEntity;
import com.ggck.vware.eggset.entity.MonitorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitorRepository extends JpaRepository<MonitorEntity, Long> {
}
