package com.coder520.attent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("attend")
public class AttendController {
   @RequestMapping
    public String toAttend(){
       return "attend";
   }
}
