package xyz.taka8rie.finalback.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sun.font.TrueTypeFont;
import xyz.taka8rie.finalback.pojo.Kanfang;

import javax.xml.bind.ValidationEvent;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public interface KanfangDAO extends JpaRepository<Kanfang, Integer> {
    //从数据库中读取某个用户的所预约过的所有房屋
    List<Kanfang> findAllByTenantNumber(int tenantNumber);

    //尝试拿来判断租客是否已经看过该房屋 4.17
    List<Kanfang> findAllByTenantNumberAndHouseNumber(int tenantNumber, int houseNumber);

    //当用户重复添加预约看房要求时,依据租客ID跟房屋名来搜索出结果。
    Kanfang findAllByHouseNumberAndTenantNumber(int houseNumber, int tenantNumber);

    //查找kanfang表中同一个用户对应的所有houseNumber 4.22
    @Query(value = "select house_number from scoreinfo where tenant_number=?1", nativeQuery = true)
    ArrayList<Integer> allHouseNumberDAO(int tenantNumber);

    //按照房屋来查找对应的showNumber 4.24
    @Query(value = "select show_number from scoreinfo where house_number=?1", nativeQuery = true)
    ArrayList<Integer> findShowNumberByHouseNumber(int houseNumber);
}
