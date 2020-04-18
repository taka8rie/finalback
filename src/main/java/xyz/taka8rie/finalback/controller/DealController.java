package xyz.taka8rie.finalback.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import xyz.taka8rie.finalback.Service.DealService;
import xyz.taka8rie.finalback.Service.HouseService;
import xyz.taka8rie.finalback.Service.UserService;
import xyz.taka8rie.finalback.dao.DealDAO;
import xyz.taka8rie.finalback.dao.HouseDAO;
import xyz.taka8rie.finalback.pojo.Deal;
import xyz.taka8rie.finalback.pojo.House;
import xyz.taka8rie.finalback.pojo.User;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.Calendar;

import java.util.List;

@RestController
public class DealController {
    @Autowired
    DealService dealService;

    //由订购引起的房屋订购状态isOrder的变化
    @Autowired
    HouseDAO houseDAO;

    //租客页面显示未出租的房屋 4.15
    @Autowired
    HouseService houseService;


    //尝试实现租客按类型展示功能
    @CrossOrigin
    public List<House> list() {
        return dealService.list();
    }

    @CrossOrigin
    @PostMapping("api/dealadd")//这里要跟前端对应，都是用post方法
    public Deal addOrUpdate(@RequestBody Deal deal) {
        System.out.println("进入了添加订单的方法");
        System.out.println("订单的房屋编号: " + deal.getHouseNumber());
        // 4.15 尝试不用输入租客编号,从数据库里边自动获取
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.findByUsername(username);
//        System.out.println("这是在添加订单的时候获取到的租客的username : "+user.getUsername());
//        System.out.println("这是在添加订单的时候获取到的租客的登录账号 : "+user.getId());
        deal.setTenantNumber(user.getId());
//        System.out.println("set id操作完成");
        //添加处理时间
        Date currentDate = new Date(System.currentTimeMillis());
//        System.out.println("获取到的现在的时间: "+currentDate);
        deal.setHandleTime(currentDate);
//        System.out.println("添加时间完成");
        dealService.addOrUpdate(deal);
        //对应的,如果添加了订单那么意味着该房屋被订购,所以需要修改当前的房屋状态isOrder,由0变1
        //将租客的评价写入对应的房屋中...
        int houseNumber = deal.getHouseNumber();
        House nowHouse = houseDAO.findAllByHouseNumber(houseNumber);
        nowHouse.setTenentClaim(deal.getClaim());//写入租客对该房屋的评价。
        nowHouse.setIsOrder(1);
        houseDAO.save(nowHouse);
        System.out.println("成功添加订单");
        return deal;
    }

    //尝试实现租客按类型展示未出租的房屋里边的分类功能,复制了HouseController部分的按类查询
    @CrossOrigin
    @GetMapping("api/type/{value}/deals")
    public List<House> listByType(@PathVariable("value") String value) {
        System.out.println("这里是DealController的分类查询方法");
        System.out.println("进入了api/type/{value}/deals方法");
        System.out.println("value的值是: " + value);
        //字符串的比较不应该用!=,应用equals
        if ("全部".equals(value) ) {
            //应该返回的是某种类型的房屋，现在返回所有类型的房屋了
            System.out.println("进入cid 的if 方法");
//            return houseService.notOrderHouse(0);
            return houseService.checkAndNotOrder();
        }
        //返回value类型且未被订购的房屋
//        return houseService.TypeNotOrder(value);
        return houseService.checkAndNotOrderByType(value);
    }


    // 4/12 找出已登录的租客所下的订单
    @Autowired
    UserService userService;

    @CrossOrigin
    @GetMapping("api/myorder")
    public List<Deal> listmyorder() {
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.findByUsername(username);
//        System.out.println("---------------------------");
//        System.out.println("username is "+ username);
//        System.out.println(username+" 的ID号码是 : "+user.getId());
//        System.out.println("list的长度是 :"+dealService.listMyOrder(user.getId()).size());
        return dealService.listMyOrder(user.getId());
    }

    //找出房主对应的订单
    @CrossOrigin
    @GetMapping("api/myrents")
    public List<Deal> listMyRent() {

        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.findByUsername(username);
        System.out.println("---------------------------");
        System.out.println("到达Myrents");
        System.out.println("username is " + username);
        System.out.println(username + " 的ID号码是 : " + user.getId());
        System.out.println("list的长度是 :" + dealService.listRent(user.getId()).size());
        return dealService.listRent(user.getId());
    }

    //列举出所有订单(admin用) 4.14
    @GetMapping("api/allorder")
    public List<Deal> listallorder() {
        return dealService.listOrder();
    }

    //删除订单
    @PostMapping("api/admindeleteorder")
    public void admindelete(@RequestBody Deal deal) {
        dealService.deleteDeal(deal);
    }



}
