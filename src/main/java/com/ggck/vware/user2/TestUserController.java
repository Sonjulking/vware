package com.ggck.vware.user2;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/testUser")
public class TestUserController {

  private final TestUserService testUserService;

  @GetMapping("/signup")
  public String signup(TestUserCreateForm testUserCreateForm) {
    return "test/test_signup_form";
  }

  @PostMapping("/signup")
  public String signup(@Valid TestUserCreateForm testUserCreateForm, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "test/test_signup_form";
    }

    if (!testUserCreateForm.getPassword1().equals(testUserCreateForm.getPassword2())) {
      bindingResult.rejectValue("password2", "passwordInCorrect",
          "2개의 패스워드가 일치하지 않습니다.");
      return "test/test_signup_form";
    }

    TestUserDto testUserDto = new TestUserDto();
    testUserDto.setUserId(testUserCreateForm.getUserId());
    testUserDto.setEmail(testUserCreateForm.getEmail());
    testUserDto.setPassword(testUserCreateForm.getPassword1());

/*    TestUserDto testUserDto = TestUserDto.builder()
        .userId(testUserCreateForm.getUserId())
        .email(testUserCreateForm.getEmail())
        .password(testUserCreateForm.getPassword1())
        .build();*/

    testUserService.create(testUserDto);

    return "redirect:/";
  }

}
