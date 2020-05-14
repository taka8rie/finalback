package xyz.taka8rie.finalback.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.taka8rie.finalback.dao.UserDAO;
import xyz.taka8rie.finalback.pojo.User;

import java.util.List;

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
       return userDAO.getByUsernameAndPasswordAndAccountType(username, password, accountType);
    }

    public void add(User user) {
        userDAO.save(user);
    }

    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    public List<User> findByUserId(int id) {
        return userDAO.findAllById(id);
    }

    //忘记密码函数,输入用户名和电话后可修改密码
//    public User alterForgetPassword(String tel, String username) {
//        return userDAO.getAllByTelAndUsername(tel, username);
//    }
    //忘记密码函数,输入口令和账户名后可修改密码
    public User alterForgetPassword(String token, String username) {
        return userDAO.getAllByForgetTokenAndUsername(token, username);
    }

    //获取所有用户
    public List<User> findAllUser() {
        return userDAO.findAll();
    }

    //4.26显示所有被冻结账户
    public List<User> findAllFreezed(int freezed) {
        return userDAO.findAllByFreeze(freezed);// 1为被冻结,0:正常账户
    }

    //5.1 后台显示用户图表
    public Integer userChart(int accountType) {
        return userDAO.countByAccountType(accountType);
    }
}
