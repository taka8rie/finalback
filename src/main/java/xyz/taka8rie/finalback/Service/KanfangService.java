package xyz.taka8rie.finalback.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.taka8rie.finalback.dao.KanfangDAO;
import xyz.taka8rie.finalback.pojo.Kanfang;

import java.util.List;

@Service
public class KanfangService {
    @Autowired
    KanfangDAO kanfangDAO;

    public void addKanfang(Kanfang kanfang) {
        System.out.println("我到kanfangService了");
        kanfangDAO.save(kanfang);
        System.out.println("执行完了看房service的save操作");
    }


    public List<Kanfang> listByTenantNumberToKanFang(int tennantNumber) {
        return kanfangDAO.findAllByTenantNumber(tennantNumber);
    }

    public void deleteByShowNumber(Kanfang kanfang) {
        kanfangDAO.deleteById(kanfang.getShowNumber());
    }

    //列举出所有订单
    public List<Kanfang> listAllyuyue() {
        return kanfangDAO.findAll();
    }

    //租客反复添加预约看房请求
    public Kanfang listRepeatKanfang(int houseNumber,int tenantNumber) {
        return kanfangDAO.findAllByHouseNumberAndTenantNumber(houseNumber, tenantNumber);
    }

    //4.24 用于删除房屋后同时删除预约订单


}
