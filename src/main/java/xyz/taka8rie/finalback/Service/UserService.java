package xyz.taka8rie.finalback.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.taka8rie.finalback.dao.UserDAO;
import xyz.taka8rie.finalback.pojo.User;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public boolean isExist(String username) {
        User user = getByname(username);
        return user!=null;
    }

    public User getByname(String username) {
        return userDAO.findByUsername(username);
    }

    public User get(String username, String password,int accountType) {
        //return userDAO.getByUsernameAndPasswordAndAccounttype(username, password,accounttype);
        return userDAO.getByUsernameAndPasswordAndAccountType(username, password, accountType);
    }
    public void add(User user) {
        userDAO.save(user);
    }
}
