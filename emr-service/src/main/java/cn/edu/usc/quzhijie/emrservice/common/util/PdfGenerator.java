package cn.edu.usc.quzhijie.emrservice.common.util;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class PdfGenerator {
    private final TemplateEngine templateEngine;

    public byte[] generatePdf(String templateName, Context context) {

        try {

            // 1. 生成HTML
            String html = templateEngine.process(templateName, context);

            ByteArrayOutputStream os = new ByteArrayOutputStream();

            // 2. 创建PDF构建器
            PdfRendererBuilder builder = new PdfRendererBuilder();

            // 3. 加载中文字体（关键代码）
            ClassPathResource resource = new ClassPathResource("fonts/msyh.ttf");

            builder.useFont(
                    () -> {
                        try {
                            return resource.getInputStream();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    },
                    "Microsoft YaHei"
            );

            // 4. 生成PDF
            builder.withHtmlContent(html, null);
            builder.toStream(os);
            builder.run();

            return os.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("生成PDF失败", e);
        }
    }
}
