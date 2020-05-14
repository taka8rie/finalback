package xyz.taka8rie.finalback.controller;

import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.json.JsonObjectDecoder;
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
    public Result register(@RequestBody String info) {
        User user = JSONObject.parseObject(info, User.class);
        String username=user.getUsername();
        String password=user.getPassword();

        username = HtmlUtils.htmlEscape(username);

        user.setUsername(username);

        //添加电话号码
        String originTel=user.getTel();
        user.setTel(originTel);


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

        //获取口令，并进行加密
        String originToken=user.getForgetToken();
        String encodedToken=new SimpleHash("md5",originToken,salt,times).toString();
        user.setForgetToken(encodedToken);

        user.setSalt(salt);
        user.setForgetSalt(salt);
        user.setPassword(encodedPassword);
        if (user.getAccountType() != 0){
            userService.add(user);
        }
        System.out.println("AccountType是: "+user.getAccountType());

        if (user.getAccountType() == 0 || user.getTel() == null) {
            String message = "请填写完信息";
            return ResultFactory.buildFailResult(message);
        }

//       accountType: 3为房屋出租者,2为租户，1为管理员
        if (user.getAccountType() !=1&&user.getAccountType()!=0) {
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


    //修改密码
    @CrossOrigin
    @PostMapping(value = "api/forgetpassword")
    public Result forGet(@RequestBody User user) {
        //设置hash的算法迭代次数
        int times=2;

        if (user.getUsername() == null||user.getPassword()==null) {
            String message = "请将信息填写完整";
            return ResultFactory.buildFailResult(message);
        }
        String username=user.getUsername();
        String newPassword=user.getPassword();
//        String tel=user.getTel();
        String forgetToken=user.getForgetToken();

        User forgetUser = userService.findByUsername(user.getUsername());
        String forgetUserSalt=forgetUser.getForgetSalt();
//        System.out.println("获取到该用户名数据库对应的盐(修改密码成功前的盐) "+forgetUserSalt);
        String tempToken=new SimpleHash("md5",forgetToken,forgetUserSalt,times).toString();

        User tempUser = userService.alterForgetPassword(tempToken, username);
//        User tempUser = userService.alterForgetPassword(tel, username);

        if (tempUser == null) {
            String message = "请检查用户名和手机号是否填写错误！";
            return ResultFactory.buildFailResult(message);
        }

        //生成盐,默认长度16位
        String salt=new SecureRandomNumberGenerator().nextBytes().toString();
//        System.out.println("新生成的盐:"+salt);

        //得到的密码
        String encodedPassword = new SimpleHash("md5", newPassword, salt, times).toString();
        tempUser.setSalt(salt);
//        System.out.println("存入的盐:"+salt);
        tempUser.setPassword(encodedPassword);
        userService.add(tempUser);
        return ResultFactory.buildSuccessResult("修改密码成功");
    }

}
