package com.ggck.vware.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteUserRepository extends JpaRepository<SiteUser, Long> {

  static boolean existsByNickname(String nickname) {
    return false;
  }

  static boolean existsByEmail(String email) {
    return false;
  }

  static boolean existsByUsername(String username) {
    return false;
  }

  Optional<SiteUser> findByUserId(String userId);

}
