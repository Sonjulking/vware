package com.ggck.vware.user.controller;

import com.ggck.vware.user.dto.SiteUserDto;
import com.ggck.vware.user.entity.SiteUserEntity;
import com.ggck.vware.user.form.SiteUserCreateForm;
import com.ggck.vware.user.form.SiteUserModifyForm;
import com.ggck.vware.user.form.SiteUserModifyPasswordForm;
import com.ggck.vware.user.repository.SiteUserRepository;
import com.ggck.vware.user.service.SiteUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor // fianl이나 @NonNull인 필드 값만 파라미터로 받는 생성자를 만듬.
@Controller
@EnableScheduling
@RequestMapping("/user")
public class SiteUserController {

  private final SiteUserService siteUserService;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  private SiteUserRepository siteUserRepository;
  /*
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
      // DataIntegrityViolationException 처리 로직
      System.out.println("콘솔아 떠라~ " + ex.getMessage());
    }
  */
  @Autowired
  private JavaMailSender javaMailSender;

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
      bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
      return "siteUser/signup_form";

    }
    try {
      com.ggck.vware.user.dto.SiteUserDto newUserDto = com.ggck.vware.user.dto.SiteUserDto.builder()
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
          "site_user_entity.UK_h47ghyni8djesbuhqcbflv8il")) { //아이디 중복일때 site_user.UK_7txm0o5jvngqyt8iwgnr1ohwu (윈도우로 실행시)
        System.out.println("아이디 중복");
        bindingResult.rejectValue("userId", "signupFailed", "이미 등록된 아이디 입니다.");
      } else if (errorMessage != null && errorMessage.contains(
          "site_user_entity.UK_isq45epchnlf1p8ytx6abskom")) {    //이메일 중복일때 site_user.UK_cff3da7kxabw064f3qqd3t2kj
        System.out.println("이메일 중복");
        bindingResult.rejectValue("userEmail", "signupFailed", "이미 등록된 이메일 입니다.");
      } else if (errorMessage != null && errorMessage.contains(
          "site_user_entity.UK_7li9yovuhod5ssrc486yd7ie6")) {  //닉네임 중복일때 site_user.UK_eydkr1n0qdr8lsiduiqh3pcjx
        System.out.println("닉네임 중복");
        bindingResult.rejectValue("userNickName", "signupFailed", "이미 등록된 닉네임 입니다.");
      } else {

        bindingResult.reject("signupFailed", "이값이 뜨면 망한거시야요");

      }

      return "siteUser/signup_form";

    } catch (Exception e) {
      e.printStackTrace();
      bindingResult.reject("signupFailed", "회원가입에 실패했습니다.");
      return "siteUser/signup_form";
    }

    return "redirect:/"; // 홈화면으로 이동
  }

  @GetMapping("/login")
  public String login() {
    return "siteUser/login_form";
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/MyPage")
  public String myPage(Principal principal, ModelMap modelMap) {
    String loginId = principal.getName();
    SiteUserEntity siteUser = this.siteUserService.getUser(principal.getName());
    com.ggck.vware.user.dto.SiteUserDto myUserDto = com.ggck.vware.user.dto.SiteUserDto.builder()
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
    SiteUserEntity siteUser = this.siteUserService.getUser(principal.getName());
    com.ggck.vware.user.dto.SiteUserDto myUserDto = com.ggck.vware.user.dto.SiteUserDto.builder()
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

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/MyPage/modify")
  public String myPageModify(@Valid SiteUserModifyForm siteUserModifyForm,
      BindingResult bindingResult,
      HttpServletRequest request, Model model, ModelMap modelMap, Principal principal) {

    String loginId = principal.getName(); //로그인된 아이디 값 가져오기
    SiteUserEntity siteUser = this.siteUserService.getUser(principal.getName());

    if (bindingResult.hasErrors()) { //에러가 생기면
      System.out.println("hasErrors"); //
      return "siteUser/my_page_modify";
    }
    try {

      com.ggck.vware.user.dto.SiteUserDto modifyUserDto = com.ggck.vware.user.dto.SiteUserDto.builder()
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
      System.out.println("중복에러"); //
      bindingResult.reject("signupFailed", "이미 등록된 닉네임 입니다.");
      return "siteUser/my_page_modify";

    } catch (Exception e) {
      System.out.println("전체에러"); //
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
      BindingResult bindingResult,
      Principal principal, HttpServletResponse response) {
    String loginId = principal.getName(); //로그인된 아이디 값 가져오기
    SiteUserEntity siteUser = this.siteUserService.getUser(principal.getName());
    if (bindingResult.hasErrors()) { //에러가 생기면
      //bindingResult.reject("comeon", "비밀번호 입력하세용");
      return "siteUser/modify_password_form";
    }
    if (!siteUserModifyPasswordForm.getPassword1()
        .equals(siteUserModifyPasswordForm.getPassword2())) { //패스워드확인이 실패하면
      bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
      return "siteUser/modify_password_form";
    }

    com.ggck.vware.user.dto.SiteUserDto modifyPasswordUserDto = com.ggck.vware.user.dto.SiteUserDto.builder()
        .password(siteUserModifyPasswordForm.getPassword1())
        .build();
    siteUserService.updateUserPassword(siteUser, modifyPasswordUserDto);

    // 비밀변경 시 JavaScript 코드를 실행하여 alert 창을 띄움
    String script =
        "<script>alert('비밀번호가 변경되었습니다.'); window.location='/';</script>"; //메인으로 넘어감
    try {
      response.setContentType("text/html;charset=UTF-8");
      response.getWriter().write(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return "EGG/main";

  }

  @GetMapping("/findIdPwd")
  public String findIdPwd() {
    return "siteUser/find_id_pw_form";
  }


  @PostMapping("/findIdPwd")
  public String findIdPwd(@RequestParam("email") String userEmail, HttpServletResponse response,
      Model model)
      throws Exception {
    /*    if (siteUser == null) {

      throw new Exception("사용자를 찾을 수 없습니다.");

    }*/

    try {

      SiteUserEntity siteUser = this.siteUserService.emailGetUser(userEmail);

      String myId = siteUser.getUserId();

      int codeLength = 6; //6자리

      Random random = new Random();

      StringBuilder randomPwd = new StringBuilder();

      for (int i = 0; i < codeLength; i++) {
        int digit = random.nextInt(10); // 0부터 9까지의 난수
        randomPwd.append(digit);
      }

      String temporaryPwd = randomPwd.toString();
      SiteUserDto finderDto = com.ggck.vware.user.dto.SiteUserDto.builder()
          .password(temporaryPwd)
          .build();
      siteUserService.findUser(siteUser, finderDto);

      SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

      simpleMailMessage.setTo(userEmail);
      simpleMailMessage.setSubject("EGG.GG 아이디와 임시 비밀번호 입니다.");
      simpleMailMessage.setText(
          "아이디 : " + myId + "\n임시 비밀번호 :  " + temporaryPwd + "\n아이디와 임시비밀번호로 로그인 부탁드립니다.");

      javaMailSender.send(simpleMailMessage);

      System.out.println("임시비밀번호 : " + temporaryPwd);
      return "siteUser/login_form";


    } catch (Exception e) {
      model.addAttribute("error", true);

      return "siteUser/find_id_pw_form";
    }

  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/MyPage/withdrawal")
  public String withdrawal(HttpServletRequest request, Principal principal,
      HttpServletResponse response) {
    String loginId = principal.getName(); //로그인된 아이디 값 가져오기
    Authentication auth = SecurityContextHolder.getContext()
        .getAuthentication(); //로그인되어있는 계정정보 가져오기
    SiteUserEntity siteUser = this.siteUserService.getUser(principal.getName());
    siteUserService.withdrawalUser(siteUser);

    String script =
        "<script>alert('회원탈퇴가 정상적으로 처리되었습니다. 그동안 이용해주셔서 감사합니다.'); window.location='/';</script>"; //메인으로 넘어감

    try {
      response.setContentType("text/html;charset=UTF-8");
      response.getWriter().write(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    new SecurityContextLogoutHandler().logout(request, response, auth); //로그아웃 처리
    return "EGG/main";

  }

  @Scheduled(cron = "0 0 0 * * ?") //매일 자정에 실행
  public void cleanWithdrawalData() {
    LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
    List<SiteUserEntity> withdrawalUsers = siteUserRepository.findByWithdrawalTimeBefore(
        thirtyDaysAgo); //탈퇴한지 30일이 지난 유저들
    siteUserRepository.deleteAll(withdrawalUsers);
    System.out.println("자동삭제됐다옹" + withdrawalUsers);
  }

  @Scheduled(cron = "0 0 0 * * ?") //매일 자정에 실행
  //@Scheduled(cron = "*/5 * * * * *") //5초마다
  public void cleanPointStatus() {
    List<SiteUserEntity> paidPointUsers = siteUserRepository.findByPaymentStatus("1");

    for (SiteUserEntity user : paidPointUsers) {
      user.setPaymentStatus("0");
    }
    siteUserRepository.saveAll(paidPointUsers);

  }
}