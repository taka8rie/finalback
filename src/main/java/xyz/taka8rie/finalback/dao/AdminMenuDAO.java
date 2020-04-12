package xyz.taka8rie.finalback.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.taka8rie.finalback.pojo.AdminMenu;

import java.util.List;

public interface AdminMenuDAO extends JpaRepository<AdminMenu,Integer> {
    AdminMenu findById(int id);

    List<AdminMenu> findAllByParentId(int ParentId);
}
