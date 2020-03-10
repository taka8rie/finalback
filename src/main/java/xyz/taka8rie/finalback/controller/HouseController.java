package xyz.taka8rie.finalback.controller;

import com.sun.org.apache.xpath.internal.compiler.Keywords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.annotations.MultiField;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import xyz.taka8rie.finalback.Service.HouseService;
import xyz.taka8rie.finalback.pojo.House;
import xyz.taka8rie.finalback.utils.StringUtils;

import java.io.File;
import java.io.IOException;
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

    @CrossOrigin   //这里的返回已修正，查询功能可用
    @GetMapping("api/type/{cid}/houses")
    public List<House> listByType(@PathVariable("cid") int cid) {
        System.out.println("进入了api/type/{cid}/houses方法");
        System.out.println("cid的值是: " + cid);
        if (cid != 0) {
            //应该返回的是某种类型的房屋，现在返回所有类型的房屋了
            // return houseService.list(cid);
            return houseService.list(cid);
        } else {
            //return list();//这里是否不对？改为null试试
            return list();
        }
    }

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

    @CrossOrigin
    @PostMapping(value = "api/covers")
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
            System.out.println("这是上传图片函数的imgURL: "+imgURL);
            return imgURL;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
