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
    private String name;
    private String keyboard;
    private String mouse;
    private String edpi;
    private String sensi;
    private String monitor;
    private String gpu;

    // Entity를 DTO로 변환 like BoardEntity의 toSaveEntity
    public static EggSetDTO toEggSetDTO(EggSetEntity eggSetEntity) {
        EggSetDTO eggSetDTO = new EggSetDTO();
        eggSetDTO.setId(eggSetEntity.getId());
        eggSetDTO.setName(eggSetEntity.getName());
        eggSetDTO.setKeyboard(eggSetEntity.getKeyboard());
        eggSetDTO.setMouse(eggSetEntity.getMouse());
        eggSetDTO.setEdpi(eggSetEntity.getEdpi());
        eggSetDTO.setSensi(eggSetEntity.getSensi());
        eggSetDTO.setMonitor(eggSetEntity.getMonitor());
        eggSetDTO.setGpu(eggSetEntity.getGpu());
        return eggSetDTO;
    }
}
