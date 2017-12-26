package com.kunlun.api.service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kunlun.api.mapper.FileMapper;
import com.kunlun.constant.Constant;
import com.kunlun.entity.MallImage;
import com.kunlun.entity.MallImageSize;
import com.kunlun.entity.MallImg;
import com.kunlun.enums.CommonEnum;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author JackSpeed
 * @version V1.0 <>
 * @date 17-12-25下午5:29
 * @desc
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileMapper fileMapper;


    private final static Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);

    @Transactional
    @Override
    public DataRet uploadImage(MultipartFile file, String jsonContent) throws IOException {
        if (StringUtils.isEmpty(jsonContent)) {
            return new DataRet("param_error", "参数有误");
        }
        jsonContent = jsonContent.replace("\r\n", "");
        JSONObject jsonObject = JSON.parseObject(jsonContent);
        String imageType = jsonObject.getString("imageType");
        String cut = jsonObject.getString("cut");
        String watermark = jsonObject.getString("watermark");
        List<MallImageSize> cutSizeList;
        if (jsonObject.containsKey("cutSizeList")) {
            cutSizeList = jsonObject.getJSONArray("cutSizeList").toJavaList(MallImageSize.class);
        } else {
            cutSizeList = new ArrayList<>();
        }
        String path = parseFilePathByType(imageType);
        existDir(path);
        Calendar calendar = Calendar.getInstance();
        String fileName = String.valueOf(calendar.getTime().getTime());
        String name = file.getOriginalFilename();
        String endName = name.substring(name.lastIndexOf("."));
        File image = new File(path + fileName + endName);
        file.transferTo(image);

        List<MallImage> urlList = new ArrayList<>();
        String imageUrl = getFileName(path, fileName, endName, null);
        urlList.add(new MallImage(imageUrl));
        if (Constant.RICH_TEXT_IMG_PATH.equals(path)) {
            //富文本图片记录 url入库
            fileMapper.add(new MallImg(imageUrl, CommonEnum.TYPE_IMG_RICH_CONTENT.getCode()));
        }
        //裁剪
        if (CommonEnum.CUT.getCode().equals(cut)) {
            cutImage(watermark, cutSizeList, path, fileName, endName, image, urlList);
        } else if (CommonEnum.WATER_REMARK.getCode().equals(watermark)) {
            //加水印
            String absolutePath = getAbsolutePath(path, fileName, endName, null);
            waterRemark(null, absolutePath);
            urlList.add(new MallImage(getFileName(path, fileName, endName, null)));
        }
        return new DataRet<>(urlList);
    }


    /**
     * 根据url删除图片记录
     *
     * @param url 图片url
     * @return DataRet
     */
    @Override
    public DataRet deleteByUrl(String url) {
        if (StringUtils.isEmpty(url)) {
            return new DataRet<>("param_error", "参数有误");
        }
        //删除数据库信息
        deleteDBImage(url);
        //删除源文件
        deleteDiskImage(url);
        return new DataRet<>("删除成功");
    }

    /**
     * 根据图片URL查询图片详情
     *
     * @param url String
     * @return DataRet
     */
    @Override
    public DataRet findByUrl(String url) {
        if (StringUtils.isEmpty(url)) {
            return new DataRet<>("param_error", "参数有误");
        }
        MallImg mallImg = fileMapper.findByUrl(url);
        if (mallImg == null) {
            return new DataRet("not_found", "查无结果");
        }
        return new DataRet<>(mallImg);
    }

    /**
     * 根据id删除图片记录
     *
     * @param id 图片id
     * @return DataRet
     */
    @Override
    public DataRet deleteById(Long id) {
        if (id == null) {
            return new DataRet("param_error", "参数有误");
        }
        int result = fileMapper.deleteById(id);
        if (result > 0) {
            return new DataRet<>("删除成功");
        }
        return new DataRet<>("del_error", "删除失败");
    }

    /**
     * @param pageNo   Integer
     * @param pageSize Integer
     * @param type     类型 商品图片 TYPE_IMG_GOOD,
     *                 品牌图片 TYPE_IMG_BRAND,
     *                 文章图片 TYPE_IMG_ARTICLE,
     *                 商品评价图片 TYPE_IMG_ESTIMATE,
     *                 广告图片 TYPE_IMG_BANNER,
     *                 活动图片 TYPE_IMG_ACTIVITY,
     *                 富文本图片路径 TYPE_IMG_RICH_CONTENT ,
     *                 证件照图片路径  TYPE_IMG_ID_PHOTO
     * @param targetId 目标id
     * @return List
     */
    @Override
    public PageResult findByCondition(Integer pageNo, Integer pageSize, Long targetId, String type) {
        if (pageNo == null || pageSize == null) {
            return new PageResult();
        }
        PageHelper.startPage(pageNo, pageSize);
        Page<MallImg> page = fileMapper.findByCondition(type, targetId);
        return new PageResult<>(page);
    }

    /**
     * 图片裁剪
     */
    private void cutImage(String watermark, List<MallImageSize> cutSizeList, String path, String fileName, String endName, File image, List<MallImage> urlList) throws IOException {
        for (MallImageSize imageCutSize : cutSizeList) {
            String absolutePath = getAbsolutePath(path, fileName, endName, imageCutSize);
            cutImage(image, imageCutSize, absolutePath);
            if (CommonEnum.WATER_REMARK.getCode().equals(watermark)) {
                waterRemark(imageCutSize, absolutePath);
            }
            urlList.add(new MallImage(getFileName(path, fileName, endName, imageCutSize)));
        }
    }


    /**
     * 删除数据库图片
     *
     * @param imageName String
     */
    private void deleteDBImage(String imageName) {
        //子图
        fileMapper.deleteByUrl(imageName);
        if (isChildImage(imageName)) {
            return;
        }
        //循环删除主图对应的子图
        for (String size : Constant.SIZE_ARR) {
            String endName = imageName.substring(imageName.lastIndexOf("."));
            String startName = imageName.substring(0, imageName.lastIndexOf("."));
            String filePath = startName + "_" + size + endName;
            fileMapper.deleteByUrl(filePath);
        }
    }

    /**
     * 删除磁盘图片
     *
     * @param imageName String
     */
    private void deleteDiskImage(String imageName) {
        //图片根目录
        String basePath = Constant.BASE_IMG_DIR;
        String filePath = basePath + imageName;
        deleteFile(filePath);
        if (isChildImage(imageName)) {
            return;
        }
        //循环删除主图对应的子图
        for (String size : Constant.SIZE_ARR) {
            String endName = imageName.substring(imageName.lastIndexOf("."));
            String startName = imageName.substring(0, imageName.lastIndexOf("."));
            filePath = basePath + startName + "_" + size + endName;
            deleteFile(filePath);
        }
    }


    /**
     * @param imageName 是否是子图
     * @return boolean
     */
    private static boolean isChildImage(String imageName) {
        if (StringUtils.isEmpty(imageName)) {
            return false;
        }
        for (String size : Constant.SIZE_ARR) {
            if (imageName.contains(size)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 删除文件
     *
     * @param filePath 绝对路径
     */
    private static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            boolean delResult = file.getAbsoluteFile().delete();
            LOGGER.debug("删除文件: " + delResult);
        }
    }

    /**
     * 图片裁剪
     *
     * @param image        源文件
     * @param imageCutSize 尺寸
     * @param absolutePath 目标路径
     * @throws IOException e
     */
    private static void cutImage(File image, MallImageSize imageCutSize, String absolutePath) throws IOException {
        Thumbnails.of(image)
                .size(imageCutSize.getWidth(), imageCutSize.getHeight())
                .toFile(absolutePath);
    }

    /**
     * 添加水印
     *
     * @param imageCutSize MallImageSize
     * @param absolutePath String
     * @throws IOException e
     */
    private static void waterRemark(MallImageSize imageCutSize, String absolutePath) throws IOException {
        if (imageCutSize == null) {
            //大图加水印
            Thumbnails.of(absolutePath)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(Constant.WATER_REMARK_IMAGE_PATH)), 1f)
                    .toFile(absolutePath);
        } else if (imageCutSize.getWidth() >= Constant.WATER_REMARK_SIZE) {
            //加水印
            Thumbnails.of(absolutePath)
                    .size(imageCutSize.getWidth(), imageCutSize.getHeight())
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(Constant.WATER_REMARK_IMAGE_PATH)), 1f)
                    .toFile(absolutePath);
        }

    }

    /**
     * 获取图片名称
     *
     * @param path         String
     * @param fileName     示例：122334441121212
     * @param endName      后缀名 示例： .png
     * @param imageCutSize MallImageSize
     * @return String
     */
    private static String getFileName(String path, String fileName, String endName, MallImageSize imageCutSize) {
        path = path.replace(Constant.BASE_IMG_DIR, "");
        if (imageCutSize == null) {
            return path + fileName + endName;
        }
        return path + fileName + "_" + imageCutSize.getWidth() + "x" + imageCutSize.getHeight() + endName;
    }

    /**
     * 获取绝对路径
     *
     * @param path         示例： /home/hcon/mall
     * @param fileName     示例：122334441121212
     * @param endName      后缀名 示例： .png
     * @param imageCutSize MallImageSize
     * @return String
     */
    private static String getAbsolutePath(String path, String fileName, String endName, MallImageSize imageCutSize) {
        if (imageCutSize == null) {
            return path + fileName + endName;
        }
        return path + fileName + "_" + imageCutSize.getWidth() + "x" + imageCutSize.getHeight() + endName;
    }

    /**
     * 根据类型获取图片地址
     *
     * @param imageType 商品图片 TYPE_IMG_GOOD,
     *                  品牌图片 TYPE_IMG_BRAND,
     *                  文章图片 TYPE_IMG_ARTICLE,
     *                  商品评价图片 TYPE_IMG_ESTIMATE,
     *                  广告图片 TYPE_IMG_BANNER,
     *                  活动图片 TYPE_IMG_ACTIVITY,
     *                  富文本图片 TYPE_IMG_RICH_CONTENT ,
     *                  证件照图片  TYPE_IMG_ID_PHOTO
     * @return String
     */
    private String parseFilePathByType(String imageType) {
        String path;
        switch (imageType) {
            case "TYPE_IMG_GOOD":
                // 商品图片路径
                path = Constant.GOODS_IMG_PATH;
                break;
            case "TYPE_IMG_BRAND":
                //品牌图片路径
                path = Constant.BRAND_IMG_PATH;
                break;
            case "TYPE_IMG_ARTICLE":
                //文章图片路径
                path = Constant.ARTICLE_IMG_PATH;
                break;
            case "TYPE_IMG_ESTIMATE":
                //商品评价图片路径
                path = Constant.GOODS_ESTIMATE_IMG_PATH;
                break;
            case "TYPE_IMG_BANNER":
                //广告图片路径
                path = Constant.BANNER_IMG_PATH;
                break;
            case "TYPE_IMG_ACTIVITY":
                //活动图片路径
                path = Constant.ACTIVITY_IMG_PATH;
                break;
            case "TYPE_IMG_RICH_CONTENT":
                //富文本图片路径
                path = Constant.RICH_TEXT_IMG_PATH;
                break;
            case "TYPE_IMG_ID_PHOTO":
                //证件照图片
                path = Constant.ID_CARD_IMG_PATH;
                break;
            default:
                //其他模块图片路径
                path = Constant.OTHER_IMG_PATH;
                break;
        }
        return path;
    }

    /**
     * 检查文件目录是否存在,不存在就创建
     *
     * @param path String
     */
    private static void existDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                LOGGER.error("创建文件夹出错--------------");
            }
        }
    }
}
