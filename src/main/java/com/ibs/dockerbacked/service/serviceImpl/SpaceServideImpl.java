package com.ibs.dockerbacked.service.serviceImpl;

import cn.hutool.core.io.FileUtil;
import com.alipay.service.schema.util.StringUtil;
import com.ibs.dockerbacked.common.Constants;
import com.ibs.dockerbacked.execption.CustomExpection;
import com.ibs.dockerbacked.service.FileService;
import com.ibs.dockerbacked.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.io.File;

public class SpaceServideImpl implements SpaceService {

    @Autowired
    FileService fileService;

    @Value("space.root:")
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
            fileService.createFolder(rootSpace,account);
            return true;
        }
        return false;
    }

    @Override
    public boolean createImageSpace(String account, String image) {
        //检测用户空间
        String userSpacePath = getUserSpace(account);
        File userSpace = new File(userSpacePath);
        if(!userSpace.exists())
            createUserSpace(account);

        //创建镜像空间
        String imageSpacePath = getImageSpaceFromUser(account,image);
        File imageSpace = new File(imageSpacePath);
        if(!imageSpace.exists()){
            fileService.createFolder(userSpacePath,image);
            return true;
        }
        return false;
    }

    @Override
    public String getUserSpace(String account) {
        String userSpace = rootSpace+File.pathSeparator+account;
        if(FileUtil.exist(userSpace)){
            createUserSpace(account);
        }
        return userSpace;
    }

    @Override
    public String getImageSpaceFromUser(String account, String image) {
        String imageSpace = getUserSpace(account)+File.pathSeparator+image;
        if(FileUtil.exist(imageSpace)){
            createImageSpace(account,image);
        }
        return imageSpace;
    }
}
