package com.xiupeilian.carpart.session;

import com.xiupeilian.carpart.model.Menu;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Description: session拦截、权限控制
 * @Author: Tu Xu
 * @CreateDate: 2019/8/21 13:59
 * @Version: 1.0
 **/
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;

    //session过滤以及权限控制
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception{
/*        //获取请求目标资源路径
        String path = request.getRequestURI();
        if(path.contains("login")){
            return  true;
        }else{
            //需要进行session过滤以及权限控制
            HttpSession session = request.getSession(false);
            if(null==session){
                //session为null
                response.sendRedirect(request.getContextPath()+"/login" +
                        "/toLogin");
                return false;
            }else{
                if(null==session.getAttribute("user")){
                    //session不为null但没有user对象
                    response.sendRedirect(request.getContextPath()+"/login" +
                            "/toLogin");
                    return false;
                }else{
                    //意味着用户登陆过（session检验完毕）
                    //权限过滤
                    //查询出该用户对应的导航权限
                    SysUser user = (SysUser) session.getAttribute("user");
                    //查询出该用户对应的权限
                    List<Menu> menuList = userService.findMenusById(user.getId());
                    //每一个导航菜单都有一个权限关键字（url的分包路径）
                    boolean check = false;
                    for(Menu menu:menuList){
                        //如果用户请求的资源路径包含了自己所拥有的导航中的权限关键字
                        if(path.contains(menu.getMenuKey())){
                            check=true;
                        }
                    }
                    //如果check为true，那么就是正常访问，如果为false，非法访问
                    if(check){
                        return true;
                    }else{
                        //登录成功但非法访问
                        response.sendRedirect(request.getContextPath()+"/login" +
                                "/noauth");
                        return false;
                    }

                }
            }
        }*/
return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
           request.setAttribute("ctx",request.getContextPath());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
