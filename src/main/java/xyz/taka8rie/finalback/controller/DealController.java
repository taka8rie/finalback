package xyz.taka8rie.finalback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.taka8rie.finalback.Service.DealService;
import xyz.taka8rie.finalback.pojo.Deal;

@RestController
public class DealController {
    @Autowired
    DealService dealService;

    @CrossOrigin
    @PostMapping ("api/dealadd")//这里本来用getMapping
    public Deal addOrUpdate(@RequestBody Deal deal) {
        System.out.println("进入了添加订单的方法");
        dealService.addOrUpdate(deal);
        return deal;
    }
}
