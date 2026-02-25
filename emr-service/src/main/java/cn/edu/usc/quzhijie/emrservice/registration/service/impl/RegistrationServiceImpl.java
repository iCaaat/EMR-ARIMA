package cn.edu.usc.quzhijie.emrservice.registration.service.impl;

import cn.edu.usc.quzhijie.emrservice.registration.entity.Department;
import cn.edu.usc.quzhijie.emrservice.registration.mapper.RegistrationMapper;
import cn.edu.usc.quzhijie.emrservice.registration.service.RegistrationService;
import cn.edu.usc.quzhijie.emrservice.registration.vo.DepartmentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final RegistrationMapper registrationMapper;

    @Override
    public List<DepartmentVO> getDepartmentInfo() {
        // 1.获取科室信息
        List<Department> departments = registrationMapper.listDepartment();

        // 2.转换成VO并放入Map
        Map<Integer, DepartmentVO> map = new HashMap<>();
        for (Department department : departments) {
            DepartmentVO vo = new DepartmentVO();
            vo.setId(department.getId());
            vo.setName(department.getName());
            map.put(department.getId(), vo);
        }

        // 3.构建父子关系
        List<DepartmentVO> rootList = new ArrayList<>();

        for (Department department : departments) {
            DepartmentVO current = map.get(department.getId());

            // 根节点
            if (department.getParentId() == null || department.getParentId() == 0) {
                rootList.add(current);
            } else {
                // 找父节点
                DepartmentVO parent = map.get(department.getParentId());
                if (parent != null) {
                    parent.getChildren().add(current);
                }
            }
        }


        return rootList;
    }

}
