package com.ibs.dockerbacked.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ibs.dockerbacked.entity.User;
import com.ibs.dockerbacked.mapper.UserMapper;
import com.ibs.dockerbacked.service.UserSerivce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * UserMapper
 *
 * @author Chajian
 */

@Slf4j
@Service
public class UserSerivceImpl extends ServiceImpl<UserMapper, User> implements UserSerivce {

}
