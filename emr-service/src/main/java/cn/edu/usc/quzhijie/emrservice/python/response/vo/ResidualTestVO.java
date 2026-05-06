package cn.edu.usc.quzhijie.emrservice.python.response.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResidualTestVO {
    @JsonProperty("lb_pvalue")
    private Double lbPvalue;
    @JsonProperty("is_white_noise")
    private Boolean isWhiteNoise;
}
