package cn.edu.usc.quzhijie.emrservice.user.service.impl;

import cn.edu.usc.quzhijie.emrservice.user.service.PatientService;
import cn.edu.usc.quzhijie.emrservice.user.vo.UserPatientVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {
    @Override
    public List<UserPatientVO> getUserPatients(Integer uid) {

        return List.of();
    }
}
