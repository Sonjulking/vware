package com.ggck.vware.eggset.dto;

import com.ggck.vware.eggset.entity.KeyBoardEntity;
import com.ggck.vware.eggset.entity.MonitorEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonitorDTO {
    private Long monitorId;
    private String monitorName;

    public static MonitorDTO toMonitorDTO(MonitorEntity monitorEntity) {
        MonitorDTO monitorDTO = new MonitorDTO();
        monitorDTO.setMonitorId(monitorEntity.getMonitorId());
        monitorDTO.setMonitorName(monitorEntity.getMonitorName());

        return monitorDTO;
    }
}
