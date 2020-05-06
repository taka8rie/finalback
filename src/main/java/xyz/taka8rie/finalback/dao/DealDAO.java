package xyz.taka8rie.finalback.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.taka8rie.finalback.pojo.Deal;
import xyz.taka8rie.finalback.pojo.House;

import javax.management.ValueExp;
import java.sql.Date;
import java.util.List;

public interface DealDAO extends JpaRepository<Deal,Integer> {
    List<Deal> findAllByStaffNumber(int staffNumber);

    List<Deal> findAllByTenantNumber(int tenantNumber);

    //登录角色为房主所对应的出租订单
    List<Deal> findAllByOwnerNumber(int OwnerNumber);

    //找出某一房屋对应的dealID
    @Query(value = "select deal_number from deal where house_number=?1", nativeQuery = true)
    Integer findDealNumberByHouseNumber(int houseNumber);

    //通过dealNumber查找对象
    Deal findAllByDealNumber(int DealNumber);

    //5.6 统计某个时间段的订单数量
    List<Deal> findAllByHandleTimeBetween(Date time1, Date time2);

}
