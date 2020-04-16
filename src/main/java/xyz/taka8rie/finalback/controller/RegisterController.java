package xyz.taka8rie.finalback.controller;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.hibernate.annotations.NaturalId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import xyz.taka8rie.finalback.Service.AdminLoginRoleService;
import xyz.taka8rie.finalback.Service.UserService;
import xyz.taka8rie.finalback.dao.AdminLoginRoleDAO;
import xyz.taka8rie.finalback.pojo.AdminLoginRole;
import xyz.taka8rie.finalback.result.Result;
import xyz.taka8rie.finalback.pojo.User;
import xyz.taka8rie.finalback.result.ResultFactory;

@RestController
public class RegisterController {
    @Autowired
    UserService userService;
    @Autowired
    AdminLoginRoleService adminLoginRoleService;

    @CrossOrigin //暂时去掉
    @PostMapping(value = "api/register")
//    @ResponseBody
    //这里的返回值Result为新增的result包里边的Result 4/4
    public Result register(@RequestBody User user) {
        String username=user.getUsername();
        String password=user.getPassword();
        System.out.println("register username : "+username);
        System.out.println("register password : "+password);
        username = HtmlUtils.htmlEscape(username);
//        int accountType=user.getAccountType();//由String转换回int 4/7
//        System.out.println("accountType is "+accountType);
        user.setUsername(username);
//        user.setAccountType(accountType);//由String转回int
        boolean exist = userService.isExist(username);
        if (exist) {
            String message = "该用户名已被占用";
            return ResultFactory.buildFailResult(message);
        }
        //生成盐,默认长度16位
        String salt=new SecureRandomNumberGenerator().nextBytes().toString();
        //设置hash的算法迭代次数
        int times=2;
        //得到的密码
        String encodedPassword = new SimpleHash("md5", password, salt, times).toString();
        user.setSalt(salt);
        user.setPassword(encodedPassword);
        userService.add(user);
//       accountType: 3为房屋出租者,2为租户，1为管理员
        if (user.getAccountType() !=1) {
           System.out.println("AccountType是: "+user.getAccountType());
            adminLoginRoleService.saveTypeToRole(user);
        }

        return ResultFactory.buildSuccessResult(user);
    }


//   原来可用的代码,并且Result类是import xyz.taka8rie.finalback.pojo.Result; 4/4
//    public Result register(@RequestBody User requestUser) {
//        userService.add(requestUser);
//        if (requestUser == null) {
//            System.out.println("注册失败");
//            return new Result(400);
//        }else{
//             System.out.println("注册成功");
//             System.out.println("注册的账户名为: " + requestUser.getUsername());
//             System.out.println("注册的密码为: "+requestUser.getPassword());
//             System.out.println("注册的类型为: "+requestUser.getAccountType());
//             return new Result(200);
//        }
//    }

}
