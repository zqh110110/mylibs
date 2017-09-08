package com.smcb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * Created by Administrator on 2017/4/11.
 */
@Controller
@RequestMapping
public class ImageUpLoadController  {

    @RequestMapping("/upload")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file")MultipartFile file){
        if(!file.isEmpty()){
            String path = "";
            String imgPath= "uploadicon/"+ UUID.randomUUID()+".png";
            try {
                path = getClass().getClassLoader().getResource("static/").getFile()+imgPath ;
                File file1 = new File(getClass().getClassLoader().getResource("static/").getFile() + "uploadicon");
                if (!file1.exists()) {
                    file1.mkdirs();
                }
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(path)));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return"上传失败,"+e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return"上传失败,"+e.getMessage();
            }
            return imgPath;
        }else{
            return"上传失败，因为文件是空的.";
        }
    }
}
