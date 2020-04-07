package xyz.taka8rie.finalback.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import xyz.taka8rie.finalback.Service.UserService;
import xyz.taka8rie.finalback.pojo.User;


public class FBRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    //授权逻辑
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo s=new SimpleAuthorizationInfo();
        return s;
    }

    //认证逻辑
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken Token) throws AuthenticationException {
       String username=Token.getPrincipal().toString();
        User user = userService.getByname(username);
        if (ObjectUtils.isEmpty(user)) {
            throw new UnknownAccountException();
        }
        String passwordInDB = user.getPassword();
        String salt=user.getSalt();
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, passwordInDB, ByteSource.Util.bytes(salt), getName());
        return authenticationInfo;
    }
}
