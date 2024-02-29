package com.ggck.vware.eggset.entity;

import com.ggck.vware.eggset.dto.GpuDTO;
import com.ggck.vware.eggset.dto.HeadsetDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "headset_table")
public class HeadsetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment ,mySQL 기준
    private Long headsetId;

    @Column
    private String headsetName;

    public static HeadsetEntity toSaveEntity(HeadsetDTO headsetDTO) {
        HeadsetEntity headsetEntity = new HeadsetEntity();
        headsetEntity.setHeadsetId(headsetDTO.getHeadsetId());
        headsetEntity.setHeadsetName(headsetDTO.getHeadsetName());

        return headsetEntity;
    }
}
