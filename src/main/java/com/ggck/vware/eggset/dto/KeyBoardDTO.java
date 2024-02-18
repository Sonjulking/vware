package com.ggck.vware.eggset.dto;

import com.ggck.vware.eggset.entity.EggSetEntity;
import com.ggck.vware.eggset.entity.KeyBoardEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeyBoardDTO {
    private Long keyboardId;
    private String keyboardName;

    // Entity를 DTO로 변환 like BoardEntity의 toSaveEntity
    public static KeyBoardDTO toKeyBoardDTO(KeyBoardEntity keyBoardEntity) {
        KeyBoardDTO keyBoardDTO = new KeyBoardDTO();
        keyBoardDTO.setKeyboardId(keyBoardEntity.getKeyboardId());
        keyBoardDTO.setKeyboardName(keyBoardEntity.getKeyboardName());

        return keyBoardDTO;
    }
}
