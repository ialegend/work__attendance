package com.coder520.login;

import com.coder520.common.utils.SecurityUtils;
import com.coder520.user.entity.user;
import com.coder520.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("login")
public class LoginController {

    //@Resource(name = "userServiceImpl")
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @RequestMapping("index")
    public String login(){
        return "login";
    }

    @RequestMapping("/check")
    @ResponseBody
    public String checkLogin(HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
       String username =request.getParameter("username");
       String pwd=request.getParameter("password");
       String flag=null;
        //查询数据库 如果查到数据 调用MD5对比密码
        user user=userService.findUserByUserName(username);
        if(user!=null){
            if(SecurityUtils.checkPassword(pwd,user.getPassword())){
                //设置session
                request.getSession().setAttribute("userinfo",user);
                flag="login_succ";
            }
        }else{
            //校验失败 返回校验失败signal
                flag="login_fail";
        }
    return flag;

    }

    @RequestMapping("/register")
    @ResponseBody
    public String register(@RequestBody user user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        userService.createUser(user);
        return "succ";
    }
}
