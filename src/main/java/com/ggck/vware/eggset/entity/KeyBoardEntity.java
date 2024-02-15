package com.ggck.vware.eggset.entity;

import com.ggck.vware.eggset.dto.EggSetDTO;
import com.ggck.vware.eggset.dto.KeyBoardDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "keyboard_table")
public class KeyBoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment ,mySQL 기준
    private Long keyboardId;

    @Column
    private String keyboardName;

    // DTO에 담긴 값들을 Entity 객체로 옮겨 담는 작업
    public static KeyBoardEntity toSaveEntity(KeyBoardDTO keyBoardDTO) {
        KeyBoardEntity keyBoardEntity = new KeyBoardEntity();
        keyBoardEntity.setKeyboardId(keyBoardDTO.getKeyboardId());
        keyBoardEntity.setKeyboardName(keyBoardDTO.getKeyboardName());

        return keyBoardEntity;
    }
}
