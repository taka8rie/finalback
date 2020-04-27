package xyz.taka8rie.finalback.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import sun.font.TrueTypeFont;
import xyz.taka8rie.finalback.pojo.User;

import javax.transaction.Transactional;
import javax.xml.bind.ValidationEvent;
import java.beans.Transient;
import java.util.List;

public interface UserDAO extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    User getByUsernameAndPasswordAndAccountType(String username, String password, int accountType);

    //    User findAllById(int id);
    List<User> findAllById(int id);

    //忘记密码,检验用户名和手机号是否跟数据库一致,是则返回该用户
    User getAllByTelAndUsername(String tel, String username);

    //对冻结属性进行更改,使用jpa框架,要在Repository层的方法中加上这两段注解：
    @Transactional
    @Modifying
    @Query(value = "update logininfo set freeze=1 where id=?1", nativeQuery = true)
    void letUserBeFreeze(int id);

    //解冻某个用户
    @Transactional
    @Modifying
    @Query(value = "update logininfo set freeze=0 where id=?1", nativeQuery = true)
    void letUserBeUnFreeze(int id);

    //查找所有被冻结账户
    List<User> findAllByFreeze(int freezed);
}
