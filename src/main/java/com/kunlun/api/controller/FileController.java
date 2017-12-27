package com.kunlun.api.controller;

import com.kunlun.api.service.FileService;
import com.kunlun.entity.MallImg;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author JackSpeed
 * @version V1.0 <>
 * @date 17-12-25下午5:27
 * @desc
 */
@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    FileService fileService;

    /**
     * 图片上传
     *
     * @param file        MultipartFile
     * @param jsonContent String
     * @return DataRet
     * @throws IOException e
     */
    @PostMapping(value = "/uploadImage")
    public DataRet uploadImage(@RequestParam(value = "file") MultipartFile file,
                               @RequestParam(value = "jsonContent") String jsonContent) throws IOException {
        return fileService.uploadImage(file, jsonContent);
    }

    /**
     * 根据url查询图片
     *
     * @param mallImg 图片对象
     * @return DataRet
     */
    @GetMapping(value = "/add")
    public DataRet add(@RequestBody MallImg mallImg) {
        return fileService.add(mallImg);
    }


    /**
     * 根据url查询图片
     *
     * @param url 图片url
     * @return DataRet
     */
    @GetMapping(value = "/findByUrl")
    public DataRet findByUrl(@RequestParam(value = "url") String url) {
        return fileService.findByUrl(url);
    }


    /**
     * 图片删除
     *
     * @param url 图片url
     * @return DataRet
     */
    @PostMapping(value = "/deleteByUrl")
    public DataRet deleteByUrl(@RequestParam(value = "url") String url) {
        return fileService.deleteByUrl(url);
    }


    /**
     * 图片删除
     *
     * @param id 图片id
     * @return DataRet
     */
    @PostMapping(value = "/deleteById")
    public DataRet deleteById(@RequestParam(value = "id") Long id) {
        return fileService.deleteById(id);
    }

    /**
     * 图片列表
     *
     * @param pageNo   Integer
     * @param pageSize Integer
     * @param type     类型 商品图片 TYPE_IMG_GOOD,
     *                 品牌图片 TYPE_IMG_BRAND,
     *                 文章图片 TYPE_IMG_ARTICLE,
     *                 商品评价图片 TYPE_IMG_ESTIMATE,
     *                 广告图片 TYPE_IMG_BANNER,
     *                 活动图片 TYPE_IMG_ACTIVITY,
     *                 富文本图片 TYPE_IMG_RICH_CONTENT ,
     *                 证件照图片  TYPE_IMG_ID_PHOTO
     * @param targetId 目标id
     * @return List
     */
    @GetMapping(value = "/findByCondition")
    public PageResult findByCondition(@RequestParam(value = "pageNo") Integer pageNo,
                                      @RequestParam(value = "pageSize") Integer pageSize,
                                      @RequestParam(value = "type", required = false) String type,
                                      @RequestParam(value = "targetId", required = false) Long targetId) {
        return fileService.findByCondition(pageNo, pageSize, targetId, type);
    }

}
