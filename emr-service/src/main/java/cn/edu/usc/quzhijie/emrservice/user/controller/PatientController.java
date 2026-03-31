package cn.edu.usc.quzhijie.emrservice.user.controller;

import cn.edu.usc.quzhijie.emrservice.common.result.Result;
import cn.edu.usc.quzhijie.emrservice.user.dto.PatientDetailDTO;
import cn.edu.usc.quzhijie.emrservice.user.service.PatientService;
import cn.edu.usc.quzhijie.emrservice.user.vo.PatientDetailVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.UserPatientVO;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@Validated
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

    @PutMapping()
    public Result<String> updatePatientDetail(Authentication authentication,
                                              @RequestBody @Validated PatientDetailDTO dto) {
        Claims claims = (Claims) authentication.getDetails();
        Integer uid = (Integer) claims.get("uid");
        return Result.success(patientService.updatePatientDetail(uid, dto));
    }

    @PostMapping()
    public Result<String> addPatientDetail(Authentication authentication,
                                           @RequestBody @Validated PatientDetailDTO dto) {
        Claims claims = (Claims) authentication.getDetails();
        Integer uid = (Integer) claims.get("uid");
        return Result.success(patientService.addPatient(uid, dto));
    }
}
