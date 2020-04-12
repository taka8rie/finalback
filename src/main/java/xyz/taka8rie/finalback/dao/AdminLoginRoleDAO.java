package xyz.taka8rie.finalback.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.taka8rie.finalback.pojo.AdminLoginRole;

import java.util.List;

public interface AdminLoginRoleDAO extends JpaRepository<AdminLoginRole,Integer> {
    List<AdminLoginRole>findAllByLid(int lid);

    void deleteAllByLid(int lid);
}
