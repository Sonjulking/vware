package com.ggck.vware.user2;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TestUserService {

  private final TestUserRepository testUserRepository;

  private final PasswordEncoder passwordEncoder;

  public TestUserDto create(TestUserDto testUserDto) {
    TestUser user = new TestUser();
    user.setUserId(testUserDto.getUserId());
    user.setEmail(testUserDto.getEmail());
    user.setPassword(passwordEncoder.encode(testUserDto.getPassword()));
    this.testUserRepository.save(user);
    //testUserDto.setId(user.getId());
    return testUserDto;
  }
}
