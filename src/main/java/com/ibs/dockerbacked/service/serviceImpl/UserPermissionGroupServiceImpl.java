package com.ibs.dockerbacked.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ibs.dockerbacked.entity.UserPermissionGroup;
import com.ibs.dockerbacked.mapper.UserPermissionGroupMapper;
import com.ibs.dockerbacked.service.UserPermissionGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserPermissionGroupServiceImpl extends ServiceImpl<UserPermissionGroupMapper, UserPermissionGroup> implements UserPermissionGroupService {
}
