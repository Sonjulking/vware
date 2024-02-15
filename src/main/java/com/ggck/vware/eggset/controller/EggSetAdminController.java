package com.ggck.vware.eggset.controller;

import com.ggck.vware.eggset.dto.EggSetDTO;
import com.ggck.vware.eggset.dto.KeyBoardDTO;
import com.ggck.vware.eggset.service.EggSetAdminService;
import com.ggck.vware.eggset.service.EggSetHomeService;
import com.ggck.vware.eggset.service.EggSetService;
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
    private final EggSetAdminService eggSetAdminService; // 생성사 주입방식으로 의존성 주입

    // 키보드 목록 조회
    @GetMapping("/KeyBoard")
    public String findAll(Model model) {
        List<KeyBoardDTO> keyBoardDTOList = eggSetAdminService.findAll();
        // keyBoardList 라는 이름으로 데이터를 담음
        model.addAttribute("keyBoardList", keyBoardDTOList);
        return "EGGSET/newKeyboard";
    }

    @PostMapping("/KeyBoard/save")
    public String save(@ModelAttribute KeyBoardDTO keyBoardDTO, Model model)
    {
        System.out.println("keyBoardDTO = " + keyBoardDTO);
        eggSetAdminService.save(keyBoardDTO);
        List<KeyBoardDTO> keyBoardDTOList = eggSetAdminService.findAll();
        // eggSetList 라는 이름으로 데이터를 담음
        model.addAttribute("keyBoardList", keyBoardDTOList);
        return "EGGSET/newKeyboard";
    }
}
