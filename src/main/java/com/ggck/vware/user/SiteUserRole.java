package com.ggck.vware.user;

import lombok.Getter;

@Getter
public enum SiteUserRole { //열거 자료형 : 서로 연관된 상수들의 집합을 정의하는 자료형임.

  ADMIN("ROLE_ADMIN"),

  USER("ROLE_USER");

  private String value;

  SiteUserRole(String value) {
    this.value = value;
  }

}
