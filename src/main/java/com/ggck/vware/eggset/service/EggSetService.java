package com.ggck.vware.eggset.service;

import com.ggck.vware.DataNotFoundException;
import com.ggck.vware.eggset.dto.*;
import com.ggck.vware.eggset.entity.*;
import com.ggck.vware.eggset.repository.*;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// DTO -> Entity (DTO를 Entity 클래스로 변환을 하거나) Entity 클래스 에서 함.
// ==> Controller로 부터 넘겨받을 때는 DTO로 넘겨받아 Repository로 Entity로 넘겨준다.
// Entity -> DTO (Entity를 DTO 클래스로 변환을 하거나) DTO 클래스 에서 함.
// ==> DB 데이터 조회같은 겨우는 Repository로 부터 Entity로 넘어와 Controller로 넘겨줄 때 DTO로 넘겨준다.

// Entity 클래스는 DB와 직접적인 연관이 있기 때문에, Entity에 담긴 값들이 DB에 영향을 줄 수 있음.
// View 단이라던지, 노출을 많이 시키지 않는 것이 좋음.
// Entity 클래스는 최대한 Serice 까지만 오도록 코드를 짜자.

@Service
@RequiredArgsConstructor
public class EggSetService {

  private final EggSetRepository eggSetRepository;
  private final KeyBoardRepository keyBoardRepository;
  private final MouseRepository mouseRepository;
  private final MonitorRepository monitorRepository;
  private final GpuRepository gpuRepository;

  //eggSet 기본 save
  public void save(EggSetDTO eggSetDTO) {
    EggSetEntity eggSetEntity = EggSetEntity.toSaveEntity(eggSetDTO);
    eggSetRepository.save(eggSetEntity);
  }

  //keyBoard 기본 save
  public void saveKeyboard(KeyBoardDTO keyBoardDTO) {
    KeyBoardEntity keyBoardEntity = KeyBoardEntity.toSaveEntity(keyBoardDTO);
    keyBoardRepository.save(keyBoardEntity);
  }

  //mouse 기본 save
  public void saveMouse(MouseDTO mouseDTO) {
    MouseEntity mouseEntity = MouseEntity.toSaveEntity(mouseDTO);
    mouseRepository.save(mouseEntity);
  }

  //monitor 기본 save
  public void saveMonitor(MonitorDTO monitorDTO) {
    MonitorEntity monitorEntity = MonitorEntity.toSaveEntity(monitorDTO);
    monitorRepository.save(monitorEntity);
  }

  //gpu 기본 save
  public void saveGpu(GpuDTO gpuDTO) {
    GpuEntity gpuEntity = GpuEntity.toSaveEntity(gpuDTO);
    gpuRepository.save(gpuEntity);
  }

  public EggSetEntity eggGetEntity(String playerName) {
    Optional<EggSetEntity> eggSetEntity = this.eggSetRepository.findByPlayer(playerName);
    if (eggSetEntity.isPresent()) {
      return eggSetEntity.get();
    } else {
      throw new DataNotFoundException("eggSetEntity found");
    }
  }

  public void update(EggSetEntity eggSetEntity, EggSetDTO eggSetDTO) {

    eggSetEntity.setTeam(eggSetDTO.getTeam());
    eggSetEntity.setPlayer(eggSetEntity.getPlayer());
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

    this.eggSetRepository.save(eggSetEntity);
  }
}
