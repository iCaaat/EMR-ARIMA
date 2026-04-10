package cn.edu.usc.quzhijie.emrservice.record.controller;

import cn.edu.usc.quzhijie.emrservice.common.result.Result;
import cn.edu.usc.quzhijie.emrservice.record.service.MedicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

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
}
