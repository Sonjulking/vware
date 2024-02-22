package com.ggck.vware.user.repository;

import com.ggck.vware.user.entity.SiteUserEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteUserRepository extends JpaRepository<SiteUserEntity, Long> {

  Optional<SiteUserEntity> findByUserId(String userId);

  Optional<SiteUserEntity> findByUserEmail(String userEmail);

  List<SiteUserEntity> findByWithdrawalTimeBefore(LocalDateTime date);

  List<SiteUserEntity> findByPaymentStatus(String status);
}
