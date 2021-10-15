package com.sportswork.sportswork.controller;

import java.awt.*;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.sportswork.sportswork.config.SecurityUserInfo;
import com.sportswork.sportswork.core.entity.User;
import com.sportswork.sportswork.core.mapper.RobotDao;
import com.sportswork.sportswork.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sportswork.sportswork.entity.RobotEntity;
import com.sportswork.sportswork.core.service.RobotService;
import com.sportswork.sportswork.utils.PageUtils;
import com.sportswork.sportswork.utils.Query;
import com.sportswork.sportswork.utils.R;




/**
 * 
 * 
 * @author junpeng
 * @email 157237022@qq.com
 * @date 2021-06-18 17:33:37
 */
@RestController
@RequestMapping("robot")
public class RobotController {
	@Autowired
	private RobotService robotService;

	@Autowired
	private RobotDao robotDao;

	/**
	 * 所有列表
	 */
	@RequestMapping("/listAll")
	public R listAll(@RequestParam Map<String, Object> params){
	
		//查询列表数据
		List<RobotEntity> robotList = robotService.queryList(params);
		return R.ok().put("list", robotList);
	}

	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		SecurityUserInfo info =(SecurityUserInfo) authenticationToken.getPrincipal();
		if(info.getRoleCode().equals("role_user")){
			params.put("userid", info.getUserId());
		}
		if(params.get("robotSn")!=null){
			params.put("robotSn","%"+params.get("robotSn").toString()+"%");
		}
		
		//查询列表数据
        Query query = new Query(params);
		List<RobotEntity> robotList = robotService.queryList(query);

		int total = robotService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(robotList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Integer id){
		RobotEntity robot = robotService.queryObject(id);
		return R.ok().put("robot", robot);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody RobotEntity robot){
		try{
			//获取当前用户信息
			UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
			SecurityUserInfo info =(SecurityUserInfo) authenticationToken.getPrincipal();
			robot.setUsername(info.getUsername());
			robotService.save(robot);
			robotDao.bindRobot(info.getUserId(),robot.getId()+"");
		}catch (Exception e){
			return R.error("机器人编码重复");
		}

		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public R update(@RequestBody RobotEntity robot){
		try{
			robotService.update(robot);
			HttpClientUtils.postData("http://localhost:8085/updateRobot", JSONObject.toJSONString(robot));
			return R.ok().put("status","update success");
		}catch (Exception e){
			e.printStackTrace();
			return R.error(500,"update fail");
		}
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Integer[] ids){
		try{
			HttpClientUtils.postData("http://localhost:8085/deleteRobot", ids[0]+"");
			robotService.deleteBatch(ids);
			robotDao.deleteRobotForMap(ids[0]+"");
			return R.ok().put("status","add success");
		}catch (Exception e){
			e.printStackTrace();
			return R.error(500,"add fail");
		}


	}

	/***
	 *
	 *
	 *
	 */
	@RequestMapping("/activeRobot")
	public R activeRobot(@RequestBody String body){

		try{
			JSONObject json = JSONObject.parseObject(body);
			RobotEntity robotEntity = new RobotEntity();
			robotEntity.setLiveSn(json.getString("liveSn"));
			robotEntity.setRobotSn(json.getString("robotSn"));
			robotEntity.setRobotType(json.getString("robotType"));
			robotEntity.setRobotInfo(json.getString("robotInfo"));
			robotEntity.setOther(json.getString("other"));
			robotService.save(robotEntity);
			return R.ok().put("status","active success");
		}catch (Exception e){
			e.printStackTrace();
			return R.error(500,"project problem");

		}

	}

	/***
	 *
	 *
	 *
	 */
	@RequestMapping("/onlineRobot")
	public R onlineRobot(@RequestBody String body){
		try{
			JSONObject json = JSONObject.parseObject(body);
			Map map = new HashMap();
			map.put("robotSn",json.getString("robotSn"));
			map.put("online",json.getString("online").equals("true")?"在线":"离线");
			robotDao.updateStatus(map);
			return R.ok().put("status","update success");
		}catch (Exception e){
			e.printStackTrace();
			return R.error(500,"update fail");

		}
	}

	/**
	 * 新增
	 */
	@RequestMapping("/deleteRobot")
	public R deleteRobot(@RequestBody String body){
		JSONObject obj = JSONObject.parseObject(body);
		try{
			robotDao.deleteBySn(obj.getString("robotSn"));
			HttpClientUtils.postData("http://localhost:8085/deleteRobot", body);
			return R.ok().put("status","add success");
		}catch (Exception e){
			e.printStackTrace();
			return R.error(500,"add fail");
		}
	}

	/**
	 * 修改
	 */
	@RequestMapping("/updateRobot")
	public R updateRobot(@RequestBody RobotEntity robot){
		try{
			robotService.update(robot);
			HttpClientUtils.postData("http://localhost:8085/updateRobot", JSONObject.toJSONString(robot));
			return R.ok().put("status","update success");
		}catch (Exception e){
			e.printStackTrace();
			return R.error(500,"update fail");
		}

	}

	@RequestMapping("/bind")
	public R bindRobot(@RequestBody String body){
		try{
			JSONObject object = JSONObject.parseObject(body);
			//获取当前用户信息
			UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
			SecurityUserInfo info =(SecurityUserInfo) authenticationToken.getPrincipal();
			if(robotDao.queryOneCountForMapping(info.getUserId(),object.getString("id"))>0){
				return R.error(500,"该用户已绑定过这个机器人");
			}


			if(robotDao.queryCountForMapping(info.getUserId())>3&&info.getRoleCode().equals("role_user")){
				return R.error(500,"该用户已绑定超过三个机器人，不能再绑定");
			};
			robotDao.bindRobot(info.getUserId(),object.getString("id"));
			RobotEntity robot = robotDao.queryObject(object.getString("id"));
			//Map map = new HashMap();
			//map.put("robot",robot);
			//map.put("userid",info.getUserId());
			//同步三方
			HttpClientUtils.postData("http://localhost:8085/addRobot",JSONObject.toJSONString(robot));
			return R.ok().put("status","update success");
		}catch (Exception e){
			e.printStackTrace();
			return R.error(500,"update fail");
		}

	}

	@RequestMapping("/getAllInfos")
	public R queryInfos(){
		try{
			return R.ok().put("data",robotDao.queryAllInfos());
		}catch (Exception e){
			e.printStackTrace();
			return R.error(500,"update fail");
		}

	}

}
