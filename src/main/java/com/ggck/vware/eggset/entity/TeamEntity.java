package com.ggck.vware.eggset.entity;

import com.ggck.vware.eggset.dto.GpuDTO;
import com.ggck.vware.eggset.dto.TeamDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "team_table")
public class TeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment ,mySQL 기준
    private Long teamId;

    @Column
    private String teamName;

    public static TeamEntity toSaveEntity(TeamDTO teamDTO) {
        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setTeamId(teamDTO.getTeamId());
        teamEntity.setTeamName(teamDTO.getTeamName());

        return teamEntity;
    }
}
