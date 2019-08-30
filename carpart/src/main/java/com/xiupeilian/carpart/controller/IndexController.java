package com.xiupeilian.carpart.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiupeilian.carpart.model.*;
import com.xiupeilian.carpart.service.DymsnService;
import com.xiupeilian.carpart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Description: 首页、导航、消息公告相关
 * @Author: Tu Xu
 * @CreateDate: 2019/8/21 15:19
 * @Version: 1.0
 **/
@Controller
@RequestMapping("/index")
public class IndexController {
    @Autowired
    private UserService userService;
    @Autowired
    private DymsnService dymsnService;
    /**
     * @Description: 首页展示
     * @Author:      Administrator
     * @Param:       []
     * @Return       java.lang.String
      **/
    @RequestMapping("/index")
    public String index(){
        return "index/index";
    }
    /**
     * @Description: 首页top区域展示
     * @Author:      Administrator
     * @Param:       []
     * @Return       java.lang.String
      **/
    @RequestMapping("/top")
    public String top(HttpServletRequest request){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String now = sdf.format(new Date());
        request.setAttribute("now",now);
        return "index/top";
    }
    /**
     * @Description: 导航区域展示
     * @Author:      Administrator
     * @Param:       []
     * @Return       java.lang.String
      **/
    @RequestMapping("/navigation")
    public String navigation(HttpServletRequest request){
        //取到用户id
       SysUser user = (SysUser) request.getSession().getAttribute("user");
        //查询到登录用户的所有导航菜单
        List<Menu> menuList = userService.findMenusById(user.getId());
        request.setAttribute("menuList",menuList);
        return "index/navigation";

    }
    
    /**
     * @Description: 动态消息展示
     * @Author:      Administrator
     * @Param:       []
     * @Return       java.lang.String
      **/
    @RequestMapping("/dymsn")
    public String dymsn(HttpServletRequest request){
        List<Dymsn> dymsnList = dymsnService.findDymsns();
        request.setAttribute("list",dymsnList);
        return "index/dymsn";
    }

    @RequestMapping("/notice")
    public String notice(Integer pageSize,Integer pageNum,HttpServletRequest request){
        pageSize= pageSize==null?10:pageSize;
        pageNum= pageNum==null?1:pageNum;
        PageHelper.startPage(pageNum,pageSize);
        List<Notice> noticeList = dymsnService.findNotice();
        PageInfo<Notice> page = new PageInfo<>(noticeList);
        request.setAttribute("page",page);
        return "index/notice";
    }
    @RequestMapping("/news")
    public String news(Integer pageSize,Integer pageNum,HttpServletRequest request){
        pageSize= pageSize==null?10:pageSize;
        pageNum= pageNum==null?1:pageNum;
        PageHelper.startPage(pageNum,pageSize);
        List<News> newsList = dymsnService.findNews();
        PageInfo<News> page = new PageInfo<>(newsList);
        request.setAttribute("page",page);
        return "index/news";
    }
}
