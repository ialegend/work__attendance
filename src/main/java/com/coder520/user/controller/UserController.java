package com.coder520.user.controller;

import com.coder520.user.entity.user;
import com.coder520.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("user")
public class UserController {

    //@Resource(name = "userServiceImpl")
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userservice;
    @RequestMapping("/index")
    public String user() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        System.out.println("hello！！！！");
        user use=new user();
        use.setMobile("9283928");
        use.setPassword("pass");
        use.setRealName("real");
        use.setUsername("hello");
        //user use2=new user();
        //use2.setId(1L);
        //use2.setMobile("8888888");
        //use2.setPassword("passWORD");
        //use2.setRealName("realNAME");
        //use2.setUsername("userNAME");
        userservice.createUser(use);
          return "user";
      }
      @RequestMapping("/home")
       public String home(){
        return "home";
      }

      @RequestMapping("/userinfo")
      @ResponseBody
       public user  getUser(HttpSession session){

         user user= (com.coder520.user.entity.user) session.getAttribute("userinfo");
         return user;
      }

      @RequestMapping("logout")

      public String logout(HttpSession  session){
           session.invalidate();
           return "login";
      }
}
