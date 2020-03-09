package xyz.taka8rie.finalback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.taka8rie.finalback.Service.HouseService;
import xyz.taka8rie.finalback.pojo.House;

import java.lang.reflect.Type;
import java.util.List;

@RestController
public class HouseController {
    @Autowired
    HouseService houseService;
    @GetMapping("api/houses")
    @CrossOrigin
    public List<House> list() {
        return houseService.list();
    }
    @CrossOrigin
    @PostMapping("api/houses")
    public House addOrUpdate(@RequestBody House house) {
        System.out.println("这里是添加房屋方法");
        houseService.addOrUpdate(house);
        return house;
    }
    @CrossOrigin
    @PostMapping("api/delete")
    public void delete(@RequestBody House house) {
        System.out.println("进入了delete方法");
        houseService.deleteById(house);
    }
    @CrossOrigin   //这里的返回未修正
    @GetMapping("api/type/{cid}/houses")
    public List<House> listByType(@PathVariable("cid") int cid) {
        System.out.println("进入了api/type/{cid}/houses方法");
        System.out.println("cid的值是: "+cid );
        if (cid != 0 ) {
            //应该返回的是某种类型的房屋，现在返回所有类型的房屋了
           // return houseService.list(cid);
            return houseService.list(cid);
        }
        else {
            //return list();//这里是否不对？改为null试试
            return list() ;
        }
    }
}
