package com.ggck.vware.eggset.repository;

import com.ggck.vware.eggset.entity.KeyBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EggSetAdminRepository extends JpaRepository<KeyBoardEntity, Long> {
}
