package com.ibs.dockerbacked.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 目录树
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeNode {
    /*当前目录或者文件名*/
    private String name;
    /*绝对路径*/
    private String absolutePath;
    /*文件类型*/
    private String type;
    /*子节点*/
    private List<TreeNode> treeNodeList = new ArrayList<>();

    public void addNode(TreeNode node){
        treeNodeList.add(node);
    }
}
