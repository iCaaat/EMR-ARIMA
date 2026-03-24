package cn.edu.usc.quzhijie.emrservice.user.controller;

import cn.edu.usc.quzhijie.emrservice.common.result.Result;
import cn.edu.usc.quzhijie.emrservice.user.service.PatientService;
import cn.edu.usc.quzhijie.emrservice.user.vo.PatientDetailVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.UserPatientVO;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;
    @GetMapping
    public Result<List<UserPatientVO>> getUserPatients(Authentication authentication) {
        Claims claims  = (Claims) authentication.getDetails();
        Integer uid = (Integer) claims.get("uid");
        return Result.success(patientService.getUserPatients(uid));
    }

    @GetMapping("/{patientId}")
    public Result<PatientDetailVO> getPatientDetail(Authentication authentication,
                                                    @PathVariable Integer patientId) {
        Claims claims = (Claims) authentication.getDetails();
        Integer uid = (Integer) claims.get("uid");
        return Result.success(patientService.getPatientDetail(uid, patientId));
    }
}
