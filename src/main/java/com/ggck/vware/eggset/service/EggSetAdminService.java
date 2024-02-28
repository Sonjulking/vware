package com.ggck.vware.eggset.service;

import com.ggck.vware.eggset.dto.*;
import com.ggck.vware.eggset.entity.*;
import com.ggck.vware.eggset.repository.*;
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
    private final MousepadRepository mousepadRepository;
    private final TeamRepository teamRepository;
    private final HeadsetRepository headsetRepository;

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

    //마우스패드 목록 조회
    public List<MousepadDTO> findMousepad() {
        List<MousepadEntity> mousepadEntityList = mousepadRepository.findAll();
        List<MousepadDTO> mousepadDTOList = new ArrayList<>();

        for (MousepadEntity mousepadEntity : mousepadEntityList) {
            mousepadDTOList.add(MousepadDTO.toMousepadDTO(mousepadEntity));
        }
        return mousepadDTOList;
    }

    //마우스패드 저장
    public void mousepadSave(MousepadDTO mousepadDTO) {
        MousepadEntity mousepadEntity = MousepadEntity.toSaveEntity(mousepadDTO);
        mousepadRepository.save(mousepadEntity);
    }

    //팀 목록 조회
    public List<TeamDTO> findTeam() {
        List<TeamEntity> teamEntityList = teamRepository.findAll();
        List<TeamDTO> teamDTOList = new ArrayList<>();

        for (TeamEntity teamEntity : teamEntityList) {
            teamDTOList.add(TeamDTO.toTeamDTO(teamEntity));
        }
        return teamDTOList;
    }

    //팀 저장
    public void teamSave(TeamDTO teamDTO) {
        TeamEntity teamEntity = TeamEntity.toSaveEntity(teamDTO);
        teamRepository.save(teamEntity);
    }

    //헤드셋 목록 조회
    public List<HeadsetDTO> findHeadset() {
        List<HeadsetEntity> headsetEntityList = headsetRepository.findAll();
        List<HeadsetDTO> headsetDTOList = new ArrayList<>();

        for (HeadsetEntity headsetEntity : headsetEntityList) {
            headsetDTOList.add(HeadsetDTO.toHeadsetDTO(headsetEntity));
        }
        return headsetDTOList;
    }

    //헤드셋 저장
    public void headsetSave(HeadsetDTO headsetDTO) {
        HeadsetEntity headsetEntity = HeadsetEntity.toSaveEntity(headsetDTO);
        headsetRepository.save(headsetEntity);
    }
}
