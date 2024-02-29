package com.ggck.vware.eggset.dto;

import com.ggck.vware.eggset.entity.GpuEntity;
import com.ggck.vware.eggset.entity.TeamEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamDTO {
    private Long teamId;
    private String teamName;

    public static TeamDTO toTeamDTO(TeamEntity teamEntity) {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setTeamId(teamEntity.getTeamId());
        teamDTO.setTeamName(teamEntity.getTeamName());

        return teamDTO;
    }
}
