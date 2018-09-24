package com.coder520.attent.controller;

import com.coder520.attent.entity.Attend;
import com.coder520.attent.service.AttendService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("attend")
public class AttendController {
    @Resource(name = "attendServiceImpl")
    private AttendService attendService;
//    @Autowired
//    private AttendMapper attendMapper;
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
}
