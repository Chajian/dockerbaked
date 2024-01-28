package com.ibs.dockerbacked.service.serviceImpl;

import com.ibs.dockerbacked.common.Constants;
import com.ibs.dockerbacked.execption.CustomExpection;
import com.ibs.dockerbacked.service.FileService;
import com.ibs.dockerbacked.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * 文件存储文件实现
 * @Date 2023/01/16
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Override
    public boolean saveFile(byte[] context, String fileName, String path) {
        File root = new File(path);
        if(!root.exists()||!root.isDirectory())
            throw new CustomExpection(Constants.PATH_NOT_EXIST);
        FileOutputStream fileOutputStream = null;
        try {
            File file = new File(path+fileName);
            if(file.exists())
                throw new CustomExpection(Constants.FILE_AREALY_EXIST);
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(context);

        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomExpection(Constants.FILE_WRITE_FAIL);
        }
        finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new CustomExpection(Constants.FILE_WRITE_FAIL);
            }
        }

        return true;
    }

    @Override
    public boolean saveFile(InputStream context, String fileName, String path) {
        File root = new File(path);
        if(!root.exists()||!root.isDirectory())
            throw new CustomExpection(Constants.PATH_NOT_EXIST);

        FileOutputStream fileOutputStream = null;
        try {
            byte[] bytes = context.readAllBytes();
            File file = new File(path+fileName);
            fileOutputStream = new FileOutputStream(file);

            if(file.exists())
                throw new CustomExpection(Constants.FILE_AREALY_EXIST);
            fileOutputStream.write(bytes);

        } catch (IOException e) {
            throw new CustomExpection(Constants.FILE_WRITE_FAIL);
        }
        finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new CustomExpection(Constants.FILE_WRITE_FAIL);
            }
        }
        return false;
    }

    @Override
    public boolean createFolder(String path, String folderName) {
        File root = new File(path);
        if(!root.exists()||!root.isDirectory())
            throw new CustomExpection(Constants.PATH_NOT_EXIST);



        return false;
    }
}
