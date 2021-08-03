package com.sportswork.sportswork.core.service;

import com.sportswork.sportswork.entity.UserEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author junpeng
 * @email 157237022@qq.com
 * @date 2021-06-18 17:33:37
 */
public interface UserService {
	
	UserEntity queryObject(Integer id);
	
	List<UserEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(UserEntity user);
	
	void update(UserEntity user);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
