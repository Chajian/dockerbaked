package com.ibs.dockerbacked.entity.dto;


import lombok.Data;

@Data
public class PullImages {
    private String name;
    private String tag;
}
