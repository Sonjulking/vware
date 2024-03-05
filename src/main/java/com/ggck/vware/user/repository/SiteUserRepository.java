package com.ggck.vware.user.repository;

import com.ggck.vware.user.entity.SiteUserEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteUserRepository extends JpaRepository<SiteUserEntity, Long> {

  Optional<SiteUserEntity> findByUserId(String userId); //아이디를 통해 유저 찾기

  Optional<SiteUserEntity> findByUserEmail(String userEmail); //이메일을 통해 유저 찾기

  List<SiteUserEntity> findByWithdrawalTimeBefore(LocalDateTime date); //탈퇴한지 며칠됐는지

  List<SiteUserEntity> findByPaymentStatus(String status); //포인트 지급했는지?
}
