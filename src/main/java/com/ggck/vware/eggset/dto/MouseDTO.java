package com.ggck.vware.eggset.dto;

import com.ggck.vware.eggset.entity.KeyBoardEntity;
import com.ggck.vware.eggset.entity.MouseEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MouseDTO {
    private Long mouseId;
    private String mouseName;

    // Entity를 DTO로 변환 like BoardEntity의 toSaveEntity
    public static MouseDTO toMouseDTO(MouseEntity mouseEntity) {
        MouseDTO mouseDTO = new MouseDTO();
        mouseDTO.setMouseId(mouseEntity.getMouseId());
        mouseDTO.setMouseName(mouseEntity.getMouseName());

        return mouseDTO;
    }
}
