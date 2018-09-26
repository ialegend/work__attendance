package com.coder520.attent.service;

import com.coder520.attent.dao.AttendMapper;
import com.coder520.attent.entity.Attend;
import com.coder520.attent.vo.QueryCondition;
import com.coder520.common.page.PageQueryBean;
import com.coder520.common.utils.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("attendServiceImpl")
public class AttendServiceImpl implements AttendService{
    private Log log=LogFactory.getLog(AttendServiceImpl.class);

    private SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final int NOON_HOUR=12;
    private static final int NOON_MUNITE=00;
    private static final int MORNING_HOUR=9;
    private static final int MORMING_MUNITE=00;
    private static final int EVENING_HOUR=18;
    private static final int EVENING_MUNITE=30;
    //缺勤一整天
    private static final Integer ABSENCE_DAY=480;
    //考勤异常状态
    private static final Byte ATTEND_STATUS_ABNORMAL=2;
    private static final Byte ATTEND_STATUS_NORMAL=1;

    @Autowired
    private AttendMapper attendMapper;

    @Override
    public void signAttend(Attend attend) {
        try {
            Date today = new Date();
            attend.setAttendDate(new Date());
            attend.setAttendWeek((byte) DateUtils.getTodayWeek());
            Attend todayRecord = attendMapper.selectTodaySignRecord(attend.getUserId());
            Date noon = DateUtils.getDate(NOON_HOUR, NOON_MUNITE);
            Date morningAttend=DateUtils.getDate(MORNING_HOUR,MORMING_MUNITE);
            if (todayRecord == null) {
                //打卡记录不存在
                if (today.compareTo(noon) <= 0) {
                    //上午打卡
                    attend.setAttendMorning(today);
                    //计算打卡时间是不是迟到
                    if(today.compareTo(morningAttend)>0){
                        //大于九点半迟到
                    attend.setAttendStatus(ATTEND_STATUS_ABNORMAL);
                    attend.setAbsence(DateUtils.getMunite(morningAttend,today));
                    }
                } else {
                    //晚上打卡
                    attend.setAttendEvening(today);
                }
                attendMapper.insertSelective(attend);
            } else {
                if (today.compareTo(noon) <= 0) {
                    return;
                } else {
                    todayRecord.setAttendEvening(today);
                    Date eveningAttend=DateUtils.getDate(EVENING_HOUR,EVENING_MUNITE);
                    if(today.compareTo(eveningAttend)<0){
                        //早于下午六点半 早退
                        todayRecord.setAttendStatus(ATTEND_STATUS_ABNORMAL);
                        todayRecord.setAbsence(DateUtils.getMunite(today,eveningAttend));
                    }else{
                        todayRecord.setAttendStatus(ATTEND_STATUS_ABNORMAL);
                        todayRecord.setAbsence(0);
                    }
                    attendMapper.updateByPrimaryKeySelective(todayRecord);
                }
            }

            //中午十二点之前打卡 都算早晨打卡 如果9:30之后 直接异常
            //十二点之后都算下午打卡
            //下午打卡检查与上午打卡的
            // attendMapper.insertSelective(attend);
        } catch (Exception e) {
            log.error("用户签到异常", e);
            throw e;
        }

    }

    @Override
    public PageQueryBean listAttend(QueryCondition condition) {
        //根据条件查询 count记录数目
        //如果有记录  才去查询分页数据 没有相关记录数目 没必要去查询分页数据
        int count=attendMapper.countByCondition(condition);
        PageQueryBean pageResult=new PageQueryBean();
        if(count>0){
            pageResult.setCurrentPage(condition.getCurrentPage());
            pageResult.setPageSize(condition.getPageSize());
            pageResult.setTotalRows(count);
            List<Attend> attendList=attendMapper.selectAttendPage(condition);
            pageResult.setItems(attendList);
        }
        //如果有记录 才去查询分页数据 没有相关记录数目 没必要去查分页数据
        return pageResult;
    }

    @Override
    public void checkAttend() {
      //查询缺勤用户id 插入打卡记录 并且设置为异常 缺勤480分钟
        List<Long> userIdList=attendMapper.selectTodayAbsence();
        if(CollectionUtils.isNotEmpty(userIdList)){
            List<Attend> attendList=new ArrayList<>();
            for(Long userId:userIdList){
                Attend attend=new Attend();
                attend.setUserId(userId);
                attend.setAttendDate(new Date());
                attend.setAttendWeek((byte)DateUtils.getTodayWeek());
                attend.setAbsence(ABSENCE_DAY);
                attend.setAttendStatus(ATTEND_STATUS_ABNORMAL);
                attendList.add(attend);
            }
            attendMapper.batchInsert(attendList);
        }
        //检查晚上打卡 将下班未打卡记录设置为异常
        List<Attend> absenceList=attendMapper.selectTodayEveningAbsence();
        if(CollectionUtils.isNotEmpty(absenceList)){
            for(Attend attend:absenceList){
                attend.setAbsence(ABSENCE_DAY);
                attend.setAttendStatus(ATTEND_STATUS_ABNORMAL);
                attendMapper.updateByPrimaryKeySelective(attend);
            }
        }
    }
}
