package com.coder520.common.task;

import com.coder520.attent.service.AttendService;

import javax.annotation.Resource;

public class AttendCheckTask {
    @Resource(name = "attendServiceImpl")
    private AttendService attendService;
    public void checkAttend(){
       //首先获取 今天没有打卡的人 给他插入打卡记录
       //如果有打卡记录 检查早晚打卡 看看考勤是否正常
        System.out.println("hello world");
        attendService.checkAttend();
    }
}
