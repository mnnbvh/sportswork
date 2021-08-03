package com.sportswork.sportswork.core.mapper;

import com.sportswork.sportswork.dao.BaseDao;
import com.sportswork.sportswork.entity.UserEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 
 * 
 * @author junpeng
 * @email 157237022@qq.com
 * @date 2021-06-18 17:33:37
 */
@Mapper
public interface UserDao extends BaseDao<UserEntity> {
    @Insert("insert into t_usermapping(user_id,robot_id) values(#{uid},#{rid})")
    void insertMapping(String uid,String rid);

    @Select("select * from user where username = #{username}")
    UserEntity queryByName(String username);

    @Delete("delete from user_role where #{user_id}=?")
    void deleteMapping(String user_id);
}
