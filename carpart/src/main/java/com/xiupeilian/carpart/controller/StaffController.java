package com.xiupeilian.carpart.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.service.UserService;
import com.xiupeilian.carpart.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    private UserService userService;
    @RequestMapping("/staffList")
    public String staffList(LoginVo vo, Integer pageNo,
                            Integer pageSize,
                            HttpServletRequest request,
                            HttpServletResponse response) throws Exception{
        pageNo=pageNo==null?1:pageNo;
        pageSize=pageSize==null?9:pageSize;
        PageHelper.startPage(pageNo,pageSize);
        List<SysUser> userList = userService.findUser(vo);
        PageInfo<SysUser> page = new PageInfo<>(userList);
        request.setAttribute("page",page);
        request.setAttribute("staffList",userList);
        request.setAttribute("username",vo.getUsername());
        return "staff/staffList";
    }
}
