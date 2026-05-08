package cn.edu.usc.quzhijie.emrservice.python.controller;

import cn.edu.usc.quzhijie.emrservice.common.result.Result;
import cn.edu.usc.quzhijie.emrservice.python.config.PythonServerApi;
import cn.edu.usc.quzhijie.emrservice.python.service.PythonService;
import cn.edu.usc.quzhijie.emrservice.python.vo.ArimaPredictVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/python")
@RequiredArgsConstructor
public class PythonController {
    private final PythonService pythonService;

    @GetMapping("/data")
    public Object getData() {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:5000/data";

        return restTemplate.getForObject(url, Object.class);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/predict")
    public Result<ArimaPredictVO> predict(@RequestParam Integer departmentId, @RequestParam Integer days) {
        return Result.success(pythonService.arimaPredictByDepartmentTest(departmentId, days));
    }

}
