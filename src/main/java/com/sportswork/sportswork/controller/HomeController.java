package com.sportswork.sportswork.controller;

/**
 * @author dengwei
 * @date 2020/2/3 13:53
 * @description
 */

import com.sportswork.sportswork.config.SecurityUserInfo;
import com.sportswork.sportswork.core.entity.Role;
import com.sportswork.sportswork.core.entity.User;
import com.sportswork.sportswork.core.help.Menu;

import com.sportswork.sportswork.core.service.dto.PageDTO;
import com.sportswork.sportswork.core.service.dto.UserDTO;
import com.sportswork.sportswork.core.service.impl.MenuServiceImp;
import com.sportswork.sportswork.core.service.impl.UserServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Controller
public class HomeController {
    @Resource
    private UserServiceImp userServiceImp;
    @Resource
    private MenuServiceImp menuServiceImp;



    @RequestMapping({"/", "/index"})
    public String index(HttpServletRequest httpServletRequest, org.springframework.ui.Model model) {
        if(httpServletRequest.getRemoteUser() == null) {
            return "pages/login";
        } else {
            return "pages/index";
        }
    }

    @RequestMapping({"/details"})
    public String details() {
        org.springframework.security.core.userdetails.UserDetails userDetails = (org.springframework.security.core.userdetails.UserDetails) org.springframework.security.core.context.SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return "pages/view/home";
    }

    @RequestMapping({"/home"})
    public String home() {
        return "pages/view/home";
    }

    @RequestMapping({"/test"})
    public String test() {
        return "pages/test";
    }

    @RequestMapping("/404")
    public String error() {
        return "pages/error404";
    }

    @RequestMapping("/login")
    public String login(){
        return "pages/login";
    }

    @RequestMapping("/register")
    public String register(org.springframework.ui.Model model){
        return "pages/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String doRegister(UserDTO userDTO){
        // 此处省略校验逻辑
        userDTO.setRoleCode("role_user");
        if (userServiceImp.addUser(userDTO)) {
            log.info("自动登陆成功，登陆账户：" + userDTO.getUsername());
            User user = userServiceImp.getUserByUsername(userDTO.getUsername());
            SecurityUserInfo userInfo = new SecurityUserInfo(user, true, true, true);
            Authentication auth = new UsernamePasswordAuthenticationToken(userInfo, userInfo.getPassword(), userInfo.getAuthorities());
            org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(auth);
            return "redirect:/login";
        }
        return "redirect:/register?error";
    }

    @RequestMapping(value = "/session/invalid")
    public String sessionInvalid(org.springframework.ui.Model model, HttpServletRequest req) {
        model.addAttribute("url", "/login");
        return "pages/session_invalid";
    }

    @RequestMapping("/menu")
    @PreAuthorize("permitAll()")
    @ResponseBody
    public List<Menu> getMenu(){
        List<Menu> menuList = new ArrayList<>();
        SecurityUserInfo userDetails = (SecurityUserInfo) org.springframework.security.core.context.SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userServiceImp.getUserByUsername(userDetails.getUsername());
        List<Role> rolesName = user.getRoleList();
        for(Role roleName : rolesName) {
            menuList.addAll(menuServiceImp.getFirstLevelMenusByRoleName(roleName.getName()));
        }
        return menuList;
    }



    /**
     * 1-7表示 星期日至星期六
     * @param dates
     * @return
     */
    public int getWeek(String dates)
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Date d=null;
        try {
            d=f.parse(dates);
        } catch (ParseException e) {

            e.printStackTrace();
        }
        cal.setTime(d);
        int w=cal.get(Calendar.DAY_OF_WEEK)-1;
        return w+1;
    }
}

