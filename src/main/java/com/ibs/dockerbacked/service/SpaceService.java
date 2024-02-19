package com.ibs.dockerbacked.service;

/**
 *  空间服务
 * @author Yanglin
 */
public interface SpaceService {
    /**
     * 创建用户空间
     * @param userName
     * @return
     */
    boolean createUserSpace(String userName);

    /**
     * 创建用户镜像空间
     * @param account
     * @param image
     * @return
     */
    boolean createImageSpace(String account,String image);

    /**
     * 获取用户空间
     * @return
     */
    String getUserSpace(String userName);

    /**
     * 获取用户镜像空间
     * @return
     */
    String getImageSpaceFromUser(String account, String image);


}
