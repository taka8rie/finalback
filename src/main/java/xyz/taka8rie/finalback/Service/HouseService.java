package xyz.taka8rie.finalback.Service;

import org.omg.PortableServer.LIFESPAN_POLICY_ID;
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

    //是否type的类型要从int换为house对象？ ---不用也可以
    public List<House> list(int type) {
        return houseDAO.findAllByHouseType(type);
    }

    public List<House> Search(String keyword1) {
        return houseDAO.findAllByHouseAddrLike(keyword1);
    }

    //实现查找未审核的房屋功能
    public List<House> CheckHouse(int type) {
        return houseDAO.findAllByAdminCheck(type);
    }

    //尝试查找房主对应的房屋功能
    public List<House> listByOwner(int ownerNumber) {
        return houseDAO.findAllByOwnerNumber(ownerNumber);
    }

    //后台房主删除房屋
    public boolean adminDeleteByHouseNumber(int id) {
        try {
            houseDAO.deleteByHouseNumber(id);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    //查找未被订购的房屋,0代表未被订购,1为已被订购
    public List<House> notOrderHouse(int id) {
        return houseDAO.findAllByIsOrder(id);
    }

    //搜索未被订购的房屋
    public List<House> SearchNotOrder(String keyword) {
//        return houseDAO.findAllByHouseAddrLike(keyword);
        //0:未出租
        return houseDAO.findAllByIsOrderAndHouseAddrLike(0, keyword);
    }

    //按照类型来展示未被订购的房屋所包含的类型
    public List<House> TypeNotOrder(int houseType) {
        return houseDAO.findAllByIsOrderAndHouseType(0, houseType);
    }

}
