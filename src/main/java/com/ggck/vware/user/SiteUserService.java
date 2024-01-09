package com.ggck.vware.user;

import com.ggck.vware.DataNotFoundException;
import jakarta.persistence.Column;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor // fianl 키워드가 지정되거나 @NonNull 어노테이션이 붙은 필드르 모두 포함하는 생성자를 자동으로 생성해줌
@Service
//해당클래스가 SeviceLayer, 즉 비지니스 로직을 처리하는 클래스임을 나타냄. 이 어노테이션을 사용하면 Spring이 해당 클래스를 자동으로 Bean으로 등록하고, 필요한 곳에서 DI를 통해 사용할 수 있게 해줌.
public class SiteUserService {

  private final SiteUserRepository siteUserRepository;
  private final PasswordEncoder passwordEncoder;

  public SiteUserDto create(SiteUserDto siteUserDto) {
    //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); //비밀번호를 암호화 해주는 보안작업
    SiteUser siteUser = new SiteUser();
    siteUser.setUserId(siteUserDto.getUserId());
    siteUser.setUserEmail(siteUserDto.getUserEmail());
    //siteUser.setPassword(siteUserDto.getPassword());
    siteUser.setPassword(passwordEncoder.encode(siteUserDto.getPassword()));
    siteUser.setUserNickName(siteUserDto.getUserNickName());
    //siteUser.setPoint(100);
    siteUser.setSignUpTime(LocalDateTime.now());
    siteUser.setPreferredPosition(siteUserDto.getPreferredPosition());
    //siteUser.setLastAccessTime(LocalDateTime.now());
    this.siteUserRepository.save(siteUser);
    return siteUserDto;
  }

  public SiteUser getUser(String userId) {
    Optional<SiteUser> siteUser = this.siteUserRepository.findByUserId(userId);
    if (siteUser.isPresent()) {
      return siteUser.get();
    } else {
      throw new DataNotFoundException("siteuser not found");
    }
  }

  public void savePositionResult(SiteUser siteUser, SiteUserDto siteUserDto,
      String positionResult) {
    siteUser.setUserId(siteUserDto.getUserId());
    siteUser.setUserEmail(siteUserDto.getUserEmail());
    siteUser.setPassword(siteUserDto.getPassword());
    siteUser.setUserNickName(siteUserDto.getUserNickName());
    siteUser.setPreferredPosition(siteUserDto.getPreferredPosition());
    siteUser.setTestResult(positionResult);
    this.siteUserRepository.save(siteUser);
  }

 /* public SiteUser updateUserInfo(SiteUserDto siteUserDto){

  }*/
}
