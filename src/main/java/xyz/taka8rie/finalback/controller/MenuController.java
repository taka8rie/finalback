package xyz.taka8rie.finalback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.taka8rie.finalback.Service.AdminMenuService;
import xyz.taka8rie.finalback.pojo.AdminMenu;
import xyz.taka8rie.finalback.result.Result;
import xyz.taka8rie.finalback.result.ResultFactory;

import java.util.List;


@RestController
public class MenuController {
    @Autowired
    AdminMenuService adminMenuService;



    @GetMapping("/api/menu")
    public Result menu() {
        return ResultFactory.buildSuccessResult(adminMenuService.getMenuByCurrentUser());
    }

//    @GetMapping("/api/menu")
//    public List<AdminMenu> menus() {
//        return adminMenuService.getMenuByCurrentUser();
//    }
}
