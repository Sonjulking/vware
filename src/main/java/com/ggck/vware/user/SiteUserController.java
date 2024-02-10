package com.ggck.vware.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.lang.ref.ReferenceQueue;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor // fianl이나 @NonNull인 필드 값만 파라미터로 받는 생성자를 만듬.
@Controller
@RequestMapping("/user")
public class SiteUserController {

  private final SiteUserService siteUserService;

  private final PasswordEncoder passwordEncoder;

  @GetMapping("/signup")
 /* public String signup(SiteUserCreateForm siteUserCreateForm) {
    return "siteUser/signup_form";
  }*/
  public String signup(Model model) {
    SiteUserCreateForm siteUserCreateForm = new SiteUserCreateForm();
    //SiteUserCreateForm siteUserCreateForm = SiteUserCreateForm.builder().build();;
    model.addAttribute("siteUserCreateForm", siteUserCreateForm);
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


    }  /*catch (DuplicateEmailException e) {
      bindingResult.reject("duplicateEmail", "이미 사용 중인 이메일입니다.");
      return "siteUser/signup_form";

    } catch (DuplicateNicknameException e) {
      bindingResult.reject("duplicateNickName", "이미 사용 중인 닉네임입니다.");
      return "siteUser/signup_form";

    } catch (DuplicateUsernameException e) {
      bindingResult.reject("duplicateUsername", "이미 사용 중인 아이디입니다.");
      return "siteUser/signup_form";

    } catch (Exception e) {
      e.printStackTrace();
      bindingResult.reject("signupFailed", e.getMessage());
      return "siteUser/signup_form";
    }*/ catch (DataIntegrityViolationException e) { // id나 이메일이 중복일 경우 발생하는 예외
      String errorMessage = e.getMessage();

      System.out.println("e.getMessage() 출력 : " + errorMessage);
      if (errorMessage != null && errorMessage.contains(
          "site_user.UK_7txm0o5jvngqyt8iwgnr1ohwu")) { //아이디 중복일때
        System.out.println("아이디 중복");
        bindingResult.rejectValue("userId", "signupFailed", "이미 등록된 아이디 입니다.");
      } else if (errorMessage != null && errorMessage.contains( //이메일 중복일때
          "site_user.UK_cff3da7kxabw064f3qqd3t2kj")) {
        System.out.println("이메일 중복");
        bindingResult.rejectValue("userEmail", "signupFailed", "이미 등록된 이메일 입니다.");
      } else if (errorMessage != null && errorMessage.contains( //닉네임 중복일때
          "site_user.UK_eydkr1n0qdr8lsiduiqh3pcjx")) {
        System.out.println("닉네임 중복");
        bindingResult.rejectValue("userNickName", "signupFailed", "이미 등록된 닉네임 입니다.");
      }

      return "siteUser/signup_form";

    } catch (Exception e) {
      e.printStackTrace();
      bindingResult.reject("signupFailed", "회원가입에 실패했습니다.");
      return "siteUser/signup_form";
    }

    return "redirect:/"; // 홈화면으로 이동
  }

