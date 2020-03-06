package xyz.taka8rie.finalback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;
import xyz.taka8rie.finalback.Service.UserService;
import xyz.taka8rie.finalback.pojo.Result;
import xyz.taka8rie.finalback.pojo.User;

import javax.xml.bind.ValidationEvent;
import java.util.Objects;

@Controller
public class LoginController {
    @Autowired
    UserService userService;
    @CrossOrigin
    @PostMapping(value = "api/login")
    @ResponseBody
    public Result login(@RequestBody User requestUser) {
       // 对 html 标签进行转义，防止 XSS 攻击
        String username=requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);
        System.out.println(username);
        System.out.println(requestUser.getPassword());
        System.out.println(requestUser.getAccountType());
        User user = userService.get(username, requestUser.getPassword(),requestUser.getAccountType());
        if (user==null) {
            String message = "账号密码错误";
            System.out.println("test");
            return new Result(400);
        } else {
            System.out.println("用户名："+user.getUsername());
            System.out.println("密码: "+user.getPassword());
            System.out.println("账户类型 ："+user.getAccountType());
            return new Result(200);
        }
    }
}
