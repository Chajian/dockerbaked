package com.ibs.dockerbacked.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ibs.dockerbacked.entity.UserPermissionGroup;
import com.ibs.dockerbacked.mapper.UserPermissionGroupMapper;
import com.ibs.dockerbacked.service.UserPermissionGroupService;
import org.springframework.stereotype.Service;


@Service
public class UserPermissionGroupServiceImpl extends ServiceImpl<UserPermissionGroupMapper, UserPermissionGroup> implements UserPermissionGroupService {
}
