package cn.edu.usc.quzhijie.emrservice.registration.controller;

import cn.edu.usc.quzhijie.emrservice.common.result.Result;
import cn.edu.usc.quzhijie.emrservice.registration.entity.Department;
import cn.edu.usc.quzhijie.emrservice.registration.service.RegistrationService;
import cn.edu.usc.quzhijie.emrservice.registration.vo.DepartmentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @GetMapping("/departments")
    public Result<List<DepartmentVO>> getDepartment() {
        return Result.success("查询成功", registrationService.getDepartmentInfo());
    }
}
