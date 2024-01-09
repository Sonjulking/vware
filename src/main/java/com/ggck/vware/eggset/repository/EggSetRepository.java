package com.ggck.vware.eggset.repository;

import com.ggck.vware.eggset.entity.EggSetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository는 기본적으로 Entity 클래스만 받아준다.
public interface EggSetRepository extends JpaRepository<EggSetEntity, Long> {
}
