package com.ggck.vware.eggset.dto;

import com.ggck.vware.eggset.entity.GpuEntity;
import com.ggck.vware.eggset.entity.MonitorEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GpuDTO {
    private Long gpuId;
    private String gpuName;

    public static GpuDTO toGpuDTO(GpuEntity gpuEntity) {
        GpuDTO gpuDTO = new GpuDTO();
        gpuDTO.setGpuId(gpuEntity.getGpuId());
        gpuDTO.setGpuName(gpuEntity.getGpuName());

        return gpuDTO;
    }
}
