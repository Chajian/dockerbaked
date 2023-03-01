package com.ibs.dockerbacked.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ibs.dockerbacked.entity.Users;
import com.ibs.dockerbacked.mapper.UserMapper;
import com.ibs.dockerbacked.service.UserSerivce;
import org.springframework.stereotype.Service;

/**
 * UserMapper
 *
 * @author Chajian
 */


@Service
public class UserSerivceImpl extends ServiceImpl<UserMapper, Users> implements UserSerivce {

}
