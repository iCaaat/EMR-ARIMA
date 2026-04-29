package cn.edu.usc.quzhijie.emrservice.python.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/python")
public class PythonController {
    @GetMapping("/data")
    public Object getData() {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:5000/data";

        return restTemplate.getForObject(url, Object.class);
    }

    @PostMapping("/predict")
    public Object predict() {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:5000/predict";

        // 模拟数据库数据
        List<Integer> data = Arrays.asList(100, 120, 130, 90, 150, 170);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<Integer>> request = new HttpEntity<>(data, headers);

        return restTemplate.postForObject(url, request, Object.class);
    }

}
