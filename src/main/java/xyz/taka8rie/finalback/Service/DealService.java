package xyz.taka8rie.finalback.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.taka8rie.finalback.dao.DealDAO;
import xyz.taka8rie.finalback.dao.HouseDAO;
import xyz.taka8rie.finalback.pojo.Deal;
import xyz.taka8rie.finalback.pojo.House;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DealService {
    @Autowired
    DealDAO dealDAO;
    @Autowired
    HouseDAO houseDAO;//尝试实现租客按类型展示功能

    public void addOrUpdate(Deal deal) {
        dealDAO.save(deal);
    }

    //实现租客按类型展示功能
    public List<House> list(String type) {
        return houseDAO.findAllByHouseTypeLike(type);
    }
    //实现租客按类型展示功能
    public List<House> list() {
        return houseDAO.findAll();
    }

    //按租客账号来列举出对应订单
    public List<Deal> listMyOrder(int id) {
        return dealDAO.findAllByTenantNumber(id);
    }

    // 4/12尝试列举出所有订单(测试)
    public List<Deal> listOrder() {
        return dealDAO.findAll();
    }

    //按照房主账号列举对应订单
    public List<Deal> listRent(int ownerNumber) {
        return dealDAO.findAllByOwnerNumber(ownerNumber);
    }

    // 删除订单 4.14
    public void deleteDeal(Deal deal) {
        dealDAO.deleteById(deal.getDealNumber());
    }
}
