package xyz.taka8rie.finalback.Service;

import net.bytebuddy.agent.builder.AgentBuilder;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.taka8rie.finalback.dao.AdminMenuDAO;
import xyz.taka8rie.finalback.pojo.AdminLoginRole;
import xyz.taka8rie.finalback.pojo.AdminMenu;
import xyz.taka8rie.finalback.pojo.AdminRoleMenu;
import xyz.taka8rie.finalback.pojo.User;

import java.security.Security;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AdminMenuService {
    @Autowired
    AdminMenuDAO adminMenuDAO;
    @Autowired
    UserService userService;
    @Autowired
    AdminLoginRoleService adminLoginRoleService;
    @Autowired
    AdminRoleMenuService adminRoleMenuService;

    public List<AdminMenu> getAllByParentId(int parentId) {
        return  adminMenuDAO.findAllByParentId(parentId);
    }

//    public List<AdminMenu> getMenusByCurrentUser() {
//        String username = SecurityUtils.getSubject().getPrincipal().toString();
//        User user = userService.findByUsername(username);
//
//        List<Integer> rids=adminLoginRoleService.listAllByLid(user.getId()).stream().map(AdminLoginRole::getRid).collect(Collectors.toList());
//
//        List<Integer> menuIds = adminRoleMenuService.findAllByRid(rids).stream().map(AdminRoleMenu::getMid).collect(Collectors.toList());
//
//    }

    public List<AdminMenu> getMenuByCurrentUser() {
        //从数据库中获取当前用户
        String username= SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.findByUsername(username);
        //获取当前用户对应的所有角色的id列表
        List<AdminLoginRole> loginRoleList = adminLoginRoleService.listAllByLid(user.getId());
//        System.out.println("查找出来的loginRoleList是: "+loginRoleList);
        List<AdminMenu> menus=new ArrayList<>();

        loginRoleList.forEach(lr->{
            List<AdminRoleMenu> rms = adminRoleMenuService.findAllByRid(lr.getRid());
//            System.out.println("查找出来的adminRoleMenu是: "+rms);
            rms.forEach(rm->{
                AdminMenu adminMenu = adminMenuDAO.findById(rm.getMid());
                // System.out.println("得到的adminMenu是: "+adminMenu);
                //防止多角色状态下菜单重复
                if (!menus.contains(adminMenu)) {
                    //  System.out.println("要添加的Menu是: "+adminMenu);
                    menus.add(adminMenu);
                }
            });
        });
//        System.out.println("处理前的menus是: "+menus);
        handleMenus(menus);
        // System.out.println("现在的menus是: "+menus);
        return menus;
    }

    public List<AdminMenu> getMenusByRoleId(int rid) {
        List<AdminMenu> menus=new ArrayList<>();
        List<AdminRoleMenu> rms = adminRoleMenuService.findAllByRid(rid);

        rms.forEach(rm->menus.add(adminMenuDAO.findById(rm.getMid())));

        handleMenus(menus);
        return menus;
    }

    //    public void handleMenus(List<AdminMenu> menus) {
//        menus.forEach(m->{
//            List<AdminMenu> children = getAllByParentId(m.getId());
//            m.setChildren(children);
//        });
//        menus.removeIf(m -> m.getParentId() != 0);
//    }
    public void handleMenus(List<AdminMenu> menus) {
        for (AdminMenu menu : menus) {
            List<AdminMenu> children = getAllByParentId(menu.getId());
            menu.setChildren(children);
        }
        Iterator<AdminMenu>iterator=menus.iterator();
        while (iterator.hasNext()) {
            AdminMenu menu=iterator.next();
//            System.out.println("handleMenus里边的menu.getParentId是: "+menu.getParentId());
            if (menu.getParentId() != 0) {
               iterator.remove();
            }
        }
    }

}
