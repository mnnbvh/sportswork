package com.sportswork.sportswork.core.mapper;

import com.sportswork.sportswork.dao.BaseDao;
import com.sportswork.sportswork.entity.RobotEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author junpeng
 * @email 157237022@qq.com
 * @date 2021-06-18 17:33:37
 */
@Mapper
public interface RobotDao extends BaseDao<RobotEntity> {

    @Update("update t_robot set online=#{online} where robot_sn = #{robotSn}")
    void updateStatus(Map map);

    @Select("select id from t_robot where robot_sn=#{sn}")
    String queryBySn(String sn);

    @Delete("delete from t_robot where robot_sn = #{sn}")
    void deleteBySn(String sn);

    @Insert("insert into t_usermapping(user_id,robot_id) values(#{userId},#{robotId})")
    void bindRobot(String userId,String robotId);

    @Select("select count(*) from t_usermapping where user_id=#{userId}")
    int queryCountForMapping(String userId);

    @Select("select count(*) from t_usermapping where user_id=#{userId} and robot_id=#{robotId}")
    int queryOneCountForMapping(String userId,String robotId);

    @Select("select t1.*,t3.id as rid,t3.robot_sn,t3.robot_type,t3.live_sn,t3.robot_info,t3.remark,t3.other,t3.online from user t1 " +
            "left join t_usermapping t2 on t1.id = t2.user_id " +
            "left join t_robot t3 on t2.robot_id = t3.id ")
    List<Map<String,Object>> queryAllInfos();

    @Delete("delete from t_usermapping where robot_id=#{rid}")
    int deleteRobotForMap(String rid);
}
