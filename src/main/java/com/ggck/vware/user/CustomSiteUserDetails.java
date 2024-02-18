package com.ggck.vware.user;

import com.ggck.vware.user.entity.SiteUserEntity;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomSiteUserDetails extends User {

  private int point;
  private String userNickName;

  public CustomSiteUserDetails(SiteUserEntity siteUser,
      Collection<? extends GrantedAuthority> authorities) { //? 기호는 Java에서 제네릭스(generics)를 사용할 때와 연관이 있습니다. 이 코드에서의 Collection<? extends GrantedAuthority>에서 ?는 와일드카드(wildcard)를 나타냅니다.
    super(siteUser.getUserId(), siteUser.getPassword(), authorities);
    this.point = siteUser.getPoint();
    this.userNickName = siteUser.getUserNickName();
  }

  public int getPoint() {
    return point;
  }

  public String getUserNickName() {
    return userNickName;
  }

}
