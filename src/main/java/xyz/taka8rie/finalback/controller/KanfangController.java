package xyz.taka8rie.finalback.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.taka8rie.finalback.Service.KanfangService;
import xyz.taka8rie.finalback.Service.UserService;
import xyz.taka8rie.finalback.dao.KanfangDAO;
import xyz.taka8rie.finalback.pojo.Kanfang;
import xyz.taka8rie.finalback.pojo.User;

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
    public Kanfang add(@RequestBody Kanfang kanfang) {
        System.out.println("进入看房controller");
        System.out.println("看房的时间是: " + kanfang.getSeeTime());
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.findByUsername(username);
        kanfang.setTenantNumber(user.getId());
        kanfangService.addKanfang(kanfang);
        return kanfang;
    }

    //后台租客查看自己的预约订单
    @CrossOrigin
    @GetMapping("api/myyuyues")
    public List<Kanfang> zukeYuyue() {
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.findByUsername(username);
        return kanfangService.listByTenantNumberToKanFang(user.getId());
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
