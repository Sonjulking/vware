package com.ggck.vware.eggset.entity;

import com.ggck.vware.eggset.dto.KeyBoardDTO;
import com.ggck.vware.eggset.dto.MonitorDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "monitor_table")
public class MonitorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment ,mySQL 기준
    private Long monitorId;

    @Column
    private String monitorName;

    public static MonitorEntity toSaveEntity(MonitorDTO monitorDTO) {
        MonitorEntity monitorEntity = new MonitorEntity();
        monitorEntity.setMonitorId(monitorDTO.getMonitorId());
        monitorEntity.setMonitorName(monitorDTO.getMonitorName());

        return monitorEntity;
    }
}
