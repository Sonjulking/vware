package com.ggck.vware.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor // fianl이나 @NonNull인 필드 값만 파라미터로 받는 생성자를 만듬.
@Controller
@RequestMapping("/user")
public class SiteUserAuthenticationController {

  private static final Logger logger = LoggerFactory.getLogger(
      SiteUserAuthenticationController.class);

  @Autowired
  private JavaMailSender javaMailSender;

  public String randomNumber() {

    int codeLength = 6;
    Random random = new Random();
    StringBuilder codeBuilder = new StringBuilder();
    for (int i = 0; i < codeLength; i++) {
      int digit = random.nextInt(10); // 0부터 9까지의 난수
      codeBuilder.append(digit);
    }

    return codeBuilder.toString();

  }

  @GetMapping("/authentication")
  public String Authentication(HttpServletRequest request) {
    String resultNumber = randomNumber();

    HttpSession session = request.getSession();

    session.setAttribute("resultNumber", resultNumber);
    session.setMaxInactiveInterval(60 * 5); // 5분

    return "siteUser/signup_form";

  }

  @RequestMapping(value = "/mailCheck", method = RequestMethod.GET)
  @ResponseBody
  public String mailCheckGET(@RequestParam("email") String email) throws Exception {

    /* 뷰(View)로부터 넘어온 데이터 확인 */
    //System.out.println(email);
    //logger.info(email);

    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

    int codeLength = 6;

    Random random = new Random();

    StringBuilder codeBuilder = new StringBuilder();

    for (int i = 0; i < codeLength; i++) {
      int digit = random.nextInt(10); // 0부터 9까지의 난수
      codeBuilder.append(digit);
    }
    logger.info("메일주소 : " + email);
    logger.info("인증번호 : " + codeBuilder.toString());

    simpleMailMessage.setTo(email);
    simpleMailMessage.setSubject("EGG.GG에 회원가입 해주셔서 감사합니다. 인증을 위한 메일입니다.");
    simpleMailMessage.setText("인증번호를 입력해주세요.\n인증번호 : " + codeBuilder.toString()); // setSubject 대신 setText 사용

/*
    simpleMailMessage.setSubject("반갑습니다.", "UTF-8");
    simpleMailMessage.setText("인증번호 : " + codeBuilder.toString(), "UTF-8");*/

    javaMailSender.send(simpleMailMessage);

    return codeBuilder.toString();
  }



}
