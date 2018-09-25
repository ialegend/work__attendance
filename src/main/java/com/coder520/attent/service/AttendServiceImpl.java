package com.coder520.attent.service;

import com.coder520.attent.dao.AttendMapper;
import com.coder520.attent.entity.Attend;
import com.coder520.attent.vo.QueryCondition;
import com.coder520.common.page.PageQueryBean;
import com.coder520.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("attendServiceImpl")
public class AttendServiceImpl implements AttendService{
    private Log log=LogFactory.getLog(AttendServiceImpl.class);

    private SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final int NOON_HOUR=12;
    private static final int NOON_MUNITE=00;
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
            if (todayRecord == null) {
                //打卡记录不存在
                if (today.compareTo(noon) <= 0) {
                    //上午打卡
                    attend.setAttendMorning(today);
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
}
