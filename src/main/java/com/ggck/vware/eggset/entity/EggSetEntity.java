package com.ggck.vware.eggset.entity;

import com.ggck.vware.eggset.dto.EggSetDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// DB의 테이블 역할을 하는 클래스
@Entity
@Getter
@Setter
@Table(name = "board_table")
public class EggSetEntity {
    @Id // pk 컬럼 지정. 필수
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment ,mySQL 기준
    private Long id;

    @Column // 이렇게 하면 default 설정, 크기 255 - null 가능
    private String team;

    @Column(length = 20, nullable = false)  // null일 수 없다.
    private String player;

    @Column
    private String mouse;

    @Column
    private String hz;

    @Column
    private String dpi;

    @Column
    private String sens;

    @Column
    private String edpi;

    @Column
    private String scopedsens;

    @Column
    private String monitor;

    @Column
    private String resolution;

    @Column
    private String gpu;

    @Column
    private String mousepad;

    @Column
    private String keyboard;

    @Column
    private String headset;

    // DTO에 담긴 값들을 Entity 객체로 옮겨 담는 작업
    public static EggSetEntity toSaveEntity(EggSetDTO eggSetDTO) {
        EggSetEntity eggSetEntity = new EggSetEntity();
        eggSetEntity.setId(eggSetDTO.getId());
        eggSetEntity.setTeam(eggSetDTO.getTeam());
        eggSetEntity.setPlayer(eggSetDTO.getPlayer());
        eggSetEntity.setMouse(eggSetDTO.getMouse());
        eggSetEntity.setHz(eggSetDTO.getHz());
        eggSetEntity.setDpi(eggSetDTO.getDpi());
        eggSetEntity.setSens(eggSetDTO.getSens());
        eggSetEntity.setEdpi(eggSetDTO.getEdpi());
        eggSetEntity.setScopedsens(eggSetDTO.getScopedsens());
        eggSetEntity.setMonitor(eggSetDTO.getMonitor());
        eggSetEntity.setResolution(eggSetDTO.getResolution());
        eggSetEntity.setGpu(eggSetDTO.getGpu());
        eggSetEntity.setMousepad(eggSetDTO.getMousepad());
        eggSetEntity.setKeyboard(eggSetDTO.getKeyboard());
        eggSetEntity.setHeadset(eggSetDTO.getHeadset());

        return eggSetEntity;
    }
}
