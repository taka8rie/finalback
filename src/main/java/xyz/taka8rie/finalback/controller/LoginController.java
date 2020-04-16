package xyz.taka8rie.finalback.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import xyz.taka8rie.finalback.Service.UserService;
import xyz.taka8rie.finalback.dao.UserDAO;
import xyz.taka8rie.finalback.pojo.User;
import xyz.taka8rie.finalback.result.Result;
import xyz.taka8rie.finalback.result.ResultFactory;

import javax.persistence.Id;
import javax.servlet.http.HttpSession;
import javax.xml.bind.ValidationEvent;
import java.util.List;
import java.util.Objects;

@RestController
public class LoginController {
    @Autowired
    UserService userService;

    @Autowired
    UserDAO userDAO;

    @CrossOrigin
    @PostMapping(value = "api/login")
    //    @ResponseBody

    public Result login(@RequestBody User requestUser) {
        String username = requestUser.getUsername();
        Subject subject = SecurityUtils.getSubject();
        //将类型转换为字符串
//        int host=requestUser.getAccountType();
//        System.out.println("登录时的账户类型为: "+host);//没有对账户类型进行检验
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, requestUser.getPassword());
        try {
            subject.login(usernamePasswordToken);
            return ResultFactory.buildSuccessResult(username);
        } catch (AuthenticationException e) {
            String message = "用户名或密码错误";
            return ResultFactory.buildFailResult(message);
        }
    }

    //    @CrossOrigin
    @GetMapping(value = "api/logout")
//    @ResponseBody
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        String message = "已登出";
        return ResultFactory.buildSuccessResult(message);
    }

    @GetMapping(value = "api/authentication")
    public String authentication() {
        return "身份验证成功";
    }

    //    原来的可用代码
//    public Result login(@RequestBody User requestUser, HttpSession session) {
//       // 对 html 标签进行转义，防止 XSS 攻击
//        String username=requestUser.getUsername();
//        username = HtmlUtils.htmlEscape(username);
//        User user = userService.get(username, requestUser.getPassword(),requestUser.getAccountType());
//        if (user==null) {
////            String message = "账号密码错误或权限不匹配";
////            System.out.println(message);
//            return new Result(400);
//        } else {
////            System.out.println("用户名："+user.getUsername());
////            System.out.println("密码: "+user.getPassword());
////            System.out.println("账户类型 ："+user.getAccountType());
////            session.setAttribute("user",user);//这里为拦截器
//            return new Result(200);
//        }
//    }
    @CrossOrigin
    @GetMapping(value = "api/alluserSearch")
    public List<User> allUser(@RequestParam("keywords") int keywords) {
//        if ("".equals(keywords)) {
//            return null;
//        }else{
//            return userService.findByUserId(keywords);
//        }
        return userService.findByUserId(keywords);
    }
}
