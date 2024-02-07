package com.ibs.dockerbacked.service.serviceImpl;

import com.ibs.dockerbacked.service.SpaceService;

public class SpaceServideImpl implements SpaceService {
    @Override
    public boolean createUserSpace(String userName) {
        return false;
    }

    @Override
    public String getUserSpace(String userName) {
        return null;
    }

    @Override
    public String getImageSpaceFromUser(String userName) {
        return null;
    }
}
