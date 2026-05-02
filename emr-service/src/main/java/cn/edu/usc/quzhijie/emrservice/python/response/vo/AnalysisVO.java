package cn.edu.usc.quzhijie.emrservice.python.response.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 数据本身的特征
 */
@Data
public class AnalysisVO {

    private Double mean;
    private Integer max;
    private Integer min;

    private String trend;

    @JsonProperty("is_stationary")
    private Boolean isStationary;
}
