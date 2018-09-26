package com.coder520.attent.service;

import com.coder520.attent.entity.Attend;
import com.coder520.attent.vo.QueryCondition;
import com.coder520.common.page.PageQueryBean;

public interface AttendService {
   void signAttend(Attend attend);

    PageQueryBean listAttend(QueryCondition condition);

    void checkAttend();
}
