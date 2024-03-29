package com.cp.controller;

import com.cp.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author feipeng
 * @site www.gcp168.cn
 * @create 2019-10-27 15:56
 */
@RestController
@RequestMapping("/")
public class ImageController {
    @Value("${imageFilePath}")
    private String imageFilePath;

    @RequestMapping("/uploadImage")
    public Map<String,Object> uploadImage(MultipartFile file)throws Exception{
        Map<String,Object> map = new HashMap<>();
        if(!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf('.'));
            String newFileName = DateUtil.getCurrentDateStr() + suffixName;
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(imageFilePath + newFileName));
            map.put("code", 0);
            map.put("msg", "上传成功");
            Map<String, Object> map2 = new HashMap<>();
            map2.put("title", newFileName);
            map2.put("src", "/image/" + newFileName);
            map.put("data", map2);
        }
        return  map;

        }
    }
