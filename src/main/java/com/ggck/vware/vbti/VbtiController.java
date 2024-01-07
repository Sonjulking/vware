package com.ggck.vware.vbti;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/vbti")
public class VbtiController {
  @GetMapping("/test")

  public  String vbtiRoot(){
    return "vbti/vbti_test";
  }

  @GetMapping("/resultSave")
  public  String resultSave(){
    return "vbti/main";
  }

}
