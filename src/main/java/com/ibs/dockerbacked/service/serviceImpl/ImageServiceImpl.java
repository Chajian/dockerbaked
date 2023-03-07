package com.ibs.dockerbacked.service.serviceImpl;

import com.github.dockerjava.api.model.Image;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.connection.ImageModel;
import com.ibs.dockerbacked.entity.User;
import com.ibs.dockerbacked.entity.dto.ImagesParam;
import com.ibs.dockerbacked.execption.CustomExpection;
import com.ibs.dockerbacked.service.ImageService;
import com.ibs.dockerbacked.service.UserSerivce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageModel imageModel;
    //获取镜像
    @Override
    public Result<List<Image>> getImages(ImagesParam imagesParam) {
        //模拟用户
        Long userId = 1234L;
        Integer page = imagesParam.getPageParam().getPage() == null ? 1 : imagesParam.getPageParam().getPage(); //页数  没传页数 默认第一
        Integer pageSize = imagesParam.getPageParam().getPageSize() == null ? 5 : imagesParam.getPageParam().getPageSize();//页大小 默认5条每页
        String label = imagesParam.getLabel();
        String imageName = imagesParam.getId().toString();
        //1.拿到该用户的所有镜像
        if (label != null) {
            imageName = imageName + ":" + label;
        }
        List<Image> images = imageModel.getImages(imageName);
        //2.分页处理
        if ((page - 1) * pageSize < images.size()) {
            images = images.stream()
                    .skip((page - 1) * pageSize)
                    .limit(pageSize)
                    .collect(Collectors.toList());
        }
        return Result.success(200, null, images);
    }

    @Override
    public Result pull(String imageName, String tag) {
        try {
            imageModel.pullImage(imageName,tag);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Result.success(200,"拉取成功",null);
    }
}
