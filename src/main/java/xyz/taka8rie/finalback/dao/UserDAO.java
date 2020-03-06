package xyz.taka8rie.finalback.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.taka8rie.finalback.pojo.User;

public interface UserDAO extends JpaRepository<User,Integer> {
    User findByUsername(String username);

    //User getByUsernameAndPasswordAndAccounttype(String username, String password,int accounttype);
    User getByUsernameAndPasswordAndAccountType(String username, String password, int accountType);
}
