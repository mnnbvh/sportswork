package com.sportswork.sportswork.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.sportswork.sportswork.core.mapper.RobotDao;
import com.sportswork.sportswork.entity.RobotEntity;
import com.sportswork.sportswork.core.service.RobotService;



@Service("robotService")
public class RobotServiceImpl implements RobotService {
	@Autowired
	private RobotDao robotDao;
	
	@Override
	public RobotEntity queryObject(Integer id){
		return robotDao.queryObject(id);
	}
	
	@Override
	public List<RobotEntity> queryList(Map<String, Object> map){
		return robotDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return robotDao.queryTotal(map);
	}
	
	@Override
	public void save(RobotEntity robot){
		robotDao.save(robot);
	}
	
	@Override
	public void update(RobotEntity robot){
		robotDao.update(robot);
	}
	
	@Override
	public void delete(Integer id){
		robotDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		robotDao.deleteBatch(ids);
	}
	
}
