package xyz.taka8rie.finalback.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.taka8rie.finalback.pojo.User;

import java.util.List;

public interface UserDAO extends JpaRepository<User,Integer> {
    User findByUsername(String username);
    User getByUsernameAndPasswordAndAccountType(String username, String password, int accountType);

    //    User findAllById(int id);
    List<User> findAllById(int id);
}
