package com.ibs.dockerbacked.service.serviceImpl;

import cn.hutool.core.io.FileUtil;
import com.alipay.service.schema.util.StringUtil;
import com.ibs.dockerbacked.common.Constants;
import com.ibs.dockerbacked.execption.CustomExpection;
import com.ibs.dockerbacked.service.FileService;
import com.ibs.dockerbacked.service.SpaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;

@Slf4j
@Service
public class SpaceServideImpl implements SpaceService {

    @Autowired
    FileService fileService;

    String userSpaceFormat = "%s"+File.separator+"%s";
    String imageSpaceFormat = "%s"+File.separator+"image"+File.separator+"%s";
    String containerSpaceFormat = "%s"+File.separator+"container"+File.separator+"%s";

    @Value("${space.root:}")
    private String rootSpace;

    @PostConstruct
    public void init(){
        //init rootspace
        if (StringUtil.isEmpty(rootSpace)) {
            rootSpace = System.getProperty("user.dir");
        }
    }

    @Override
    public boolean createUserSpace(String account) {
        //创建用户空间
        String userSpacePath = getUserSpace(account);
        File userSpace = new File(userSpacePath);
        if(!userSpace.exists()){
            userSpace.mkdir();
            return true;
        }
        return false;
    }

    @Override
    public boolean createImageSpace(String account, String image) {
        //创建镜像空间
        String imageSpacePath = getImageSpaceFromUser(account,image);
        File imageSpace = new File(imageSpacePath);
        if(!imageSpace.exists()){
            imageSpace.mkdirs();
            return true;
        }
        return false;
    }

    @Override
    public boolean createContainerSpace(String account, String container) {
        //创建镜像空间
        String containerSpacePath = getContainerSpace(account,container);
        File containerSpace = new File(containerSpacePath);
        if(!containerSpace.exists()){
            containerSpace.mkdirs();
            return true;
        }
        return false;
    }

    @Override
    public String getUserSpace(String account) {
//        String userSpace = rootSpace+File.separator+account;
        String userSpace = String.format(userSpaceFormat,rootSpace,account);
        return userSpace;
    }

    @Override
    public String getImageSpaceFromUser(String account, String image) {
//        String imageSpace = getUserSpace(account)+File.separator+image;
        String userSpace = getUserSpace(account);
        String imageSpace = String.format(imageSpaceFormat,userSpace,image);
        return imageSpace;
    }

    @Override
    public String getContainerSpace(String account, String containerName) {
        String userSpace = getUserSpace(account);
        String containerSpace = String.format(containerSpaceFormat,userSpace,containerName);
        return containerSpace;
    }
}
