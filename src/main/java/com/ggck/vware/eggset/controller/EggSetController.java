package com.ggck.vware.eggset.controller;

import com.ggck.vware.eggset.dto.EggSetDTO;
import com.ggck.vware.eggset.dto.KeyBoardDTO;
import com.ggck.vware.eggset.service.EggSetAdminService;
import com.ggck.vware.eggset.service.EggSetHomeService;
import com.ggck.vware.eggset.service.EggSetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
@RequestMapping("/EggSet") // 계층적으로 만드는 것. board로 시작하는 주소를 먼저 받고 이하의 주소를 각각의 mapping 값이 일치하는 method가 호출된다.
public class EggSetController {
    private final EggSetService eggSetService; // 생성사 주입방식으로 의존성 주입
    private final EggSetHomeService eggSetHomeService; // 생성사 주입방식으로 의존성 주입
    private final EggSetAdminService eggSetAdminService;

    @PostMapping("/save") // post로 보내서 post로 받는 것.
    public String save(@ModelAttribute EggSetDTO eggSetDTO, Model model)
    // @ModelAttribute 사용하면, BoardDTO 클래스 객체를 찾아서 주소에 있는 들어간 필드값이 일치하면 해당하는 Setter 호출해서 값을 담아줌.
    {
        System.out.println("eggSetDTO = " + eggSetDTO);
        eggSetService.save(eggSetDTO);
        List<EggSetDTO> eggSetDTOList = eggSetHomeService.findAll();
        // eggSetList 라는 이름으로 데이터를 담음
        model.addAttribute("eggSetList", eggSetDTOList);
        return "redirect:/EggSet";
    }

    // 키보드 목록 조회
    @GetMapping("/")
    public String findKeyBoard(Model model) {
        System.out.println("키보드 키보드 : ");
        List<KeyBoardDTO> keyBoardDTOList = eggSetAdminService.findKeyBoard();
        // keyBoardList 라는 이름으로 데이터를 담음
        model.addAttribute("keyBoardList", keyBoardDTOList);

        return "EGGSET/equipment";
    }

}
