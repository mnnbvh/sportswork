package com.sportswork.sportswork.core.service;

import com.sportswork.sportswork.entity.RobotEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author junpeng
 * @email 157237022@qq.com
 * @date 2021-06-18 17:33:37
 */
public interface RobotService {
	
	RobotEntity queryObject(Integer id);
	
	List<RobotEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RobotEntity robot);
	
	void update(RobotEntity robot);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
