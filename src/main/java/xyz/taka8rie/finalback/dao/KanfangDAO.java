package xyz.taka8rie.finalback.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.taka8rie.finalback.pojo.Kanfang;

import java.util.List;

public interface KanfangDAO extends JpaRepository<Kanfang, Integer> {
    List<Kanfang> findAllByTenantNumber(int tenantNumber);
    //列举出所有预约订单

}
