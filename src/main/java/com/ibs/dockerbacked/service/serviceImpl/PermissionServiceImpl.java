package com.ibs.dockerbacked.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ibs.dockerbacked.entity.Permission;
import com.ibs.dockerbacked.mapper.PermissionMapper;
import com.ibs.dockerbacked.service.PermissionService;
import org.springframework.stereotype.Service;

/**
 * PermissionMapper
 * @author Chajian
 */

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
}
