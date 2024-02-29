package com.ggck.vware.eggset.controller;

import com.ggck.vware.eggset.dto.*;
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

  //기본
  @GetMapping("")
  public String giborn() {
    return "EGGSET/equipmentAdmin";
  }

  //키보드 목록 조회
  @GetMapping("/KeyBoard")
  public String findKeyboard(Model model) {
    List<KeyBoardDTO> keyBoardDTOList = eggSetAdminService.findKeyboard();
    model.addAttribute("keyBoardList", keyBoardDTOList);
    return "EGGSET/newKeyboard";
  }

  //키보드 신규 등록
  @PostMapping("/KeyBoard/save")
  public String saveKeyboard(@ModelAttribute KeyBoardDTO keyBoardDTO, Model model) {
    System.out.println("keyBoardDTO = " + keyBoardDTO);
    eggSetAdminService.keyBoardSave(keyBoardDTO);
    List<KeyBoardDTO> keyBoardDTOList = eggSetAdminService.findKeyboard();
    model.addAttribute("keyBoardList", keyBoardDTOList);
    return "EGGSET/newKeyboard";
  }

  //마우스 목록 조회
  @GetMapping("/Mouse")
  public String findMouse(Model model) {
    List<MouseDTO> mouseDTOList = eggSetAdminService.findMouse();
    model.addAttribute("mouseList", mouseDTOList);
    return "EGGSET/newMouse";
  }

  //마우스 신규 등록
  @PostMapping("/Mouse/save")
  public String saveMouse(@ModelAttribute MouseDTO mouseDTO, Model model) {
    System.out.println("mouseDTO = " + mouseDTO);
    eggSetAdminService.mouseSave(mouseDTO);
    List<MouseDTO> mouseDTOList = eggSetAdminService.findMouse();
    model.addAttribute("mouseList", mouseDTOList);
    return "EGGSET/newMouse";
  }

  //모니터 목록 조회
  @GetMapping("/Monitor")
  public String findMonitor(Model model) {
    List<MonitorDTO> monitorDTOList = eggSetAdminService.findMonitor();
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
    model.addAttribute("gpuList", gpuDTOList);
    return "EGGSET/newGpu";
  }

  //그래픽카드 신규 등록
  @PostMapping("/Gpu/save")
  public String saveGpu(@ModelAttribute GpuDTO gpuDTO, Model model) {
    System.out.println("gpuDTO = " + gpuDTO);
    eggSetAdminService.gpuSave(gpuDTO);
    List<GpuDTO> gpuDTOList = eggSetAdminService.findGpu();
    model.addAttribute("gpuList", gpuDTOList);
    return "EGGSET/newGpu";
  }

  //마우스패드 목록 조회
  @GetMapping("/Mousepad")
  public String findMousepad(Model model) {
    List<MousepadDTO> mousepadDTOList = eggSetAdminService.findMousepad();
    model.addAttribute("mousepadList", mousepadDTOList);
    return "EGGSET/newMousepad";
  }

  //마우스패드 신규 등록
  @PostMapping("/Mousepad/save")
  public String saveMousepad(@ModelAttribute MousepadDTO mousepadDTO, Model model) {
    System.out.println("mousepadDTO = " + mousepadDTO);
    eggSetAdminService.mousepadSave(mousepadDTO);
    List<MousepadDTO> mousepadDTOList = eggSetAdminService.findMousepad();
    model.addAttribute("mousepadList", mousepadDTOList);
    return "EGGSET/newMousepad";
  }

  //팀 목록 조회
  @GetMapping("/Team")
  public String findTeam(Model model) {
    List<TeamDTO> teamDTOList = eggSetAdminService.findTeam();
    model.addAttribute("teamList", teamDTOList);
    return "EGGSET/newTeam";
  }

  //팀 신규 등록
  @PostMapping("/Team/save")
  public String saveTeam(@ModelAttribute TeamDTO teamDTO, Model model) {
    System.out.println("teamDTO = " + teamDTO);
    eggSetAdminService.teamSave(teamDTO);
    List<TeamDTO> teamDTOList = eggSetAdminService.findTeam();
    model.addAttribute("teamList", teamDTOList);
    return "EGGSET/newTeam";
  }

  //헤드셋 목록 조회
  @GetMapping("/Headset")
  public String findHeadset(Model model) {
    List<HeadsetDTO> headsetDTOList = eggSetAdminService.findHeadset();
    model.addAttribute("headsetList", headsetDTOList);
    return "EGGSET/newHeadset";
  }

  //헤드셋 신규 등록
  @PostMapping("/Headset/save")
  public String saveHeadset(@ModelAttribute HeadsetDTO headsetDTO, Model model) {
    System.out.println("headsetDTO = " + headsetDTO);
    eggSetAdminService.headsetSave(headsetDTO);
    List<HeadsetDTO> headsetDTOList = eggSetAdminService.findHeadset();
    model.addAttribute("headsetList", headsetDTOList);
    return "EGGSET/newHeadset";
  }
}
