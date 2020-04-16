package xyz.taka8rie.finalback.dao;

import org.elasticsearch.client.license.LicensesStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import xyz.taka8rie.finalback.pojo.House;
import xyz.taka8rie.finalback.pojo.User;

import java.security.PublicKey;
import java.util.List;

public interface HouseDAO extends JpaRepository<House,Integer> {
    List<House> findAllByHouseType(int houseType);
    List<House> findAllByHouseAddrLike(String keywords);
    //查找未审核房屋
    List<House> findAllByAdminCheck(int adminCheck);

    //查找房主所对应的房屋
    List<House> findAllByOwnerNumber(int ownerNumber);

    //查找未被订购的房屋
    List<House> findAllByIsOrder(int isOrder);

    //用于后台删除房屋
    @Transactional
    public void deleteByHouseNumber(int houseNumber);

    //根据房屋编号找到房屋,用于下订单时改变房屋的isOrder状态
    House findAllByHouseNumber(int houseNumber);

    //在未出租的房屋里边搜索想要的房屋
    List<House> findAllByIsOrderAndHouseAddrLike(int id, String keyword);

    //在未出租的房屋里边展示不同的类型
    List<House> findAllByIsOrderAndHouseType(int id, int houseType);
}
