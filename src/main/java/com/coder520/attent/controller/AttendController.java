package com.coder520.attent.controller;

import com.coder520.attent.entity.Attend;
import com.coder520.attent.service.AttendService;
import com.coder520.attent.vo.QueryCondition;
import com.coder520.common.page.PageQueryBean;
import com.coder520.user.entity.user;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("attend")
public class AttendController {
    @Resource(name = "attendServiceImpl")
    private AttendService attendService;
//    @Autowired
//    private AttendMapper attendMapper;
    @RequestMapping("index")
    public String toAttend(){

       return "attend";
   }

    @RequestMapping("/sign")
    @ResponseBody
   public String signAttend(@RequestBody Attend attend){
        System.out.println(attend.getAttendMorning()+"okokkoko");
        attendService.signAttend(attend);
        //attendMapper.insertSelective(attend);
        return "succ";
   }
/**考情数据分页查询*/
@RequestMapping("/signList")
@ResponseBody
   public PageQueryBean listAttend(QueryCondition condition){
       System.out.println(condition.getEndDate()+"okokoko");
       PageQueryBean result=attendService.listAttend(condition);
       return result;
   }

   @RequestMapping("/attendList")
   @ResponseBody
   public PageQueryBean listAttend(QueryCondition condition, HttpSession session){
       user user=(user) session.getAttribute("userinfo");
       String[] rangeDate= condition.getRangeDate().split("/");
       condition.setStartDate(rangeDate[0]);
       condition.setEndDate(rangeDate[1]);
       condition.setUserId(user.getId());
       PageQueryBean result=attendService.listAttend(condition);
       return result;
   }
}
