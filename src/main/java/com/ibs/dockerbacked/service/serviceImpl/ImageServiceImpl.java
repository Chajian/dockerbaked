package com.ibs.dockerbacked.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dockerjava.api.model.DockerObject;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.connection.ImageModel;
import com.ibs.dockerbacked.entity.Image;
import com.ibs.dockerbacked.entity.dto.ImagesParam;
import com.ibs.dockerbacked.mapper.ImageMapper;
import com.ibs.dockerbacked.service.ImageService;
import com.ibs.dockerbacked.task.EventTask;
import com.ibs.dockerbacked.task.TaskStatus;
import com.ibs.dockerbacked.task.TaskThreadPool;
import com.ibs.dockerbacked.task.event.BaseListener;
import com.ibs.dockerbacked.task.event.Event;
import com.ibs.dockerbacked.util.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements ImageService {
    @Autowired
    private ImageModel imageModel;

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private TaskThreadPool taskThreadPool;
    //获取镜像
    @Override
    public List<? extends DockerObject> getImages(ImagesParam imagesParam, long userId) {
        //模拟用户
        Integer page = imagesParam.getPageParam() == null ? 1 : imagesParam.getPageParam().getPage(); //页数  没传页数 默认第一
        Integer pageSize = imagesParam.getPageParam() == null ? 5 : imagesParam.getPageParam().getPageSize();//页大小 默认5条每页

        String imageName = imagesParam.getLabel();
        List<? extends DockerObject> images = new ArrayList<>();
        if (imageName==null) {
            imageName = "";
            images = imageModel.getImages(imageName);
            //2.分页处理
            if(page!=null&&pageSize!=null)
                if ((page - 1) * pageSize < images.size()) {
                    images = images.stream()
                            .skip((page - 1) * pageSize)
                            .limit(pageSize)
                            .collect(Collectors.toList());
                }
        }
        else{
            images = imageModel.searchImage(imageName,pageSize);
        }
        return images;
    }

    /**
     *
     * @param imageName
     * @param tag
     * @return
     */
    @Override
    @Transactional
    public Result pull(String imageName, String tag) {
        //事件监听
        //TODO 异步处理
        log.info("pull之前:"+Thread.currentThread().getId());
        EventTask pullEvent = new EventTask(100){
            @Override
            public void start(){
                //主线程执行
            }
            //异步执行
            @Override
            public void asyncStart() {
                log.info("pullTask:"+Thread.currentThread().getId());
                BaseListener baseListener = new BaseListener(){
                    @Override
                    public void onListen(Event event) {
                        super.onListen(event);
                        log.info("pull监听:"+Thread.currentThread().getId());
                        if(event.getStatus().equals("complete")){
                            com.ibs.dockerbacked.entity.Image pullResponseItem = (com.ibs.dockerbacked.entity.Image) event.getT();
                            imageMapper.insert(pullResponseItem);
                            setStatus(TaskStatus.DEATH);
                        }
                    }
                };
                baseListener.addDriver(imageModel);
                baseListener.setT(this);
                imageModel.pullImage(imageName,tag);
                super.asyncStart();
            }
        };
        taskThreadPool.addTask(pullEvent);
        log.info("pull结束:"+Thread.currentThread().getId());
        return Result.success(200,"拉取成功",null);
    }

    @Override
    public Result build(File dockerFile) {
        imageModel.buildImage(dockerFile);
        return Result.success(200,"构建成功!",null);
    }

    @Override
    public List<com.ibs.dockerbacked.entity.Image> dockerObjectToImage(List<? extends DockerObject> objects) {
        List<com.ibs.dockerbacked.entity.Image> images = new ArrayList<>();
        for(DockerObject object:objects ){
            com.ibs.dockerbacked.entity.Image image = EntityUtils.dockerObjectToImage(object);
            if(image.getName()!=null){
                images.add(image);
            }
        }



        return images;
    }
}