/*
  @ExceptionHandler(DataIntegrityViolationException.class)
  public void handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
    // DataIntegrityViolationException 처리 로직
    System.out.println("콘솔아 떠라~ " + ex.getMessage());
  }
*/

  @GetMapping("/login")
  public String login() {
    return "siteUser/login_form";
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/MyPage")
  public String myPage(Principal principal, ModelMap modelMap) {
    String loginId = principal.getName();
    SiteUser siteUser = this.siteUserService.getUser(principal.getName());
    SiteUserDto myUserDto = SiteUserDto.builder()
        .userId(siteUser.getUserId())
        .userEmail(siteUser.getUserEmail())
        .userNickName(siteUser.getUserNickName())
        .point(siteUser.getPoint())
        .testResult(siteUser.getTestResult())
        .preferredPosition(siteUser.getPreferredPosition())
        .signUpTime(siteUser.getSignUpTime())
        .build();

    modelMap.addAttribute("myUserDTO", myUserDto);

    return "siteUser/my_page";
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/MyPage/prevModify")
  public String getPrevModify(Model model) {

    SiteUserModifyForm siteUserModifyForm = new SiteUserModifyForm(); //
    model.addAttribute("siteUserModifyForm", siteUserModifyForm); //
    return "siteUser/prevModify";

  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/MyPage/prevModify")
  public String postPrevModify(Principal principal, Authentication auth,
      @RequestParam("reconfirmPw") String pw,
      RedirectAttributes rttr, SiteUserModifyForm siteUserModifyForm, Model model,
      ModelMap modelMap,
      HttpServletRequest request) {
    String loginId = principal.getName();
    SiteUser siteUser = this.siteUserService.getUser(principal.getName());
    SiteUserDto myUserDto = SiteUserDto.builder()
        .userId(siteUser.getUserId())
        .userEmail(siteUser.getUserEmail())
        .userNickName(siteUser.getUserNickName())
        .point(siteUser.getPoint())
        .testResult(siteUser.getTestResult())
        .preferredPosition(siteUser.getPreferredPosition())
        .signUpTime(siteUser.getSignUpTime())
        .password(siteUser.getPassword())
        .build();
    String userpw = myUserDto.getPassword();
    if (passwordEncoder.matches(pw, userpw)) {
      modelMap.addAttribute("myUserDTO", myUserDto);
      siteUserModifyForm.setUserId(myUserDto.getUserId());
      siteUserModifyForm.setUserEmail(myUserDto.getUserEmail());
      siteUserModifyForm.setPreferredPosition(myUserDto.getPreferredPosition());
      siteUserModifyForm.setUserNickName(myUserDto.getUserNickName());
      siteUserModifyForm.setUserEmail(myUserDto.getUserEmail());

      boolean reconfirmCheck = true;

      HttpSession session = request.getSession();
      session.setAttribute("reconfirmCheck", reconfirmCheck);

      return "siteUser/my_page_modify";
    } else {
      rttr.addFlashAttribute("msg", "비밀번호를 다시 확인해 주세요.");
      return "redirect:/user/MyPage/prevModify";
    }
  }


/*  @PreAuthorize("isAuthenticated()")
  @GetMapping("/MyPage/modify")
  public String myPageModify(SiteUserCreateForm siteUserCreateForm, Principal principal,
      Model model, ModelMap modelMap) {
    String loginId = principal.getName();
    SiteUser siteUser = this.siteUserService.getUser(principal.getName());
    SiteUserDto myUserDto = SiteUserDto.builder()
        .userId(siteUser.getUserId())
        .userEmail(siteUser.getUserEmail())
        .userNickName(siteUser.getUserNickName())
        .point(siteUser.getPoint())
        .testResult(siteUser.getTestResult())
        .preferredPosition(siteUser.getPreferredPosition())
        .signUpTime(siteUser.getSignUpTime())
        .build();
    modelMap.addAttribute("myUserDTO", myUserDto);
    siteUserCreateForm.setUserId(myUserDto.getUserId());
    siteUserCreateForm.setPassword1(siteUser.getPassword());
    siteUserCreateForm.setPassword2(siteUser.getPassword());
    siteUserCreateForm.setUserEmail(myUserDto.getUserEmail());
    siteUserCreateForm.setPreferredPosition(myUserDto.getPreferredPosition());
    siteUserCreateForm.setUserNickName(myUserDto.getUserNickName());
    siteUserCreateForm.setUserEmail(myUserDto.getUserEmail());
    return "siteUser/my_page_modify";
  }*/


/*  @PreAuthorize("isAuthenticated()")
  @GetMapping("/MyPage/modify")
  public String myPageModify(SiteUserCreateForm siteUserCreateForm, Principal principal, Model model, ModelMap modelMap) {
    String loginId = principal.getName();
    SiteUser siteUser = this.siteUserService.getUser(principal.getName());

    siteUserCreateForm.setUserId(siteUser.getUserId());

    return "siteUser/my_page_modify";
  }*/

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/MyPage/modify")
  public String myPageModify(Principal principal, @Valid SiteUserModifyForm siteUserModifyForm,
      BindingResult bindingResult,
      HttpServletRequest request, Model model, ModelMap modelMap) {

    String loginId = principal.getName(); //로그인된 아이디 값 가져오기
    SiteUser siteUser = this.siteUserService.getUser(principal.getName());

    if (bindingResult.hasErrors()) { //에러가 생기면
      return "siteUser/my_page_modify";
    }
    try {

      SiteUserDto modifyUserDto = SiteUserDto.builder()
          .userId(siteUserModifyForm.getUserId())
          .userEmail(siteUserModifyForm.getUserEmail())
          .userNickName(siteUserModifyForm.getUserNickName())
          //.password(siteUser.getPassword())
          .preferredPosition(siteUserModifyForm.getPreferredPosition())
          .point(siteUser.getPoint())
          .build();

      siteUserService.updateUserInfo(siteUser, modifyUserDto);
      modelMap.addAttribute("myUserDTO", modifyUserDto);
      //model.addAttribute("siteUserCreateForm", SiteUserCreateForm.builder().build());
      return "siteUser/my_page"; //이전페이지로 가버림

    } catch (DataIntegrityViolationException e) { // id나 이메일이 중복일 경우 발생하는 예외
      bindingResult.reject("signupFailed", "이미 등록된 사용자 입니다.");
      return "siteUser/my_page_modify";

    } catch (Exception e) {
      e.printStackTrace();
      bindingResult.reject("signupFailed", e.getMessage());
      return "siteUser/my_page_modify";
    }
    //return "siteUser/my_page_modify";
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/MyPage/modifyPassword")
  public String modifyPassword(Model model) {
    SiteUserModifyPasswordForm siteUserModifyPasswordForm = new SiteUserModifyPasswordForm();
    model.addAttribute("siteUserModifyPasswordForm", siteUserModifyPasswordForm);
    return "siteUser/modify_password_form";
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/MyPage/modifyPassword")
  public String modifyPassword(@Valid SiteUserModifyPasswordForm siteUserModifyPasswordForm,
      Principal principal, BindingResult bindingResult) {
    String loginId = principal.getName(); //로그인된 아이디 값 가져오기
    SiteUser siteUser = this.siteUserService.getUser(principal.getName());
    if (bindingResult.hasErrors()) { //에러가 생기면
      return "siteUser/modify_password_form";
    }
    if (!siteUserModifyPasswordForm.getPassword1()
        .equals(siteUserModifyPasswordForm.getPassword2())) { //패스워드확인이 실패하면
      bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않아요~");
      return "siteUser/modify_password_form";
    }
    SiteUserDto modifyPasswordUserDto = SiteUserDto.builder()
        .password(siteUserModifyPasswordForm.getPassword1())
        .build();
    siteUserService.updateUserPassword(siteUser, modifyPasswordUserDto);
    return "EGG/main";
  }


}
