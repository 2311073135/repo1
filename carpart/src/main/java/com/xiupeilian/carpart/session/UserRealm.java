package com.xiupeilian.carpart.session;

import com.xiupeilian.carpart.model.Menu;
import com.xiupeilian.carpart.model.Role;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.service.UserService;
import com.xiupeilian.carpart.util.SHA1Util;
import com.xiupeilian.carpart.vo.LoginVo;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    /**
     * 授权的方法（subject第一次访问需要权限才可以访问url的时候）
     * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection collection) {
        SysUser user = (SysUser) collection.getPrimaryPrincipal();
        //查询出该用户所有的角色信息和权限信息
        //先查角色信息
        Role role = userService.findRoleByRoleId(user.getRoleId());
        List<String> roleList = new ArrayList<>();
        roleList.add(role.getRoleEnglishName());
        //查询用户的权限信息（菜单）
        List<Menu> menuList = userService.findMenusById(user.getId());
        List<String> permisstionList = new ArrayList<>();
        for(Menu menu : menuList){
            permisstionList.add(menu.getMenuKey());
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roleList);
        info.addStringPermissions(permisstionList);
        return info;
    }

    /**
     * 登录认证的方法（subject.login(token)）
     * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //查询数据库 去判断用户名密码是否存在
        LoginVo vo = new LoginVo();
        vo.setLoginName(token.getUsername());
        vo.setPassword(SHA1Util.encode(new String(token.getPassword())));
        SysUser user = userService.findUserByLoginNameAndPassword(vo);
        if(user==null){
            throw new AccountException("2");
        }else{
            //返回用户的认证成功之后的身份信息给shiroFilter
            AuthenticationInfo info = new SimpleAuthenticationInfo(user,
                    token.getPassword(),getName());
            return info;
        }

    }
}
