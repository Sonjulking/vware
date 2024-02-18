package com.ggck.vware.eggset.entity;

import com.ggck.vware.eggset.dto.GpuDTO;
import com.ggck.vware.eggset.dto.MonitorDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "gpu_table")
public class GpuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment ,mySQL 기준
    private Long gpuId;

    @Column
    private String gpuName;

    public static GpuEntity toSaveEntity(GpuDTO gpuDTO) {
        GpuEntity gpuEntity = new GpuEntity();
        gpuEntity.setGpuId(gpuDTO.getGpuId());
        gpuEntity.setGpuName(gpuDTO.getGpuName());

        return gpuEntity;
    }
}
