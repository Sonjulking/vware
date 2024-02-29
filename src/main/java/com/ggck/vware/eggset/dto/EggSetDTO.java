package com.ggck.vware.eggset.dto;

// 아래 lombok은 필요한 method를 자동으로 만들어줌
import com.ggck.vware.eggset.entity.EggSetEntity;
import lombok.*;

@Getter
@Setter
@ToString // 필드값 확인
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
// DTO(Data Transfer Object) 데이터를 전송할 때 사용하는 객체
public class EggSetDTO {
    private Long id;
    private String team;
    private String player;
    private String mouse;
    private String hz;
    private String dpi;
    private String sens;
    private String edpi;
    private String scopedsens;
    private String monitor;
    private String resolution;
    private String gpu;
    private String mousepad;
    private String keyboard;
    private String headset;


    // Entity를 DTO로 변환 like BoardEntity의 toSaveEntity
    public static EggSetDTO toEggSetDTO(EggSetEntity eggSetEntity) {
        EggSetDTO eggSetDTO = new EggSetDTO();
        eggSetDTO.setId(eggSetEntity.getId());
        eggSetDTO.setTeam(eggSetEntity.getTeam());
        eggSetDTO.setPlayer(eggSetEntity.getPlayer());
        eggSetDTO.setMouse(eggSetEntity.getMouse());
        eggSetDTO.setHz(eggSetEntity.getHz());
        eggSetDTO.setDpi(eggSetEntity.getDpi());
        eggSetDTO.setSens(eggSetEntity.getSens());
        eggSetDTO.setEdpi(eggSetEntity.getEdpi());
        eggSetDTO.setScopedsens(eggSetEntity.getScopedsens());
        eggSetDTO.setMonitor(eggSetEntity.getMonitor());
        eggSetDTO.setResolution(eggSetEntity.getResolution());
        eggSetDTO.setGpu(eggSetEntity.getGpu());
        eggSetDTO.setMousepad(eggSetEntity.getMousepad());
        eggSetDTO.setKeyboard(eggSetEntity.getKeyboard());
        eggSetDTO.setHeadset(eggSetEntity.getHeadset());

        return eggSetDTO;
    }
}
