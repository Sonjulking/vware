package com.ggck.vware.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.sql.Time;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SiteUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userNumber;

  @Column(unique = true)
  private String userId;
  @Column(unique = true)
  private String userNickName;

  private String password;

  @Column(unique = true)
  private String email;

  private LocalDateTime signUpTime;
  private LocalDateTime lastAccessTime;
}
