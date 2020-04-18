package xyz.taka8rie.finalback.controller;

import com.sun.org.apache.xpath.internal.compiler.Keywords;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.annotations.MultiField;
import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import xyz.taka8rie.finalback.Service.HouseService;
import xyz.taka8rie.finalback.Service.UserService;
import xyz.taka8rie.finalback.pojo.House;
import xyz.taka8rie.finalback.pojo.User;
import xyz.taka8rie.finalback.result.Result;
import xyz.taka8rie.finalback.result.ResultFactory;
import xyz.taka8rie.finalback.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@RestController
public class HouseController {
    @Autowired
    HouseService houseService;

    @Autowired
    UserService userService;

    //获取全部房屋,后台管理员用
    @GetMapping("api/houses")
    @CrossOrigin
    public List<House> list() {
        return houseService.list();
    }


    @CrossOrigin
    @PostMapping("api/houses")
    public House addOrUpdate(@RequestBody House house) {
        //将当前房主的ID作为房屋的ownerNumber.
        String username=SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.findByUsername(username);
        System.out.println("这里是添加修改房屋方法,获取到的用户类型为: "+user.getAccountType());
        System.out.println("这里是添加修改房屋方法,获取到的用户ID为: "+user.getId());
        if (user.getAccountType() != 1) {
            house.setOwnerNumber(user.getId());
        }
        houseService.addOrUpdate(house);
        System.out.println("添加修改成功");
        return house;
    }

    @CrossOrigin
    @PostMapping("api/delete")
    public void delete(@RequestBody House house) {
        System.out.println("进入了delete方法");
        System.out.println("house.getHouseNumber是: " + house.getHouseNumber());
        houseService.deleteById(house);
    }

    //此处返回所有的房屋类型,对应选项:"房子"
    @CrossOrigin   //这里的返回已修正，查询功能可用 //由int改为String 4.16
    @GetMapping("api/type/{cid}/houses")
    public List<House> listByType(@PathVariable("cid") String cid) {
        System.out.println("这里是HouseController的分类查询方法");
        System.out.println("进入了api/type/{cid}/houses方法");
        System.out.println("cid的值是: " + cid);
        if ("全部".equals(cid) ) {
            //应该返回的是某种类型的房屋，现在返回所有类型的房屋了
            // return houseService.list(cid);
//            return houseService.list(cid);
            System.out.println("进入cid 的if 方法");
            return houseService.listallHouse();
        }
            //return list();//这里是否不对？改为null试试
        return houseService.list(cid);

    }

//    //分类查询出没有被出租的房屋 4.15 已经在DealController里实现。
//    @CrossOrigin
//    @GetMapping("api/type/{cid}/notOrderHouses")
//    public List<House> listByNotOrderHouses(@PathVariable("cid") int cid) {
//        if (cid != 0) {
//            return houseService.TypeNotOrder(cid);
//        }
//        else {//当类型为"全部"时,返回未出租的房屋
//            return houseService.notOrderHouse(0);
//        }
//    }



    //这里的value="/api/search"多了一个/，但是前边却不用写也可以，原因？
    @CrossOrigin
    @GetMapping(value = "/api/search")
    public List<House> searchResult(@RequestParam("keywords") String keywords) {
        if ("".equals(keywords)) {
            return houseService.list();
        } else {
            return houseService.Search(keywords);
        }
    }

    //根据住址搜索出未被出租且通过审核的房屋(租客界面用)
    @CrossOrigin
    @GetMapping(value = "/api/searchIsOrder")
    public List<House> searchIsOrders(@RequestParam("keywords") String keywords) {
        if ("".equals(keywords)) {
//            return houseService.notOrderHouse(0);
            return houseService.checkAndNotOrder();
        }else {
            return houseService.SearchNotOrder(keywords);
        }
    }


    @CrossOrigin
    @PostMapping(value = "/api/covers")
    public String coverUpload(MultipartFile file) {
        String folder = "C:/temp/img";
        File imageFolder = new File(folder);
        File f = new File(imageFolder, StringUtils.getRandomString(6) + file.getOriginalFilename()
                .substring(file.getOriginalFilename().length() - 4));
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        try {
            file.transferTo(f);
            String imgURL = "http://localhost:8443/api/file/" + f.getName();
            System.out.println("这是上传图片函数的imgURL: " + imgURL);
            System.out.println();
            return imgURL;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    //这里没有校验!!
    @CrossOrigin
    @GetMapping("api/type/{value}/check")
    public List<House> listByCheck(@PathVariable("value") int value) {
        System.out.println("这里是查找未审核的方法");
        System.out.println("进入了api/type/{value}/check方法");
        System.out.println("value的值是: " + value);
        if (value == 0) {
            //查找未审核的房屋,value==0
            return houseService.CheckHouse(value);
        } else {
            //return list();//这里是否不对？改为null试试
            return list();
        }
    }


    //查找房主拥有的对应房屋

    @CrossOrigin
    @GetMapping("api/myhouses")
    public List<House> listOwnerHouses() {
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.findByUsername(username);
        System.out.println("---------------------------");
        return houseService.listByOwner(user.getId());
    }

    // 4/13 用于后台房屋的删除

    @PostMapping("api/admin/fangzhu/houses/delete")
    public Result AdminDeleteHouse(@RequestBody House house) {
        System.out.println("到达后台房屋函数");
        System.out.println("houseNumber是: " + house.getHouseNumber());
        if (houseService.adminDeleteByHouseNumber(house.getHouseNumber())) {
            return ResultFactory.buildSuccessResult("删除成功");
        }
        return ResultFactory.buildFailResult("删除失败");
    }

    //获取未出租的房屋,(租客用)
    @GetMapping("api/notOrderHouses")
    @CrossOrigin
    public List<House> notOrderHouse() {
        return houseService.checkAndNotOrder();
    }
}
