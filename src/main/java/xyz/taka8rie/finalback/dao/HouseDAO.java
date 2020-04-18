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
    List<House> findAllByHouseTypeLike(String houseType);//由int改为String 4.16
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

    //在未出租的房屋里边展示不同的类型 //由int改为String 4.16
    List<House> findAllByIsOrderAndHouseTypeLike(int id, String houseType);

    //显示已经审查过并且没有被下订单的所有房屋
    List<House> findAllByIsOrderAndAdminCheck(int id, int adminCheck);

    //显示已经审查过并且没有被下订单,根据类型来显示的房屋
    List<House> findAllByIsOrderAndAdminCheckAndHouseTypeLike(int isOrderId, int adminCheck, String houseType);

    //显示已通过审核且没有被下订单,根据房屋地址来显示的房屋
    List<House> findAllByIsOrderAndAdminCheckAndHouseAddrLike(int isOrderId, int adminCheck, String houseAddr);
}
