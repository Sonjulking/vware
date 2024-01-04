package com.ggck.vware.user;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor // fianl이나 @NonNull인 필드 값만 파라미터로 받는 생성자를 만듬.
@Controller
@RequestMapping("/user")
public class SiteUserController {

  private final SiteUserService siteUserService;

  @GetMapping("/signup")
 /* public String signup(SiteUserCreateForm siteUserCreateForm) {
    return "siteUser/signup_form";
  }*/
  public String signup(Model model) {
    model.addAttribute("siteUserCreateForm", new SiteUserCreateForm());
    return "siteUser/signup_form";
  }

  @PostMapping("/signup")
  public String signup(@Valid SiteUserCreateForm siteUserCreateForm, BindingResult bindingResult,
      HttpServletRequest request, Model model) {
    if (bindingResult.hasErrors()) { //에러가 생기면
      return "siteUser/signup_form";
    }
    if (!siteUserCreateForm.getPassword1()
        .equals(siteUserCreateForm.getPassword2())) { //패스워드확인이 실패하면
      bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않아요~");
      return "siteUser/signup_form";

    }
    try {
      SiteUserDto newUserDto = SiteUserDto.builder()
          .userId(siteUserCreateForm.getUserId())
          .userEmail(siteUserCreateForm.getUserEmail())
          .userNickName(siteUserCreateForm.getUserNickName())
          .password(siteUserCreateForm.getPassword1())
          .preferredPosition(siteUserCreateForm.getPreferredPosition())
          .build();

      HttpSession session = request.getSession();
      String sendNickName = siteUserCreateForm.getUserNickName();
      String testString = "안뇽";

      session.setAttribute("sendNickName", sendNickName);
      session.setMaxInactiveInterval(1);
      model.addAttribute("testString", testString);
      siteUserService.create(newUserDto);


    } catch (DataIntegrityViolationException e) { // id나 이메일이 중복일 경우 발생하는 예외
      bindingResult.reject("signupFailed", "이미 등록된 사용자 입니다.");
      return "siteUser/signup_form";

    } catch (Exception e) {
      e.printStackTrace();
      bindingResult.reject("signupFailed", e.getMessage());
      return "siteUser/signup_form";
    }
    return "redirect:/"; // 홈화면으로 이동
  }

  @GetMapping("/login")
  public String login() {
    return "siteUser/login_form";
  }

}
