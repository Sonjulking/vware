package com.ggck.vware.eggset.controller;

import com.ggck.vware.eggset.dto.GpuDTO;
import com.ggck.vware.eggset.dto.KeyBoardDTO;
import com.ggck.vware.eggset.dto.MonitorDTO;
import com.ggck.vware.eggset.dto.MouseDTO;
import com.ggck.vware.eggset.service.EggSetAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/EggSetAdmin")
public class EggSetAdminController {

  private final EggSetAdminService eggSetAdminService; // 생성자 주입방식으로 의존성 주입

  //키보드 목록 조회
  @GetMapping("/KeyBoard")
  public String findKeyboard(Model model) {
    List<KeyBoardDTO> keyBoardDTOList = eggSetAdminService.findKeyboard();
    // keyBoardList 라는 이름으로 데이터를 담음
    model.addAttribute("keyBoardList", keyBoardDTOList);
    return "EGGSET/newKeyboard";
  }

  //키보드 신규 등록
  @PostMapping("/KeyBoard/save")
  public String saveKeyboard(@ModelAttribute KeyBoardDTO keyBoardDTO, Model model) {
    System.out.println("keyBoardDTO = " + keyBoardDTO);
    eggSetAdminService.keyBoardSave(keyBoardDTO);
    List<KeyBoardDTO> keyBoardDTOList = eggSetAdminService.findKeyboard();
    // eggSetList 라는 이름으로 데이터를 담음
    model.addAttribute("keyBoardList", keyBoardDTOList);
    return "EGGSET/newKeyboard";
  }

  //마우스 목록 조회
  @GetMapping("/Mouse")
  public String findMouse(Model model) {
    List<MouseDTO> mouseDTOList = eggSetAdminService.findMouse();
    // keyBoardList 라는 이름으로 데이터를 담음
    model.addAttribute("mouseList", mouseDTOList);
    return "EGGSET/newMouse";
  }

  //마우스 신규 등록
  @PostMapping("/Mouse/save")
  public String saveMouse(@ModelAttribute MouseDTO mouseDTO, Model model) {
    System.out.println("mouseDTO = " + mouseDTO);
    eggSetAdminService.mouseSave(mouseDTO);
    List<MouseDTO> mouseDTOList = eggSetAdminService.findMouse();
    // mouseList 라는 이름으로 데이터를 담음
    model.addAttribute("mouseList", mouseDTOList);
    return "EGGSET/newMouse";
  }

  //모니터 목록 조회
  @GetMapping("/Monitor")
  public String findMonitor(Model model) {
    List<MonitorDTO> monitorDTOList = eggSetAdminService.findMonitor();
    // keyBoardList 라는 이름으로 데이터를 담음
    model.addAttribute("monitorList", monitorDTOList);
    return "EGGSET/newMonitor";
  }

  //모니터 신규 등록
  @PostMapping("/Monitor/save")
  public String saveMonitor(@ModelAttribute MonitorDTO monitorDTO, Model model) {
    System.out.println("monitorDTO = " + monitorDTO);
    eggSetAdminService.monitorSave(monitorDTO);
    List<MonitorDTO> monitorDTOList = eggSetAdminService.findMonitor();
    model.addAttribute("monitorList", monitorDTOList);
    return "EGGSET/newMonitor";
  }

  //그래픽카드 목록 조회
  @GetMapping("/Gpu")
  public String findGpu(Model model) {
    List<GpuDTO> gpuDTOList = eggSetAdminService.findGpu();
    // keyBoardList 라는 이름으로 데이터를 담음
    model.addAttribute("gpuList", gpuDTOList);
    return "EGGSET/newGpu";
  }

  //모니터 신규 등록
  @PostMapping("/Gpu/save")
  public String saveGpu(@ModelAttribute GpuDTO gpuDTO, Model model) {
    System.out.println("gpuDTO = " + gpuDTO);
    eggSetAdminService.gpuSave(gpuDTO);
    List<GpuDTO> gpuDTOList = eggSetAdminService.findGpu();
    model.addAttribute("gpuList", gpuDTOList);
    return "EGGSET/newGpu";
  }
}
