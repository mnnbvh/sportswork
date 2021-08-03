package com.sportswork.sportswork.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sportswork.sportswork.core.entity.Role;
import com.sportswork.sportswork.core.entity.User;
import com.sportswork.sportswork.core.mapper.UserDao;
import com.sportswork.sportswork.core.service.dto.PageDTO;
import com.sportswork.sportswork.core.service.dto.UserDTO;
import com.sportswork.sportswork.core.service.impl.RoleServiceImp;
import com.sportswork.sportswork.core.service.impl.UserServiceImp;
import com.sportswork.sportswork.entity.UserEntity;
import com.sportswork.sportswork.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dengwei
 * @date 2020/2/4 16:49
 * @description
 */
@Slf4j
@Controller("userEntityController")
public class UserController {
    @Resource
    private UserServiceImp userServiceImp;
    @Resource
    private RoleServiceImp roleServiceImp;
    @Autowired
    private UserDao userDao;

    @RequestMapping("/admin/user/list")
    public String List(){
        return "/pages/view/admin/user/list";
    }

    @RequestMapping("/admin/user/getAllUsers")
    @ResponseBody
    public Object getAllUsers(){
        return new PageDTO<UserDTO>().toPageDTO(
                UserDTO.UserDTOList(userServiceImp.getAllUsers()));
    }

    @RequestMapping(value = "/admin/user/add", method = RequestMethod.POST)
    @ResponseBody
    public String add(UserDTO userDTO) {
        userServiceImp.addUser(userDTO);
        UserEntity user = userDao.queryByName(userDTO.getUsername());
        HttpClientUtils.postData("http://localhost:8085/addUser", JSONObject.toJSONString(user));
        return "success";
    }


    @RequestMapping(value = "/admin/user/edit", method = RequestMethod.POST)
    @ResponseBody
    public String edit(UserDTO userDTO) {
        userServiceImp.setUser(userDTO);
        UserEntity user = userDao.queryByName(userDTO.getUsername());
        HttpClientUtils.postData("http://localhost:8085/updateUser",  JSONObject.toJSONString(user));
        return "success";
    }

    @RequestMapping("/admin/user/getAllRoles")
    @ResponseBody
    public Object getAllRoles() {
        return new PageDTO<Role>().toPageDTO(roleServiceImp.getAllRoles());
    }

    @RequestMapping(value = "/admin/user/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@RequestParam String username) {
        if(username.equals("root")){
            return "error";
        }
        UserEntity user = userDao.queryByName(username);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", user.getId());
        HttpClientUtils.postData("http://localhost:8085/deleteUser", jsonObject.toJSONString());
        //删除映射表数据
        userDao.deleteMapping(user.getId()+"");
        userServiceImp.delUser(username);
        return "true";
    }

    @RequestMapping(value = "/admin/user/stop", method = RequestMethod.POST)
    @ResponseBody
    public Object stop(@RequestBody User user) {
        userServiceImp.stopUser(user);
        return "true";
    }
}
