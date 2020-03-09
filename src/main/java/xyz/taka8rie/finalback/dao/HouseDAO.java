package xyz.taka8rie.finalback.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.taka8rie.finalback.pojo.House;
import xyz.taka8rie.finalback.pojo.User;

import java.util.List;

public interface HouseDAO extends JpaRepository<House,Integer> {
    // House findByHouseType(String houseType);
    List<House> findAllByHouseType(int houseType);
}
