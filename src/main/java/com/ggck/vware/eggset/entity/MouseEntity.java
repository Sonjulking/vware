package com.ggck.vware.eggset.entity;

import com.ggck.vware.eggset.dto.KeyBoardDTO;
import com.ggck.vware.eggset.dto.MouseDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "mouse_table")
public class MouseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment ,mySQL 기준
    private Long mouseId;

    @Column
    private String mouseName;

    // DTO에 담긴 값들을 Entity 객체로 옮겨 담는 작업
    public static MouseEntity toSaveEntity(MouseDTO mouseDTO) {
        MouseEntity mouseEntity = new MouseEntity();
        mouseEntity.setMouseId(mouseDTO.getMouseId());
        mouseEntity.setMouseName(mouseDTO.getMouseName());

        return mouseEntity;
    }
}
