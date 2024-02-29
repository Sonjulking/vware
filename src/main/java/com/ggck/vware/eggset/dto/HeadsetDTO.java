package com.ggck.vware.eggset.dto;

import com.ggck.vware.eggset.entity.GpuEntity;
import com.ggck.vware.eggset.entity.HeadsetEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeadsetDTO {
    private Long headsetId;
    private String headsetName;

    public static HeadsetDTO toHeadsetDTO(HeadsetEntity headsetEntity) {
        HeadsetDTO headsetDTO = new HeadsetDTO();
        headsetDTO.setHeadsetId(headsetEntity.getHeadsetId());
        headsetDTO.setHeadsetName(headsetEntity.getHeadsetName());

        return headsetDTO;
    }
}
