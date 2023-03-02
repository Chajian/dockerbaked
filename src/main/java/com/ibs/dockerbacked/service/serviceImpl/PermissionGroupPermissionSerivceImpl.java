package com.ibs.dockerbacked.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ibs.dockerbacked.entity.PermissionGroupPermission;
import com.ibs.dockerbacked.mapper.PermissionGroupPermissionMapper;
import com.ibs.dockerbacked.service.PermissionGroupPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author sn
 */
@Slf4j
@Service
public class PermissionGroupPermissionSerivceImpl extends ServiceImpl<PermissionGroupPermissionMapper, PermissionGroupPermission> implements PermissionGroupPermissionService {
}
