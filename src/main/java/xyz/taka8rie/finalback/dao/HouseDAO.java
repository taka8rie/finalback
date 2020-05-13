package xyz.taka8rie.finalback.dao;

import org.elasticsearch.client.license.LicensesStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    //4.23 尝试用list房屋号码查找房屋
    List<House> findAllByHouseNumberIn(List<Integer> houseNumber);

    //4.25 后台通过房屋号码来查找房屋,返回list
    @Query(value = "select * from houseinfo where house_number=?1", nativeQuery = true)
    List<House> adminSearchHouse(int houseNumber);

    //4.26 按房主编号来查找其归属的未审核的房屋
    List<House> findAllByOwnerNumberAndAdminCheck(int ownerNumber, int adminCheck);

    //4.29 移除订单时,将该房屋的出租情况更新为未出租,0为未出租
    @Transactional
    @Modifying
    @Query(value = "update houseinfo set is_order=0 where house_number=?1", nativeQuery = true)
    void updateHouseStatus(int houseNumber);

    //5.1 返回出租跟未出租房屋的个数,用于统计 0:未出租 1:已出租
    Integer countByIsOrder(int isOrder);

    //5.2 查看某个房屋的评价
    @Query(value = "select tenent_claim from houseinfo where house_number=?1", nativeQuery = true)
    String getHouseClaim(int houseNumber);
}
