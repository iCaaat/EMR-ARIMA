package cn.edu.usc.quzhijie.emrservice.record.service.impl;

import cn.edu.usc.quzhijie.emrservice.common.util.PdfGenerator;
import cn.edu.usc.quzhijie.emrservice.record.service.MedicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class MedicalServiceImpl implements MedicalService {
    private final PdfGenerator pdfGenerator;

    @Override
    public byte[] getMedicalRecordPdf() {
        Context context = new Context();
        context.setVariable("name", "张三");
        context.setVariable("age", 25);
        context.setVariable("diagnosis", "感冒");
        return pdfGenerator.generatePdf("medical-record", context);
    }
}
