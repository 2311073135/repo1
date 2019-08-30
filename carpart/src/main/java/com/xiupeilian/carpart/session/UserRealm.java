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
     * ��Ȩ�ķ�����subject��һ�η�����ҪȨ�޲ſ��Է���url��ʱ��
     * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection collection) {
        SysUser user = (SysUser) collection.getPrimaryPrincipal();
        //��ѯ�����û����еĽ�ɫ��Ϣ��Ȩ����Ϣ
        //�Ȳ��ɫ��Ϣ
        Role role = userService.findRoleByRoleId(user.getRoleId());
        List<String> roleList = new ArrayList<>();
        roleList.add(role.getRoleEnglishName());
        //��ѯ�û���Ȩ����Ϣ���˵���
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
     * ��¼��֤�ķ�����subject.login(token)��
     * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //��ѯ���ݿ� ȥ�ж��û��������Ƿ����
        LoginVo vo = new LoginVo();
        vo.setLoginName(token.getUsername());
        vo.setPassword(SHA1Util.encode(new String(token.getPassword())));
        SysUser user = userService.findUserByLoginNameAndPassword(vo);
        if(user==null){
            throw new AccountException("2");
        }else{
            //�����û�����֤�ɹ�֮��������Ϣ��shiroFilter
            AuthenticationInfo info = new SimpleAuthenticationInfo(user,
                    token.getPassword(),getName());
            return info;
        }

    }
}
