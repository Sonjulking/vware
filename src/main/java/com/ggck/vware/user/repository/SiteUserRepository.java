package com.ggck.vware.user.repository;

import com.ggck.vware.user.entity.SiteUserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteUserRepository extends JpaRepository<SiteUserEntity, Long> {

  Optional<SiteUserEntity> findByUserId(String userId);

  Optional<SiteUserEntity> findByUserEmail(String userEmail);

}
