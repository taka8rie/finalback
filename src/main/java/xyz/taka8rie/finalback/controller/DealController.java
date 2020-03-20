package xyz.taka8rie.finalback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.taka8rie.finalback.Service.DealService;
import xyz.taka8rie.finalback.pojo.Deal;

@RestController
public class DealController {
    @Autowired
    DealService dealService;

    @CrossOrigin
    @GetMapping("api/dealadd")
    public Deal addOrUpdate(@RequestBody Deal deal) {
        System.out.println("进入了添加订单的方法");
        dealService.addOrUpdate(deal);
        return deal;
    }
}
