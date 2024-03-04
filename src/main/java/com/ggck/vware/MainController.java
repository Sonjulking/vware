package com.ggck.vware;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

  @GetMapping("/")
  public String root() {
    //return "EGG/main";     //전적검색 기능 완성전까지는 거시기해둠
    return "vbti/vbti_test";
  }

}
