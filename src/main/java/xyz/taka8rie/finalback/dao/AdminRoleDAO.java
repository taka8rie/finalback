package xyz.taka8rie.finalback.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.taka8rie.finalback.pojo.AdminRole;

public interface AdminRoleDAO extends JpaRepository<AdminRole,Integer> {
    AdminRole findById(int id);
}
