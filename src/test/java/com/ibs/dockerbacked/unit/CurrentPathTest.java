package com.ibs.dockerbacked.unit;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CurrentPathTest {
    private List<String> commands = new ArrayList<>();


    @Before
    public void init(){
        commands.add("cd /home/iotmp");
        commands.add("cd ./");
        commands.add("cd ./");
        commands.add("cd ../");
        commands.add("cd ../aa/jj");
        commands.add("cd ./jskdf/sdjf");
    }

    public String execCm(String common,String currentPath){
        char[] chars = common.toCharArray();

        if(chars.length<3||chars[0]!='c'||chars[1]!='d'||chars[2]!=' ') {
            return null;
        }
        return relativePathToAbstractPath(common.substring(3,common.length()),currentPath);
    }

    public String relativePathToAbstractPath(String path,String currentPath){
        char[] chars = path.toCharArray();
        if(chars[0]=='/') {
        }
        else if(chars[0]=='.'&&chars[1]=='.') {
            path = getUpPath(currentPath) +'/'+ path.substring(3, path.length());
        }
        else if(chars[0]=='.'){
            path = currentPath+path.substring(1,path.length());
        }

        if(path.charAt(path.length()-1)=='/')
            return path.substring(0,path.length()-1);
        return path;
    }

    public String getUpPath(String path){
        int lastOn = path.lastIndexOf('/');
        return path.substring(0,lastOn);
    }

    @Test
    public void test(){
        String currentPath = "/";
        for(String command : commands){
            String nextPath = execCm(command,currentPath);
            System.out.println("当前路径:"+currentPath+"当前执行的command:"+command+"执行后的路径:"+nextPath);
            currentPath = nextPath;
        }

    }


}
