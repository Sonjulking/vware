package com.ggck.vware.eggset.repository;

import com.ggck.vware.eggset.entity.KeyBoardEntity;
import com.ggck.vware.eggset.entity.MouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MouseRepository extends JpaRepository<MouseEntity, Long> {
}
