package com.ggck.vware.eggset.controller;

import com.ggck.vware.eggset.dto.*;
import com.ggck.vware.eggset.service.EggSetAdminService;
import com.ggck.vware.eggset.service.EggSetHomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EggSetHomeController {
    private final EggSetHomeService eggSetHomeService;
    private final EggSetAdminService eggSetAdminService;
//    @GetMapping("/")      // 기본주소 요청이 오면
//    public String index() {  // 이 메서드가 호출 되고
//        return "index";      // resources의 templates 폴더에 있는 index.html 을 찾아가는 것이다/
//    }

    // 글 목록 조회
    @GetMapping("/EggSet")
    public String findAll(Model model) {
        // DB에서 전체 게시글 데이터를 가져와서 index.html 에 보여준다 (홈 화면)
        List<EggSetDTO> eggSetDTOList = eggSetHomeService.findAll();

        List<KeyBoardDTO> keyBoardDTOList = eggSetAdminService.findKeyboard();
        List<MouseDTO> mouseDTOList = eggSetAdminService.findMouse();
        List<MonitorDTO> monitorDTOList = eggSetAdminService.findMonitor();
        List<GpuDTO> gpuDTOList = eggSetAdminService.findGpu();

        // 전체 리스트 조회
        model.addAttribute("eggSetList", eggSetDTOList);
        // 키보드 Select 리스트
        model.addAttribute("keyBoardList", keyBoardDTOList);
        // 마우스 Select 리스트
        model.addAttribute("mouseList", mouseDTOList);
        // 모니터 Select 리스트
        model.addAttribute("monitorList", monitorDTOList);
        // 그래픽카드 Select 리스트
        model.addAttribute("gpuList", gpuDTOList);

        return "EGGSET/equipment";
    }

}