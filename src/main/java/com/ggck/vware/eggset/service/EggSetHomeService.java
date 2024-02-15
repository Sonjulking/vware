package com.ggck.vware.eggset.service;

import com.ggck.vware.eggset.dto.EggSetDTO;
import com.ggck.vware.eggset.dto.KeyBoardDTO;
import com.ggck.vware.eggset.entity.EggSetEntity;
import com.ggck.vware.eggset.repository.EggSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EggSetHomeService {
    private final EggSetRepository eggSetRepository;
    public List<EggSetDTO> findAll() {
        // Repository에서 뭔가를 가져올때는 무조건 Entity로 넘어오는데, (List 형태의 Entity)
        // Entity로 넘어온 객체를 DTO객체로 옮겨담아서 Controller로 return 해줘야 함.
        List<EggSetEntity> eggSetEntityList = eggSetRepository.findAll();
        List<EggSetDTO> eggSetDTOList = new ArrayList<>();
        // 반복문으로 접근하는 Entity 객체를 DTO로 변환을 하고, 그 변환된 객체를 List에 담는 작업을
        // boardEntityList에 있는 모든걸 옮겨 담을 때 까지 for문을 돌리는 개념
        for (EggSetEntity eggSetEntity : eggSetEntityList) {
            eggSetDTOList.add(EggSetDTO.toEggSetDTO(eggSetEntity));
        }
        return eggSetDTOList;
    }
}
