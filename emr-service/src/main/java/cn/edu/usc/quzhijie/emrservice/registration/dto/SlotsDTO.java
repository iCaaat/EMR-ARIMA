package cn.edu.usc.quzhijie.emrservice.registration.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SlotsDTO {
    @NotNull(message = "排班ID不能为空")
    private Integer scheduleId;
    @NotBlank(message = "时段不能为空")
    private String period;
}
