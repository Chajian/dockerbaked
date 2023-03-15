package com.ibs.dockerbacked.entity.dto;


import lombok.Data;

@Data
public class PullImages {
    private String Name;
    private String tag;
}
