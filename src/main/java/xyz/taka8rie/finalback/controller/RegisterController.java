package xyz.taka8rie.finalback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.taka8rie.finalback.Service.UserService;
import xyz.taka8rie.finalback.pojo.Result;
import xyz.taka8rie.finalback.pojo.User;

@Controller
public class RegisterController {
    @Autowired
    UserService userService;

    @CrossOrigin
    @PostMapping(value = "api/register")
    @ResponseBody
    public Result register(@RequestBody User requestUser) {
        userService.add(requestUser);
        if (requestUser == null) {
            System.out.println("注册失败");
            return new Result(400);
        }else{
             System.out.println("注册成功");
             System.out.println("注册的账户名为: " + requestUser.getUsername());
             System.out.println("注册的密码为: "+requestUser.getPassword());
             System.out.println("注册的类型为: "+requestUser.getAccountType());
             return new Result(200);
        }
    }

}
