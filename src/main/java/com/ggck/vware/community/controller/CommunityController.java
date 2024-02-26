package com.ggck.vware.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor //final이나 @Nonul인 필드 값만 파라미터로 받는 생성자를 만듬.
@RequestMapping("/community")
public class CommunityController {

  @GetMapping()
  public String communityMain() {

    return "community/community";
  }

}
