package xyz.taka8rie.finalback.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.taka8rie.finalback.pojo.Deal;
import xyz.taka8rie.finalback.pojo.House;

import java.util.List;

public interface DealDAO extends JpaRepository<Deal,Integer> {
    List<Deal> findAllByStaffNumber(int staffNumber);

    List<Deal> findAllByTenantNumber(int tenantNumber);

    //登录角色为房主所对应的出租订单
    List<Deal> findAllByOwnerNumber(int OwnerNumber);


}
