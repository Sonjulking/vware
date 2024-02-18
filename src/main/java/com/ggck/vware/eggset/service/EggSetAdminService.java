package com.ggck.vware.eggset.service;

import com.ggck.vware.eggset.dto.KeyBoardDTO;
import com.ggck.vware.eggset.dto.MouseDTO;
import com.ggck.vware.eggset.entity.KeyBoardEntity;
import com.ggck.vware.eggset.entity.MouseEntity;
import com.ggck.vware.eggset.repository.KeyBoardRepository;
import com.ggck.vware.eggset.repository.MouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EggSetAdminService {
    private final KeyBoardRepository keyBoardRepository;
    private final MouseRepository mouseRepository;

    //키보드 목록 조회
    public List<KeyBoardDTO> findKeyboard() {
        List<KeyBoardEntity> keyBoardEntityList = keyBoardRepository.findAll();
        List<KeyBoardDTO> keyBoardDTOList = new ArrayList<>();

        for (KeyBoardEntity keyBoardEntity : keyBoardEntityList) {
            keyBoardDTOList.add(KeyBoardDTO.toKeyBoardDTO(keyBoardEntity));
        }
        return keyBoardDTOList;
    }

    //키보드 저장
    public void keyBoardSave(KeyBoardDTO keyBoardDTO) {
        KeyBoardEntity keyBoardEntity = KeyBoardEntity.toSaveEntity(keyBoardDTO);
        keyBoardRepository.save(keyBoardEntity);
    }
    
    //마우스 목록 조회
    public List<MouseDTO> findMouse() {
        List<MouseEntity> mouseEntityList = mouseRepository.findAll();
        List<MouseDTO> mouseDTOList = new ArrayList<>();

        for (MouseEntity mouseEntity : mouseEntityList) {
            mouseDTOList.add(MouseDTO.toMouseDTO(mouseEntity));
        }
        return mouseDTOList;
    }

    //키보드 저장
    public void mouseSave(MouseDTO mouseDTO) {
        MouseEntity mouseEntity = MouseEntity.toSaveEntity(mouseDTO);
        mouseRepository.save(mouseEntity);
    }
}
