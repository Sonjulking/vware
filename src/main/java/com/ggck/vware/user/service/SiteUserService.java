package com.ggck.vware.user.service;

import com.ggck.vware.DataNotFoundException;
import com.ggck.vware.user.dto.SiteUserDto;
import com.ggck.vware.user.repository.SiteUserRepository;
import com.ggck.vware.user.entity.SiteUserEntity;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor // fianl 키워드가 지정되거나 @NonNull 어노테이션이 붙은 필드르 모두 포함하는 생성자를 자동으로 생성해줌
@Service
//해당클래스가 SeviceLayer, 즉 비지니스 로직을 처리하는 클래스임을 나타냄. 이 어노테이션을 사용하면 Spring이 해당 클래스를 자동으로 Bean으로 등록하고, 필요한 곳에서 DI를 통해 사용할 수 있게 해줌.
public class SiteUserService {

  private final SiteUserRepository siteUserRepository;
  private final PasswordEncoder passwordEncoder;

  public com.ggck.vware.user.dto.SiteUserDto create(
      com.ggck.vware.user.dto.SiteUserDto siteUserDto) {
    String email = siteUserDto.getUserEmail();
    String username = siteUserDto.getUserId();
    String nickname = siteUserDto.getUserNickName();

  /*  if (SiteUserRepository.existsByEmail(email)) {
      throw new DuplicateEmailException("이미 등록된 이메일입니다.");
    }
    if (SiteUserRepository.existsByUsername(username)) {
      throw new DuplicateUsernameException("이미 사용 중인 아이디입니다.");
    }
    if (SiteUserRepository.existsByNickname(nickname)) {
      throw new DuplicateNicknameException("이미 사용 중인 닉네임입니다.");
    }
*/
    //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); //비밀번호를 암호화 해주는 보안작업
    SiteUserEntity siteUser = new SiteUserEntity();
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

  public SiteUserEntity getUser(String userId) {
    Optional<SiteUserEntity> siteUser = this.siteUserRepository.findByUserId(userId);
    if (siteUser.isPresent()) {
      return siteUser.get();
    } else {
      throw new DataNotFoundException("siteuser not found");
    }
  }

  public void savePositionResult(
      SiteUserEntity siteUser, com.ggck.vware.user.dto.SiteUserDto siteUserDto,
      String positionResult) {
    siteUser.setUserId(siteUserDto.getUserId());
    siteUser.setUserEmail(siteUserDto.getUserEmail());
    siteUser.setPassword(siteUserDto.getPassword());
    siteUser.setUserNickName(siteUserDto.getUserNickName());
    siteUser.setPreferredPosition(siteUserDto.getPreferredPosition());
    siteUser.setTestResult(positionResult);
    this.siteUserRepository.save(siteUser);
  }

  public void updateUserInfo(SiteUserEntity siteUser,
      com.ggck.vware.user.dto.SiteUserDto siteUserDto) {
    siteUser.setUserId(siteUserDto.getUserId());
    siteUser.setUserEmail(siteUserDto.getUserEmail());
    //siteUser.setPassword(siteUserDto.getPassword());
    //siteUser.setPassword(passwordEncoder.encode(siteUserDto.getPassword()));
    siteUser.setUserNickName(siteUserDto.getUserNickName());
    //siteUser.setPoint(100);
    siteUser.setSignUpTime(LocalDateTime.now());
    siteUser.setPreferredPosition(siteUserDto.getPreferredPosition());
    //siteUser.setLastAccessTime(LocalDateTime.now());
    this.siteUserRepository.save(siteUser);
  }

  public void updateUserPassword(
      SiteUserEntity siteUser, com.ggck.vware.user.dto.SiteUserDto siteUserDto) {
    siteUser.setPassword(passwordEncoder.encode(siteUserDto.getPassword()));
    this.siteUserRepository.save(siteUser);
  }

  public SiteUserEntity emailGetUser(String userEmail) {
    Optional<SiteUserEntity> siteUser = this.siteUserRepository.findByUserEmail(userEmail);
    if (siteUser.isPresent()) {
      return siteUser.get();
    } else {
      throw new DataNotFoundException("siteuser not found");
    }
  }

  public void findUser(SiteUserEntity siteUser, SiteUserDto siteUserDto) {

    siteUser.setPassword(passwordEncoder.encode(siteUserDto.getPassword()));

    this.siteUserRepository.save(siteUser);
  }


}
