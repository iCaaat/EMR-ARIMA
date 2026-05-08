package cn.edu.usc.quzhijie.emrservice.python.response.vo;

import lombok.Data;

/**
 * ARIMA模型本身
 */
@Data
public class ModelVO {

    private Integer p;
    private Integer d;
    private Integer q;

    private Double aic;
    private Double bic;

    private Double mse;
    private Double mae;
    private Double rmse;
}
