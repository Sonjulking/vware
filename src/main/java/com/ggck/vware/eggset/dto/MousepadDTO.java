package com.ggck.vware.eggset.dto;

import com.ggck.vware.eggset.entity.GpuEntity;
import com.ggck.vware.eggset.entity.MousepadEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MousepadDTO {
    private Long mousepadId;
    private String mousepadName;

    public static MousepadDTO toMousepadDTO(MousepadEntity mousepadEntity) {
        MousepadDTO mousepadDTO = new MousepadDTO();
        mousepadDTO.setMousepadId(mousepadEntity.getMousepadId());
        mousepadDTO.setMousepadName(mousepadEntity.getMousepadName());

        return mousepadDTO;
    }
}
