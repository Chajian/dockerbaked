package com.ibs.dockerbacked.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ibs.dockerbacked.entity.User;
import org.apache.ibatis.annotations.Select;

/**
 * @author sn
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("select id from users where account =  #{account}")
    public Long getUserIdByAccount(String account);
}
