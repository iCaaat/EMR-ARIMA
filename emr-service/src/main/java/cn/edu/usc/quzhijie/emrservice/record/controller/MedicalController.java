package cn.edu.usc.quzhijie.emrservice.record.controller;

import cn.edu.usc.quzhijie.emrservice.common.result.Result;
import cn.edu.usc.quzhijie.emrservice.record.dto.PostRecordDTO;
import cn.edu.usc.quzhijie.emrservice.record.service.MedicalService;
import cn.edu.usc.quzhijie.emrservice.record.vo.MyRecordVO;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import java.util.List;

@RestController
@RequestMapping("/medical")
@RequiredArgsConstructor
public class MedicalController {
    private final MedicalService medicalService;

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> getMedicalRecordPdf() {
        return ResponseEntity.ok()
                .header("Content-Disposition", "inline; filename=medical.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(medicalService.getMedicalRecordPdf());
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping
    public Result<String> postRecord(Authentication authentication,
                                     @RequestBody @Validated PostRecordDTO dto) {
        Claims claims = (Claims) authentication.getDetails();
        Integer uid = (Integer) claims.get("uid");

        medicalService.addMedicalRecord(uid, dto);
        return Result.success("病历提交成功");
    }

    @GetMapping("/{patientId}")
    public Result<List<MyRecordVO>> getMyRecords(Authentication authentication, @PathVariable Integer patientId) {
        Claims claims = (Claims) authentication.getDetails();
        Integer uid = (Integer) claims.get("uid");

        return Result.success(medicalService.getMyRecords(uid, patientId));
    }
}
