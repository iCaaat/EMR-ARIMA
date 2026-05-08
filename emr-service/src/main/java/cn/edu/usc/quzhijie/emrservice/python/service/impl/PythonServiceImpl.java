package cn.edu.usc.quzhijie.emrservice.python.service.impl;

import cn.edu.usc.quzhijie.emrservice.common.exception.BizException;
import cn.edu.usc.quzhijie.emrservice.python.config.PythonServerApi;
import cn.edu.usc.quzhijie.emrservice.python.mapper.AppointmentPredictMapper;
import cn.edu.usc.quzhijie.emrservice.python.request.TimePointRequest;
import cn.edu.usc.quzhijie.emrservice.python.response.ForecastResponse;
import cn.edu.usc.quzhijie.emrservice.python.response.vo.AnalysisVO;
import cn.edu.usc.quzhijie.emrservice.python.response.vo.ModelVO;
import cn.edu.usc.quzhijie.emrservice.python.response.vo.ResidualTestVO;
import cn.edu.usc.quzhijie.emrservice.python.service.PythonService;
import cn.edu.usc.quzhijie.emrservice.python.vo.ArimaPredictVO;
import cn.edu.usc.quzhijie.emrservice.python.vo.ScheduleSuggestionVO;
import cn.edu.usc.quzhijie.emrservice.registration.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PythonServiceImpl implements PythonService {
    private final RegistrationService registrationService;
    private final AppointmentPredictMapper appointmentPredictMapper;

    @Override
    public ArimaPredictVO arimaPredictByDepartmentTest(Integer departmentId, Integer days) {
        String url = PythonServerApi.API_PREDICT;
        String analyzeUrl = PythonServerApi.API_ANALYZE;
        RestTemplate restTemplate = new RestTemplate();

        // 1.历史数据
        List<Map<String, Object>> result = appointmentPredictMapper.listPredictData(departmentId);
        if (result.size() <= 5) {
            throw new BizException("数据量过少");
        }

        List<TimePointRequest> timePointRequests = new ArrayList<>();
        List<Integer> history = new ArrayList<>();
        List<String> dates = new ArrayList<>();

        for (Map<String, Object> obj : result) {
            Integer count = (Integer) obj.get("count");

            String date = obj.get("date").toString();

            TimePointRequest req = new TimePointRequest();
            req.setDate(date);
            req.setCount(count.intValue());

            timePointRequests.add(req);

            history.add(count.intValue());
            dates.add(date);
        }

        // 2.调用python
        // 请求
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("data", timePointRequests);
        requestBody.put("steps", days);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        // 调用
        ForecastResponse response = restTemplate.postForObject(
                url, request, ForecastResponse.class
        );
        // 生成图片
        restTemplate.postForObject(
                analyzeUrl, request, String.class
        );
        if (response == null) {
            throw new BizException("数据预测模块出现问题");
        }
        // 提取参数
        List<Double> forecast = response.getForecast();
        ModelVO model = response.getModel();
        AnalysisVO analysis = response.getAnalysis();
        ResidualTestVO residualTest = response.getResidualTest();

        List<String> futureDates = generateFutureDates(
                dates.get(dates.size() - 1),
                forecast.size()
        );

        // 3.生产排班建议
        List<ScheduleSuggestionVO> suggestions = buildSuggestions(forecast, history, futureDates);

        // 4.结果整理
        // 拼接dates
        List<String> allDates = new ArrayList<>();
        allDates.addAll(dates); // 历史
        allDates.addAll(futureDates); // 未来

        // 补 history
        List<Integer> fullHistory = new ArrayList<>(history);

        for (int i = 0; i < forecast.size(); i++) {
            fullHistory.add(null); // 未来没有历史数据
        }

        // 补forecast
        List<Double> fullForecast = new ArrayList<>();

        for (int i = 0; i < history.size(); i++) {
            fullForecast.add(null);
        }

        fullForecast.addAll(forecast);


        ArimaPredictVO vo = new ArimaPredictVO();
        vo.setDates(allDates);
        vo.setHistory(fullHistory);
        vo.setForecast(fullForecast);
        vo.setModel(model);
        vo.setAnalysis(analysis);
        vo.setSuggestions(suggestions);
        vo.setResidualTest(residualTest);

        return vo;
    }

    @Override
    public ArimaPredictVO arimaPredictByDepartment(Integer departmentId, Integer days) {
        String url = PythonServerApi.API_PREDICT;
        String analyzeUrl = PythonServerApi.API_ANALYZE;
        RestTemplate restTemplate = new RestTemplate();

        // 1.历史数据
        List<Map<String, Object>> result = registrationService.getDailyAppointmentCount(departmentId);
        if (result.size() <= 5) {
            throw new BizException("数据量过少");
        }

        List<TimePointRequest> timePointRequests = new ArrayList<>();
        List<Integer> history = new ArrayList<>();
        List<String> dates = new ArrayList<>();

        for (Map<String, Object> obj : result) {
            Long count = (Long) obj.get("count");

            String date = obj.get("date").toString();

            TimePointRequest req = new TimePointRequest();
            req.setDate(date);
            req.setCount(count.intValue());

            timePointRequests.add(req);

            history.add(count.intValue());
            dates.add(date);
        }

        // 2.调用python
        // 请求
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("data", timePointRequests);
        requestBody.put("steps", days);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        // 调用
        ForecastResponse response = restTemplate.postForObject(
                url, request, ForecastResponse.class
        );
        // 生成图片
        restTemplate.postForObject(
                analyzeUrl, request, String.class
        );
        if (response == null) {
            throw new BizException("数据预测模块出现问题");
        }
        // 提取参数
        List<Double> forecast = response.getForecast();
        ModelVO model = response.getModel();
        AnalysisVO analysis = response.getAnalysis();
        ResidualTestVO residualTest = response.getResidualTest();

        List<String> futureDates = generateFutureDates(
                dates.get(dates.size() - 1),
                forecast.size()
        );

        // 3.生产排班建议
        List<ScheduleSuggestionVO> suggestions = buildSuggestions(forecast, history, futureDates);

        // 4.结果整理
        // 拼接dates
        List<String> allDates = new ArrayList<>();
        allDates.addAll(dates); // 历史
        allDates.addAll(futureDates); // 未来

        // 补 history
        List<Integer> fullHistory = new ArrayList<>(history);

        for (int i = 0; i < forecast.size(); i++) {
            fullHistory.add(null); // 未来没有历史数据
        }

        // 补forecast
        List<Double> fullForecast = new ArrayList<>();

        for (int i = 0; i < history.size(); i++) {
            fullForecast.add(null);
        }

        fullForecast.addAll(forecast);


        ArimaPredictVO vo = new ArimaPredictVO();
        vo.setDates(allDates);
        vo.setHistory(fullHistory);
        vo.setForecast(fullForecast);
        vo.setModel(model);
        vo.setAnalysis(analysis);
        vo.setSuggestions(suggestions);
        vo.setResidualTest(residualTest);

        return vo;
    }

    private List<ScheduleSuggestionVO> buildSuggestions(
            List<Double> forecast,
            List<Integer> history,
            List<String> futureDates
    ) {

        List<ScheduleSuggestionVO> list = new ArrayList<>();

        double avg = history.stream().mapToInt(i -> i).average().orElse(0);

        for (int i = 0; i < forecast.size(); i++) {

            double f = forecast.get(i);

            ScheduleSuggestionVO vo = new ScheduleSuggestionVO();

            vo.setDate(futureDates.get(i));
            vo.setPredictedCount(f);

            // ===== 1. 负荷等级 =====
            if (f > avg * 1.3) {
                vo.setLevel("高");
            } else if (f < avg * 0.7) {
                vo.setLevel("低");
            } else {
                vo.setLevel("中");
            }

            // ===== 2. 推荐医生数 =====
            int baseDoctors = 1;

            int recommend = (int) Math.ceil(f / 10.0); // 每10人1个医生
            vo.setRecommendDoctors(Math.max(baseDoctors, recommend));

            // ===== 3. 文本建议 =====
            if ("高".equals(vo.getLevel())) {
                vo.setSuggestion("就诊压力较大，建议增加排班医生并延长门诊时间");
            } else if ("低".equals(vo.getLevel())) {
                vo.setSuggestion("就诊压力较小，可适当减少排班");
            } else {
                vo.setSuggestion("负荷正常，维持当前排班");
            }

            list.add(vo);
        }

        return list;
    }


    private List<String> generateFutureDates(String lastDate, int days) {

        List<String> futureDates = new ArrayList<>();

        LocalDate start = LocalDate.parse(lastDate);

        for (int i = 1; i <= days; i++) {
            futureDates.add(start.plusDays(i).toString());
        }

        return futureDates;
    }

}
