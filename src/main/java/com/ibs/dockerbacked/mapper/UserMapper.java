package com.ibs.dockerbacked.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ibs.dockerbacked.entity.Hardware;
import com.ibs.dockerbacked.entity.User;
import com.ibs.dockerbacked.entity.dto.HardwareDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/**
 * @author sn
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("select id from users where account =  #{account}")
    public int getUserIdByAccount(String account);


}
