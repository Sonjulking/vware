package com.ggck.vware.user.dto;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder //빌더 패턴을 위한 어노테이션
//@Data
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//어노테이션은 파라미터가 없는 디폴트 생성자를 자동으로 생성한다. 이 어노테이션을 사용하면, 클래스에 명시적으로 선언된 생성자가 없더라도 인스턴스를 생성할 수 있다.
//(access = AccessLevel.PROTECTED) : 기본 생성자(NoArgsConstructor)의 접근 제어를 PROCTECTED 로 설정하면 아무런 값도 갖지 않는 의미 없는 객체의 생성 무분별하게 생성하는 것을 막을 수 있다.
public class SiteUserDto {

  private Long userNumber; //유저 넘버

  private String userId; //유저 아이디
  private String userNickName; //유저 닉네임
  
  private String password; //유저 비밀번호

  private String userEmail; //유저 이메일

  private int point; //포인트

  private String preferredPosition; //선호 포지션

  private String testResult; //검사 결과

  private LocalDateTime signUpTime; //가입 시간
  private LocalDateTime lastAccessTime; //마지막 접속 시간

  //@Builder
  /*public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private TestUserDto testUserDto;

    public Builder() {
      this.testUserDto = new TestUserDto();
    }

    public Builder id(Long id) {
      this.testUserDto.setId(id);
      return this;
    }

    public Builder userId(String userId) {
      this.testUserDto.setUserId(userId);
      return this;
    }

    public Builder email(String email) {
      this.testUserDto.setEmail(email);
      return this;
    }

    public Builder password(String password) {
      this.testUserDto.setPassword(password);
      return this;
    }

    public TestUserDto build() {
      return this.testUserDto;
    }
  }*/

}
