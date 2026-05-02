package cn.edu.usc.quzhijie.emrservice.python.response;

import cn.edu.usc.quzhijie.emrservice.python.response.vo.AnalysisVO;
import cn.edu.usc.quzhijie.emrservice.python.response.vo.ModelVO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ForecastResponse {
    private List<Double> forecast;

    @JsonProperty("confidence_upper")
    private List<Double> confidenceUpper;
    @JsonProperty("confidence_lower")
    private List<Double> confidenceLower;

    private ModelVO model;
    private AnalysisVO analysis;
}
