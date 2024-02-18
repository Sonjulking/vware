package com.ggck.vware.eggset.service;

import com.ggck.vware.eggset.dto.GpuDTO;
import com.ggck.vware.eggset.dto.KeyBoardDTO;
import com.ggck.vware.eggset.dto.MonitorDTO;
import com.ggck.vware.eggset.dto.MouseDTO;
import com.ggck.vware.eggset.entity.GpuEntity;
import com.ggck.vware.eggset.entity.KeyBoardEntity;
import com.ggck.vware.eggset.entity.MonitorEntity;
import com.ggck.vware.eggset.entity.MouseEntity;
import com.ggck.vware.eggset.repository.GpuRepository;
import com.ggck.vware.eggset.repository.KeyBoardRepository;
import com.ggck.vware.eggset.repository.MonitorRepository;
import com.ggck.vware.eggset.repository.MouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EggSetAdminService {
    private final KeyBoardRepository keyBoardRepository;
    private final MouseRepository mouseRepository;
    private final MonitorRepository monitorRepository;
    private final GpuRepository gpuRepository;

    //키보드 목록 조회
    public List<KeyBoardDTO> findKeyboard() {
        List<KeyBoardEntity> keyBoardEntityList = keyBoardRepository.findAll();
        List<KeyBoardDTO> keyBoardDTOList = new ArrayList<>();

        for (KeyBoardEntity keyBoardEntity : keyBoardEntityList) {
            keyBoardDTOList.add(KeyBoardDTO.toKeyBoardDTO(keyBoardEntity));
        }
        return keyBoardDTOList;
    }

    //키보드 저장
    public void keyBoardSave(KeyBoardDTO keyBoardDTO) {
        KeyBoardEntity keyBoardEntity = KeyBoardEntity.toSaveEntity(keyBoardDTO);
        keyBoardRepository.save(keyBoardEntity);
    }
    
    //마우스 목록 조회
    public List<MouseDTO> findMouse() {
        List<MouseEntity> mouseEntityList = mouseRepository.findAll();
        List<MouseDTO> mouseDTOList = new ArrayList<>();

        for (MouseEntity mouseEntity : mouseEntityList) {
            mouseDTOList.add(MouseDTO.toMouseDTO(mouseEntity));
        }
        return mouseDTOList;
    }

    //마우스 저장
    public void mouseSave(MouseDTO mouseDTO) {
        MouseEntity mouseEntity = MouseEntity.toSaveEntity(mouseDTO);
        mouseRepository.save(mouseEntity);
    }

    //모니터 목록 조회
    public List<MonitorDTO> findMonitor() {
        List<MonitorEntity> monitorEntityList = monitorRepository.findAll();
        List<MonitorDTO> monitorDTOList = new ArrayList<>();

        for (MonitorEntity monitorEntity : monitorEntityList) {
            monitorDTOList.add(MonitorDTO.toMonitorDTO(monitorEntity));
        }
        return monitorDTOList;
    }

    //모니터 저장
    public void monitorSave(MonitorDTO monitorDTO) {
        MonitorEntity monitorEntity = MonitorEntity.toSaveEntity(monitorDTO);
        monitorRepository.save(monitorEntity);
    }

    //그래픽카드 목록 조회
    public List<GpuDTO> findGpu() {
        List<GpuEntity> gpuEntityList = gpuRepository.findAll();
        List<GpuDTO> gpuDTOList = new ArrayList<>();

        for (GpuEntity gpuEntity : gpuEntityList) {
            gpuDTOList.add(GpuDTO.toGpuDTO(gpuEntity));
        }
        return gpuDTOList;
    }

    //그래픽카드 저장
    public void gpuSave(GpuDTO gpuDTO) {
        GpuEntity gpuEntity = GpuEntity.toSaveEntity(gpuDTO);
        gpuRepository.save(gpuEntity);
    }
}
