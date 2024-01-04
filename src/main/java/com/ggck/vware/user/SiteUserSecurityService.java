package com.ggck.vware.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SiteUserSecurityService implements UserDetailsService {

  private final SiteUserRepository siteUserRepository;

  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    Optional<SiteUser> _siteUser = this.siteUserRepository.findByUserId(userId);
    if (_siteUser.isEmpty()) {
      throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
    }
    SiteUser siteUser = _siteUser.get();
    List<GrantedAuthority> authorities = new ArrayList<>();

    if ("admin".equals(userId)) {
      authorities.add(new SimpleGrantedAuthority(SiteUserRole.ADMIN.getValue()));
    } else {
      authorities.add(new SimpleGrantedAuthority(SiteUserRole.USER.getValue()));
    }
    return new CustomSiteUserDetails(siteUser,
        authorities); //스프링 시큐리티는 loadUserByUsername 메서드에 의해 리턴된 User 객체의 비밀번호가 화면으로부터 입력 받은 비밀번호와 일치하는지를 검사하는 로직을 내부적으로 가지고 있다.
  }
}
