package cn.edu.usc.quzhijie.emrservice.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class FileConfig implements WebMvcConfigurer {
    private final FileProperties fileProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:" + fileProperties.getBaseDir() + "/");

    }
}
