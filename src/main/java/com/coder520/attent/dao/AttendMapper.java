package com.coder520.attent.dao;

import com.coder520.attent.entity.Attend;
import com.coder520.attent.vo.QueryCondition;

import java.util.List;

public interface AttendMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Attend record);

    int insertSelective(Attend record);

    Attend selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Attend record);

    int updateByPrimaryKey(Attend record);

    Attend selectTodaySignRecord(Long userId);

    int countByCondition(QueryCondition condition);
    List<Attend> selectAttendPage(QueryCondition condition);
}