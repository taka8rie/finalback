package xyz.taka8rie.finalback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import xyz.taka8rie.finalback.Service.HouseService;
import xyz.taka8rie.finalback.pojo.House;

import java.util.List;

@Controller
public class HouseController {
    @Autowired
    HouseService houseService;
    @GetMapping("api/houses")
    public List<House> list() {
        return houseService.list();
    }
}
