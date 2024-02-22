package com.ggck.vware.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
//@Builder
//@NoArgsConstructor // 파라미터가 없는 기본 생성자를 생성해줌.
//@AllArgsConstructor
/*
*   public SiteUser(Long userNumber, String userId, String userEmail, String userNickName, String password,
  int point, LocalDateTime signUpTime, LocalDateTime lastAccessTime) {
    this.userNumber = userNumber;
    this.userId = userId;
    this.userEmail = userEmail;
    this.userNickName = userNickName;
    this.password = password;
    this.point = point;
    this.signUpTime = signUpTime;
    this.lastAccessTime = lastAccessTime;
  }*/
public class SiteUserEntity {

  @Id
  @SequenceGenerator(name = "id_sequence_generator", sequenceName = "id_sequence", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sequence_generator")
  @Column(unique = true)
  private Long userNumber; //유저 넘버

  @Column(unique = true)
  @NotNull
  private String userId; //유저 아이디

  @Column(unique = true)
  @NotNull
  private String userEmail; //유저 이메일

  @Column(unique = true)
  @NotNull
  private String userNickName; //유저 닉네임

  @NotNull
  private String password; //비밀번호

  //@Column(nullable = false)
  @NotNull
  private int point = 100; //포인트
  
  private String paymentStatus; //포인트 지급여부

  @NotNull
  private String preferredPosition; //선호 포지션

  private String testResult; //검사 결과

  private String withdrawalStatus; //탈퇴 여부

  private LocalDateTime signUpTime; //가입 시간
  private LocalDateTime lastAccessTime; //마지막 접속 시간
  private LocalDateTime withdrawalTime; //탈퇴시간

}

