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

    @Column(length = 20, nullable = false)  // null일 수 없다.
    private String name;

    @Column // 이렇게 하면 default 설정, 크기 255 - null 가능
    private String keyboard;

    @Column
    private String mouse;

    @Column
    private String edpi;

    @Column
    private String sensi;

    @Column
    private String monitor;

    @Column
    private String gpu;

    // DTO에 담긴 값들을 Entity 객체로 옮겨 담는 작업
    public static EggSetEntity toSaveEntity(EggSetDTO eggSetDTO) {
        EggSetEntity eggSetEntity = new EggSetEntity();
        eggSetEntity.setId(eggSetDTO.getId());
        eggSetEntity.setName(eggSetDTO.getName());
        eggSetEntity.setKeyboard(eggSetDTO.getKeyboard());
        eggSetEntity.setMouse(eggSetDTO.getMouse());
        eggSetEntity.setEdpi(eggSetDTO.getEdpi());
        eggSetEntity.setSensi(eggSetDTO.getSensi());
        eggSetEntity.setMonitor(eggSetDTO.getMonitor());
        eggSetEntity.setGpu(eggSetDTO.getGpu());
        return eggSetEntity;
    }
}
