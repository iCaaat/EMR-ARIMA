package cn.edu.usc.quzhijie.emrservice.python.vo;

import lombok.Data;

@Data
public class ScheduleSuggestionVO {
    private String date;            // 日期
    private Double predictedCount;  // 预测挂号量

    private String level;           // 负荷等级（低 / 中 / 高）

    private Integer recommendDoctors; // 建议医生数量

    private String suggestion;      // 文本建议
}
