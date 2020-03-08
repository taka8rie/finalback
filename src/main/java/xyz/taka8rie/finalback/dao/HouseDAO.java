package xyz.taka8rie.finalback.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.taka8rie.finalback.pojo.House;
import xyz.taka8rie.finalback.pojo.User;

public interface HouseDAO extends JpaRepository<House,Integer> {
    // House findByHouseType(String houseType);

}
