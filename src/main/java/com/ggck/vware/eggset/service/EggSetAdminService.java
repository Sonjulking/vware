package com.ggck.vware.eggset.service;

import com.ggck.vware.eggset.dto.EggSetDTO;
import com.ggck.vware.eggset.dto.KeyBoardDTO;
import com.ggck.vware.eggset.entity.EggSetEntity;
import com.ggck.vware.eggset.entity.KeyBoardEntity;
import com.ggck.vware.eggset.repository.EggSetAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EggSetAdminService {
    private final EggSetAdminRepository eggSetAdminRepository;
    public List<KeyBoardDTO> findKeyBoard() {
        List<KeyBoardEntity> keyBoardEntityList = eggSetAdminRepository.findAll();
        List<KeyBoardDTO> keyBoardDTOList = new ArrayList<>();

        for (KeyBoardEntity keyBoardEntity : keyBoardEntityList) {
            keyBoardDTOList.add(KeyBoardDTO.toKeyBoardDTO(keyBoardEntity));
        }
        return keyBoardDTOList;
    }

    public void save(KeyBoardDTO keyBoardDTO) {
        KeyBoardEntity keyBoardEntity = KeyBoardEntity.toSaveEntity(keyBoardDTO);
        eggSetAdminRepository.save(keyBoardEntity);
    }
}
