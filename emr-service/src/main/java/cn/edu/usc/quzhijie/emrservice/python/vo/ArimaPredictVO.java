package cn.edu.usc.quzhijie.emrservice.python.vo;

import cn.edu.usc.quzhijie.emrservice.python.response.vo.AnalysisVO;
import cn.edu.usc.quzhijie.emrservice.python.response.vo.ModelVO;
import lombok.Data;

import java.util.List;

@Data
public class ArimaPredictVO {
    // 1.趋势数据（用于折线图）
    private List<String> dates;          // 日期（x轴）
    private List<Integer> history;       // 历史数据
    private List<Double> forecast;       // 预测数据

    // 2.模型信息
    private ModelVO model;

    // 3.统计信息
    private AnalysisVO analysis;

    // 3.排班推荐
    private List<ScheduleSuggestionVO> suggestions;
}
