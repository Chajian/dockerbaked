package com.ibs.dockerbacked.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ibs.dockerbacked.entity.PermissionGroup;
import com.ibs.dockerbacked.mapper.PermissionGroupMapper;
import com.ibs.dockerbacked.service.PermissionGroupService;
import org.springframework.stereotype.Service;

/**
 * PermissionGroupMapper
 * @author Chajian
 */

@Service
public class PermissionGroupServiceImpl extends ServiceImpl<PermissionGroupMapper, PermissionGroup> implements PermissionGroupService {
}
