package xyz.taka8rie.finalback.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.taka8rie.finalback.dao.AdminLoginRoleDAO;
import xyz.taka8rie.finalback.pojo.AdminLoginRole;
import xyz.taka8rie.finalback.pojo.AdminRole;
import xyz.taka8rie.finalback.pojo.User;


import java.sql.RowId;
import java.util.List;

@Service
public class AdminLoginRoleService {
    @Autowired
    AdminLoginRoleDAO adminLoginRoleDAO;

    public List<AdminLoginRole> listAllByLid(int lid) {
        return adminLoginRoleDAO.findAllByLid(lid);
    }

    @Transactional
    public void saveRoleChanges(int lid, List<AdminRole> roles) {
        adminLoginRoleDAO.deleteAllByLid(lid);
        roles.forEach(r->{
            AdminLoginRole lr=new AdminLoginRole();
            lr.setLid(lid);
            lr.setRid(r.getId());
            adminLoginRoleDAO.save(lr);
        });
    }

    //4.14晚 将注册页面的类型添加到adminLoginRole中
    public void saveTypeToRole(User user) {
        AdminLoginRole loginRole=new AdminLoginRole();

        loginRole.setLid(user.getId());
        loginRole.setRid(user.getAccountType());
        adminLoginRoleDAO.save(loginRole);
    }

}
