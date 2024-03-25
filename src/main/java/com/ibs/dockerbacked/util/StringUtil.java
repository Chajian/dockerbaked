package com.ibs.dockerbacked.util;

public class StringUtil {
    public static String execCm(String common,String currentPath){
        char[] chars = common.toCharArray();

        if(chars.length<3||chars[0]!='c'||chars[1]!='d'||chars[2]!=' ') {
            return null;
        }
        return relativePathToAbstractPath(common.substring(3,common.length()),currentPath);
    }

    public static String relativePathToAbstractPath(String path,String currentPath){
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

    public static String getUpPath(String path){
        int lastOn = path.lastIndexOf('/');
        return path.substring(0,lastOn);
    }
}
