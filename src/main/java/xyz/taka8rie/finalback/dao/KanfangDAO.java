package xyz.taka8rie.finalback.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.taka8rie.finalback.pojo.Kanfang;

import java.util.List;

public interface KanfangDAO extends JpaRepository<Kanfang, Integer> {
    List<Kanfang> findAllByTenantNumber(int tenantNumber);

    //尝试拿来判断租客是否已经看过该房屋 4.17
    List<Kanfang> findAllByTenantNumberAndHouseNumber(int tenantNumber, int houseNumber);

    //当用户重复添加预约看房要求时,依据租客ID跟房屋名来搜索出结果。
    Kanfang findAllByHouseNumberAndTenantNumber(int houseNumber, int tenantNumber);
}
