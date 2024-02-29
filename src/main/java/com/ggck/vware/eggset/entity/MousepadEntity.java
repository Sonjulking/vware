package com.ggck.vware.eggset.entity;

import com.ggck.vware.eggset.dto.GpuDTO;
import com.ggck.vware.eggset.dto.MousepadDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "mousepad_table")
public class MousepadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment ,mySQL 기준
    private Long mousepadId;

    @Column
    private String mousepadName;

    public static MousepadEntity toSaveEntity(MousepadDTO mousepadDTO) {
        MousepadEntity mousepadEntity = new MousepadEntity();
        mousepadEntity.setMousepadId(mousepadDTO.getMousepadId());
        mousepadEntity.setMousepadName(mousepadDTO.getMousepadName());

        return mousepadEntity;
    }
}
