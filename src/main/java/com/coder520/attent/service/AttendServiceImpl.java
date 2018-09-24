package com.coder520.attent.service;

import com.coder520.attent.dao.AttendMapper;
import com.coder520.attent.entity.Attend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
@Service("attendServiceImpl")
public class AttendServiceImpl implements AttendService{
    private Log log=LogFactory.getLog(AttendServiceImpl.class);
    @Autowired
    private AttendMapper attendMapper;
    @Override
    public void signAttend(Attend attend) {
        try{
            attendMapper.insertSelective(attend);
        }catch(Exception e){
           log.error("用户签到异常",e);
           throw e;
        }

    }
}
