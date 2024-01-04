package com.ggck.vware.user2;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Builder
public class TestUserDto {

  private Long id;
  private String userId;
  private String email;
  private String password;

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
