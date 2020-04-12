package xyz.taka8rie.finalback.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.taka8rie.finalback.dao.AdminRoleDAO;
import xyz.taka8rie.finalback.pojo.AdminRole;

import java.util.List;


@Service
public class AdminRoleService {
    @Autowired
    AdminRoleDAO adminRoleDAO;
    @Autowired
    UserService userService;
    @Autowired
    AdminLoginRoleService adminLoginRoleService;
    @Autowired
    AdminMenuService adminMenuService;

//    public List<AdminRole> list() {
//        List<AdminRole> roles=adminRoleDAO.findAll();
//
//    }
}
