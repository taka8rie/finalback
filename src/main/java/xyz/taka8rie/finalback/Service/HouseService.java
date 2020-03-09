package xyz.taka8rie.finalback.Service;

import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.taka8rie.finalback.dao.HouseDAO;
import xyz.taka8rie.finalback.pojo.House;

import java.util.List;

@Service
public class HouseService  {
    @Autowired
    HouseDAO houseDAO;

    public List<House> list() {
        return houseDAO.findAll();
    }
    //这里参数类型修改成对象了
    public void deleteById(House house) {
        houseDAO.deleteById(house.getHouseNumber());
    }

    public void addOrUpdate(House house) {
        houseDAO.save(house);
    }

    //是否type的类型要从int换为house对象？
    public List<House> list(int type) {
        return houseDAO.findAllByHouseType(type);
    }
}
