package com.sportswork.sportswork.controller;



import java.util.List;
import java.util.Map;


import com.alibaba.fastjson.JSONObject;
import com.sportswork.sportswork.core.mapper.RobotDao;
import com.sportswork.sportswork.core.mapper.UserDao;
import com.sportswork.sportswork.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sportswork.sportswork.entity.UserEntity;
import com.sportswork.sportswork.core.service.UserService;






/**
 * 
 * 
 * @author junpeng
 * @email 157237022@qq.com
 * @date 2021-06-18 17:33:37
 */
@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private RobotDao robotDao;

	/**
	 * 所有列表
	 */
	@RequestMapping("/listAll")
	public R listAll(@RequestParam Map<String, Object> params){
	
		//查询列表数据
		List<UserEntity> userList = userService.queryList(params);
		return R.ok().put("list", userList);
	}

	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
	
		
		//查询列表数据
        Query query = new Query(params);
		List<UserEntity> userList = userService.queryList(query);

		int total = userService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Integer id) {
		UserEntity user = userService.queryObject(id);
		return R.ok().put("user", user);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody UserEntity user){
		userService.save(user);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public R update(@RequestBody UserEntity user){
		userService.update(user);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Integer[] ids){
		userService.deleteBatch(ids);
		return R.ok();
	}

	@RequestMapping("/addUser")
	public R addUser(@RequestBody UserEntity user){
		try{
			userService.save(user);
			return R.ok().put("status","success");
		}catch (Exception e){
			e.printStackTrace();
			return R.error(500,"add fail");
		}


	}

	/**
	 * 删除
	 */
	@RequestMapping("/deleteUser")
	public R deleteUser(@RequestBody String id){

		try{
			userService.deleteBatch(new Integer[]{Integer.parseInt(id)});
			return R.ok().put("status","delete success");
		}catch (Exception e){
			e.printStackTrace();
			return R.error(500,"delete fail");
		}
	}

	/**
	 * 删除
	 */
	@RequestMapping("/updateUser")
	public R updateUser(@RequestBody UserEntity user){

		try{
			user.setPassword(user.getPassword());
			userService.update(user);
			return R.ok().put("status","update success");
		}catch (Exception e){
			e.printStackTrace();
			return R.error(500,"update fail");
		}
	}

	/**
	 * 新增
	 */
	@RequestMapping("/addRobot")
	public R addRobot(@RequestBody String body){
		JSONObject obj = JSONObject.parseObject(body);
		try{
			String rid = robotDao.queryBySn(obj.getString("robotSn"));
			userDao.insertMapping(obj.getString("userId"),rid);
			return R.ok().put("status","update success");
		}catch (Exception e){
			e.printStackTrace();
			return R.error(500,"update fail");
		}
	}

	
}
