package com.ibs.dockerbacked.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ibs.dockerbacked.config.JWTToken;
import com.ibs.dockerbacked.entity.User;
import com.ibs.dockerbacked.entity.dto.UserDto;
import com.ibs.dockerbacked.execption.CustomExpection;
import com.ibs.dockerbacked.mapper.UserMapper;
import com.ibs.dockerbacked.service.UserSerivce;
import com.ibs.dockerbacked.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
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
