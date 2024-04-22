package com.ibs.dockerbacked.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PullImage
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PullImages {
    private String name;
    private String tag;
    private int imageId;
    private String status;
}
