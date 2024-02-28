package com.ggck.vware.eggset.repository;

import com.ggck.vware.eggset.entity.GpuEntity;
import com.ggck.vware.eggset.entity.MousepadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MousepadRepository extends JpaRepository<MousepadEntity, Long> {
}
