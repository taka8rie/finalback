package xyz.taka8rie.finalback.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.taka8rie.finalback.pojo.Deal;

import java.util.List;

public interface DealDAO extends JpaRepository<Deal,Integer> {
    List<Deal> findAllByStaffNumber(int staffNumber);
}
