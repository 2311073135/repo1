package com.xiupeilian.carpart.mapper;

import com.xiupeilian.carpart.base.BaseMapper;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.vo.LoginVo;
import com.xiupeilian.carpart.vo.RegisterVo;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser findUserByLoginNameAndPassword(LoginVo vo);
    SysUser findUserByLoginNameAndEmail(LoginVo vo);
    List<SysUser> findUser(LoginVo vo);
    SysUser findUserByLoginName(String loginName);
    SysUser findUserByPhone(String phone);
    SysUser findUserByEmail(String email);

    void addRegister(RegisterVo vo);
}