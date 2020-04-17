package xyz.taka8rie.finalback.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerResponse;
import xyz.taka8rie.finalback.Service.KanfangService;
import xyz.taka8rie.finalback.Service.UserService;
import xyz.taka8rie.finalback.dao.KanfangDAO;
import xyz.taka8rie.finalback.pojo.Kanfang;
import xyz.taka8rie.finalback.pojo.User;
import xyz.taka8rie.finalback.result.Result;
import xyz.taka8rie.finalback.result.ResultFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RestController
public class KanfangController {
    @Autowired
    KanfangService kanfangService;
    @Autowired
    UserService userService;
    @Autowired
    KanfangDAO kanfangDAO;


    @CrossOrigin
    @PostMapping("api/kanfangadd")
    public Result add(@RequestBody Kanfang kanfang) {
        System.out.println("进入看房controller");
        System.out.println("看房的时间是: " + kanfang.getSeeTime());
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.findByUsername(username);

        if (kanfangService.listRepeatKanfang(kanfang.getHouseNumber(), user.getId()) != null) {
            System.out.println("说明scoreinfo表中已经有了预约看房的记录了");
            return ResultFactory.buildFailResult("已经添加过改预约订单了");
        }

        kanfang.setTenantNumber(user.getId());
        kanfangService.addKanfang(kanfang);
        return ResultFactory.buildFailResult("添加成功");
    }

    //后台租客查看自己的预约订单
    @CrossOrigin
    @GetMapping("api/myyuyues")
    public List<Kanfang> zukeYuyue() {
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.findByUsername(username);
        return kanfangService.listByTenantNumberToKanFang(user.getId());
    }

    //后台管理员查看所有预约订单
    @CrossOrigin
    @GetMapping("api/allyuyues")
    public List<Kanfang> allYuyue() {
        System.out.println("进入admin查看所有预约订单的方法");
        return kanfangService.listAllyuyue();
    }




    //后台删除预约订单
    @CrossOrigin
    @PostMapping("api/deleteyuyue")
    public void deleteYuyue(@RequestBody Kanfang kanfang) {
        kanfangService.deleteByShowNumber(kanfang);
    }

    //后台修改预约订单
    @CrossOrigin
    @PostMapping("api/edityuyue")
    public Kanfang editYuyue(@RequestBody Kanfang kanfang) {
        String username=SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.findByUsername(username);
        System.out.println("租客的id是 "+user.getId());
        System.out.println("预约看房的房屋的编号是 "+kanfang.getHouseNumber());
        kanfang.setTenantNumber(user.getId());
        kanfangService.addKanfang(kanfang);
        return kanfang;
    }
}
