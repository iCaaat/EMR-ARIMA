package cn.edu.usc.quzhijie.emrservice.schedule.service.impl;

import cn.edu.usc.quzhijie.emrservice.common.entity.DoctorExp;
import cn.edu.usc.quzhijie.emrservice.common.entity.DoctorSchedule;
import cn.edu.usc.quzhijie.emrservice.common.entity.ScheduleSlot;
import cn.edu.usc.quzhijie.emrservice.common.exception.BizException;
import cn.edu.usc.quzhijie.emrservice.common.result.PageResult;
import cn.edu.usc.quzhijie.emrservice.schedule.dto.ScheduleAddDTO;
import cn.edu.usc.quzhijie.emrservice.schedule.dto.ScheduleSearchDTO;
import cn.edu.usc.quzhijie.emrservice.schedule.dto.ScheduleUpdateDTO;
import cn.edu.usc.quzhijie.emrservice.schedule.mapper.DoctorScheduleMapper;
import cn.edu.usc.quzhijie.emrservice.schedule.mapper.ScheduleSlotMapper;
import cn.edu.usc.quzhijie.emrservice.schedule.service.DoctorScheduleService;
import cn.edu.usc.quzhijie.emrservice.schedule.vo.DoctorScheduleVO;
import cn.edu.usc.quzhijie.emrservice.schedule.vo.ScheduleDeleteVO;
import cn.edu.usc.quzhijie.emrservice.schedule.vo.ScheduleSearchVO;
import cn.edu.usc.quzhijie.emrservice.schedule.vo.ScheduleUpdateVO;
import cn.edu.usc.quzhijie.emrservice.user.mapper.DoctorMapper;
import cn.edu.usc.quzhijie.emrservice.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorScheduleServiceImpl implements DoctorScheduleService {
    private final DoctorScheduleMapper doctorScheduleMapper;
    private final ScheduleSlotMapper scheduleSlotMapper;
    private final UserMapper userMapper;
    private final DoctorMapper doctorMapper;

    @Override
    public PageResult<ScheduleSearchVO> searchSchedule(ScheduleSearchDTO dto) {
        Long total = doctorScheduleMapper.countScheduleByCondition(dto);

        if (total == 0) {
            return PageResult.empty();
        }

        List<ScheduleSearchVO> list = doctorScheduleMapper.listScheduleByCondition(dto);

        return new PageResult<>(total, dto.getPageNum(), dto.getPageSize(), list);
    }

    private Integer batchInsertSchedules(List<DoctorSchedule> list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }

        Integer result = doctorScheduleMapper.batchInsert(list);

        // 生成号源
        generateScheduleSlots(list);
        return result;
    }
    @Override
    @Transactional
    public Integer addSchedule(ScheduleAddDTO dto) {
        List<Integer> doctorIds = dto.getDoctorIds();
        String ruleType = dto.getRuleType();

        List<String> ruleTypes = List.of("single", "multi", "week");
        if (!ruleTypes.contains(ruleType)) {
            throw new BizException("排班规则类型不合法");
        }
        if (dto.getDoctorIds().isEmpty()) {
            throw new BizException("医生ID列表不能为空");
        }

        Integer result = 0;
        if ("single".equals(ruleType)) {
            LocalDate date = dto.getSingleDate();
            if (date == null) {
                throw new BizException("选择日期不能为空");
            }

            result = addScheduleForDate(dto);
        } else if ("multi".equals(ruleType)) {
            List<LocalDate> dates = dto.getMultiDates();
            if (dates == null || dates.isEmpty()) {
                throw new BizException("选择日期列表不能为空");
            }

            result = addScheduleForDateList(dto);
        } else if ("week".equals(ruleType)) {
            List<Integer> weekDays = dto.getWeekdays();
            LocalDate startDate = dto.getStartDate();
            LocalDate endDate = dto.getEndDate();
            if (weekDays == null || weekDays.isEmpty()) {
                throw new BizException("选择星期列表不能为空");
            }
            if (weekDays.stream().anyMatch(d -> d < 1 || d > 7)) {
                throw new BizException("星期列表中的值必须在1-7之间");
            }
            if (startDate == null || endDate == null) {
                throw new BizException("开始日期和结束日期不能为空");
            }
            if (endDate.isBefore(startDate)) {
                throw new BizException("结束日期必须在开始日期之后");
            }

            result = addScheduleForWeekdaysAndDateRange(dto);
        }
        return result;
    }
    private Integer addScheduleForDate(ScheduleAddDTO dto) {
        List<LocalDate> dates =
                List.of(dto.getSingleDate());

        return addScheduleForDates(dto, dates);
    }
    private Integer addScheduleForDateList(ScheduleAddDTO dto) {
        List<LocalDate> dates = dto.getMultiDates();

        return addScheduleForDates(dto, dates);
    }
    private Integer addScheduleForWeekdaysAndDateRange(ScheduleAddDTO dto) {
        List<Integer> weekDays = dto.getWeekdays();

        LocalDate startDate = dto.getStartDate();
        LocalDate endDate = dto.getEndDate();

        List<LocalDate> dates = new ArrayList<>();

        LocalDate date = startDate;

        while (!date.isAfter(endDate)) {

            int dayOfWeek =
                    date.getDayOfWeek().getValue();

            if (weekDays.contains(dayOfWeek)) {
                dates.add(date);
            }

            date = date.plusDays(1);
        }

        return addScheduleForDates(dto, dates);
    }

    private Integer addScheduleForDates(
            ScheduleAddDTO dto,
            List<LocalDate> dates
    ) {

        List<Integer> doctorIds = dto.getDoctorIds();

        Integer intervalMinute = dto.getIntervalMinute();

        LocalTime amStartTime = dto.getAmStartTime();
        LocalTime amEndTime = dto.getAmEndTime();
        LocalTime pmStartTime = dto.getPmStartTime();
        LocalTime pmEndTime = dto.getPmEndTime();

        Integer maxNumber = calculateMaxNumber(
                amStartTime,
                amEndTime,
                pmStartTime,
                pmEndTime,
                intervalMinute);

        List<DoctorSchedule> list = new ArrayList<>();

        for (LocalDate date : dates) {

            for (Integer doctorId : doctorIds) {

                DoctorSchedule schedule = new DoctorSchedule();

                schedule.setDoctorId(doctorId);
                schedule.setWorkDate(date);

                schedule.setAmStartTime(amStartTime);
                schedule.setAmEndTime(amEndTime);

                schedule.setPmStartTime(pmStartTime);
                schedule.setPmEndTime(pmEndTime);

                schedule.setIntervalMinute(intervalMinute);
                schedule.setMaxNumber(maxNumber);

                list.add(schedule);
            }
        }

        return batchInsertSchedules(list);
    }

    // 工具方法：根据时间间隔计算最大号源数量
    private Integer calculateMaxNumber(
            LocalTime amStart,
            LocalTime amEnd,
            LocalTime pmStart,
            LocalTime pmEnd,
            Integer intervalMinute
    ) {

        int total = 0;

        // 上午
        if (amStart != null && amEnd != null) {

            long minutes =
                    Duration.between(amStart, amEnd).toMinutes();

            total += (int) (minutes / intervalMinute);
        }

        // 下午
        if (pmStart != null && pmEnd != null) {

            long minutes =
                    Duration.between(pmStart, pmEnd).toMinutes();

            total += (int) (minutes / intervalMinute);
        }

        return total;
    }


    // 生成号源
    private Integer generateScheduleSlots(List<DoctorSchedule> schedules) {
        List<Integer> doctorIds =
                schedules.stream()
                        .map(DoctorSchedule::getDoctorId)
                        .distinct()
                        .toList();

        Map<Integer, DoctorExp> doctorMap =
                userMapper.selectDoctorByIds(doctorIds)
                        .stream()
                        .collect(Collectors.toMap(
                                DoctorExp::getDoctorId,
                                e -> e
                        ));

        List<ScheduleSlot> slotList = new ArrayList<>();

        for (DoctorSchedule schedule : schedules) {

            DoctorExp doctor =
                    doctorMap.get(schedule.getDoctorId());
            Integer fee = doctor.getFee();

            LocalDate visitDate = schedule.getWorkDate();

            // 上午
            generatePeriodSlots(
                    schedule,
                    "am",
                    schedule.getAmStartTime(),
                    schedule.getAmEndTime(),
                    visitDate,
                    fee,
                    slotList
            );

            // 下午
            generatePeriodSlots(
                    schedule,
                    "pm",
                    schedule.getPmStartTime(),
                    schedule.getPmEndTime(),
                    visitDate,
                    fee,
                    slotList
            );
        }

        return scheduleSlotMapper.batchInsertSlots(slotList);
    }

    private void generatePeriodSlots(
            DoctorSchedule schedule,
            String period,
            LocalTime start,
            LocalTime end,
            LocalDate visitDate,
            Integer fee,
            List<ScheduleSlot> slotList
    ) {

        if (start == null || end == null) {
            return;
        }

        int interval = schedule.getIntervalMinute();

        LocalTime time = start;

        int seq = 1;

        while (!time.plusMinutes(interval).isAfter(end)) {

            ScheduleSlot slot = new ScheduleSlot();

            slot.setScheduleId(schedule.getScheduleId());

            slot.setPeriod(period);

            slot.setVisitDate(visitDate);

            slot.setSeqNo(seq);

            String prefix = "am".equals(period) ? "A" : "P";
            String displayNo =
                    prefix + String.format("%02d", seq) + " " +
                            time.format(DateTimeFormatter.ofPattern("HH:mm"));
            slot.setDisplayNo(displayNo);

            slot.setFee(fee);

            slotList.add(slot);

            time = time.plusMinutes(interval);

            seq++;
        }
    }

    @Override
    @Transactional
    public ScheduleDeleteVO deleteSchedule(Integer scheduleId) {
        Integer result = doctorScheduleMapper.deleteScheduleById(scheduleId);

        Integer slotCount = scheduleSlotMapper.selectByScheduleId(scheduleId);
        if (slotCount > 0) {
            scheduleSlotMapper.deleteByScheduleId(scheduleId);
        }

        ScheduleDeleteVO vo = new ScheduleDeleteVO();
        vo.setScheduleResult(result);
        vo.setSlotResult(slotCount);
        return vo;
    }

    @Override
    @Transactional
    public ScheduleUpdateVO updateSchedule(ScheduleUpdateDTO dto) {
        if (dto.getAmStartTime() == null
        && dto.getAmEndTime() == null
        && dto.getPmStartTime() == null
        && dto.getPmEndTime() == null
        && dto.getIntervalMinute() == null
        && dto.getStatus() == null) {
            throw new BizException("没有需要更新的字段");
        }
        Integer scheduleId = dto.getScheduleId();

        // 1.更新排班信息
        // 历史排班不可更新
        DoctorSchedule schedule = doctorScheduleMapper.selectByScheduleId(scheduleId);
        if (schedule.getWorkDate().isBefore(LocalDate.now())) {
            throw new BizException("历史排班无法更新!");
        }

        // 计算最大号源
        LocalTime amStart = dto.getAmStartTime() != null ? dto.getAmStartTime() : schedule.getAmStartTime();
        LocalTime amEnd = dto.getAmEndTime() != null ? dto.getAmEndTime() : schedule.getAmEndTime();
        LocalTime pmStart = dto.getPmStartTime() != null ? dto.getPmStartTime() : schedule.getPmStartTime();
        LocalTime pmEnd = dto.getPmEndTime() != null ? dto.getPmEndTime() : schedule.getPmEndTime();
        Integer intervalMinute = dto.getIntervalMinute() != null ? dto.getIntervalMinute() : schedule.getIntervalMinute();

        Integer maxNumber = calculateMaxNumber(amStart, amEnd, pmStart, pmEnd, intervalMinute);
        dto.setMaxNumber(maxNumber);

        Integer scheduleResult = doctorScheduleMapper.updateSchedule(dto);

        // 是否需要更新号源信息
        boolean needReGenerate =
                changed(dto.getAmStartTime(), schedule.getAmStartTime())
                        || changed(dto.getAmEndTime(), schedule.getAmEndTime())
                        || changed(dto.getPmStartTime(), schedule.getPmStartTime())
                        || changed(dto.getPmEndTime(), schedule.getPmEndTime())
                        || changed(dto.getIntervalMinute(), schedule.getIntervalMinute());

        Integer deletedSlots = 0;
        Integer slotResult = 0;

        if (needReGenerate) {

            // 2.删除号源
            deletedSlots = scheduleSlotMapper.deleteByScheduleId(scheduleId);

            // 3.重新生成号源
            DoctorSchedule scheduleAfter = doctorScheduleMapper.selectByScheduleId(scheduleId);
            List<DoctorSchedule> list = List.of(scheduleAfter);
            slotResult = generateScheduleSlots(list);
        }

        // 4.返回更新结果
        ScheduleUpdateVO vo = new ScheduleUpdateVO();
        vo.setScheduleResult(scheduleResult);
        vo.setSlotDelete(deletedSlots);
        vo.setSlotResult(slotResult);

        return vo;
    }

    private boolean changed(Object newVal, Object oldVal) {
        return newVal != null && !newVal.equals(oldVal);
    }

    @Override
    public List<DoctorScheduleVO> getDoctorSchedule(Integer uid) {
        DoctorExp doctor = doctorMapper.getDoctorByUid(uid);
        if (doctor == null) {
            throw new BizException("医生信息出错");
        }
        if (doctor.getStatus() != 0) {
            throw new BizException("医生状态异常");
        }
        Integer doctorId = doctor.getDoctorId();

        return doctorScheduleMapper.listScheduleByDoctorId(doctorId);
    }
}
