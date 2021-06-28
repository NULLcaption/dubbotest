package com.cxg.provider.mapper;

import com.cxg.common.domin.SysUserDo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysUserMapper {
    @Results(id = "userMap", value = {
            @Result(column = "user_id", property = "user_id"),
            @Result(column = "username", property = "username"),
            @Result(column = "dept_id", property = "dept_id"),
            @Result(column = "email", property = "email")})
    @Select("SELECT * FROM sys_user")
    List<SysUserDo> getAll();

    @Select("SELECT * FROM sys_user t WHERE t.user_id = #{user_id}")
    @ResultMap("userMap")
    SysUserDo getOne(Long id);
}
