package com.ggck.vware.vbti;

import com.ggck.vware.user.SiteUser;
import com.ggck.vware.user.SiteUserDto;
import com.ggck.vware.user.SiteUserService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/vbti")
public class VbtiController {

  private final SiteUserService siteUserService;

  @GetMapping("/test")

  public String vbtiRoot() {
    return "vbti/vbti_test";
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/resultSave")
  public String resultSave(@RequestParam(name = "testResult") String testResult,
      Principal principal, Model model, HttpServletResponse response) {
    SiteUser siteUser = this.siteUserService.getUser(principal.getName());
    SiteUserDto testUserDto = SiteUserDto.builder()
        .userId(siteUser.getUserId())
        .userEmail(siteUser.getUserEmail())
        .userNickName(siteUser.getUserNickName())
        .password(siteUser.getPassword())
        .preferredPosition(siteUser.getPreferredPosition())
        .testResult(testResult)
        .build();
    System.out.println(testResult);
    this.siteUserService.savePositionResult(siteUser, testUserDto, testResult);

    response.setContentType("text/html; charset=utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = null;
    try {
      out = response.getWriter();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    out.println("<script> alert('포지션 결과가 저장 되었습니다'); location.href='/'; </script>");
    out.flush();

    return "redirect:/"; //TODO:추후 같은 포지션, 같은 캐릭터를 주로 플레이하는 프로의 장비를 추천해주는 웹사이트로 연결시킴
  }

}
