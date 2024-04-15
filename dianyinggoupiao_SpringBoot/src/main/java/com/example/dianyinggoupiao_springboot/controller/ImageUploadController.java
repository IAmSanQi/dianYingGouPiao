package com.example.dianyinggoupiao_springboot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.dianyinggoupiao_springboot.entity.*;
import com.example.dianyinggoupiao_springboot.mapper.CinemaAccMapper;
import com.example.dianyinggoupiao_springboot.mapper.ManageAccMapper;
import com.example.dianyinggoupiao_springboot.mapper.MovieMapper;
import com.example.dianyinggoupiao_springboot.mapper.UserAccMapper;
import com.example.dianyinggoupiao_springboot.repositroy.CinemaRepository;
import com.example.dianyinggoupiao_springboot.server.*;
import com.example.dianyinggoupiao_springboot.util.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;

@RestController
@RequestMapping("/Images")
public class ImageUploadController {
    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private ImageUploadService imageUploadService;

    @Autowired
    private UserAccMapper userAccMapper;

    @Autowired
    private UserAccService userAccService;

    @Autowired
    private CinemaAccMapper cinemaAccMapper;

    @Autowired
    private CinemaAccService cinemaAccService;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private ManageAccService manageAccService;

    /**
     * 删除图片
     * @param filePath 文件路径
     * @param imageName 图片名称（包括后缀）
     * @return 字符串
     */
    public String removeImage(String filePath,String imageName){
        //获取文件夹
        File folder = new File(filePath);
        //判断文件、目录是否存在，是否为目录
        if (folder.exists()&&folder.isDirectory()){
            File[] files = folder.listFiles();
            if (files!=null){
                for (File file : files) {
                    //判断文件是否存在
                    if (file.getName().equals(imageName)){
                        boolean delete = file.delete();
                        if (delete){
                            return "删除成功！";
                        }else {
                            return "删除失败！";
                        }
                    }
                }
            }else {
                return folder.getName()+"内没有文件！！！";
            }
        }else {
            return "文件不存在，或者文件不为目录";
        }


        return "删除失败！";
    }

    /**
     * 保存图片到relativePath路径中，并返回图片的新名称
     * @param image 图片
     * @param relativePath 路径名
     * @return 返回当前图片的带有uuid名的新名称
     * @throws IOException 图片异常
     */
    public String saveImageToPath(MultipartFile image,String relativePath) throws IOException {
        byte[] bytes = image.getBytes();
        //获取图片名称
        String originalFilename = image.getOriginalFilename();
        //获取后缀
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

        //生成唯一uuid并写入保存的路径
        String newFilename = UUID.randomUUID().toString() + fileExtension;
        Path path = Paths.get(relativePath, newFilename);
        Files.write(path, bytes);

        return newFilename;
    }

    /**
     * 保存图片信息
     * @param Filename 图片名称
     * @param accName 用户账号
     * @param relativePath 图片所在路径
     * @param obj 用户类
     * @return 字符串
     * @param <T> 用户类
     */
    public <T> String saveUserAvatar(String Filename,String accName,String relativePath,T obj){
        String ImageName = Filename.substring(0, Filename.lastIndexOf("."));
        String Extension = Filename.substring(Filename.lastIndexOf("."));

        if (obj instanceof User){
            try {
                User user = (User) obj;
                //判断user用户的头像是否为null
                if (user.getAvatarId()==null||user.getAvatarId().isEmpty()){
                    RandomStringGenerator rm=new RandomStringGenerator();
                    //获取唯一随机AvatarId
                    String uid=rm.generateUniqueRandomStringAvatar(userAccMapper);
                    //向user用户添加AvatarId
                    imageUploadService.addUserAvatarId(accName,uid);
                    //头像
                    Images avatarImage = new Images(uid,ImageName,Extension,relativePath);
                    //向数据库表user_avatar插入
                    imageUploadService.addUser_avatar(avatarImage);
                }else {
                    //获取老的照片信息
                    Images oldAvatar = imageUploadService.findOldUser_AvatarById(user.getAvatarId());

                    //获取头像的id
                    String avatarId =user.getAvatarId();
                    //设置avatarImage的属性
                    Images avatarImage = new Images(ImageName,Extension,relativePath);
                    //更新数据库信息
                    imageUploadService.addUser_AvatarId(avatarImage,avatarId);
                    //删除原本的照片
                    removeImage(relativePath,oldAvatar.getFilename()+oldAvatar.getExtension());
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
                return "user图片信息保存失败";
            }
        }else if (obj instanceof Cinema){
            try{
                Cinema cinema = (Cinema) obj;
                if (cinema.getAvatarId()==null||cinema.getAvatarId().isEmpty()){
                    RandomStringGenerator rm=new RandomStringGenerator();
                    //获取唯一随机AvatarId
                    String uid=rm.generateUniqueRandomStringAvatar(cinemaRepository);

                    //查找cinema用户中账号为accName的id
                    int cinema_id = cinemaAccMapper.findCinemaId(accName);
                    //向cinema用户添加AvatarId
                    cinemaRepository.updateAvatarIdById(cinema_id,uid);
                    //头像
                    Images avatarImage = new Images(uid,ImageName,Extension,relativePath);
                    //向数据库表cinema_avatar插入
                    imageUploadService.addCinema_avatar(avatarImage);
                }else {
                    //获取老的照片信息
                    Images oldAvatar = imageUploadService.findOldCinema_AvatarById(cinema.getAvatarId());
                    //获取头像的id
                    String avatarId =cinema.getAvatarId();
                    //设置avatarImage的属性
                    Images avatarImage = new Images(ImageName,Extension,relativePath);
                    //更新数据库信息
                    imageUploadService.addCinema_AvatarId(avatarImage,avatarId);
                    //删除原本的照片
                    removeImage(relativePath,oldAvatar.getFilename()+oldAvatar.getExtension());
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
                return "cinema图片信息保存失败";
            }
        }else if (obj instanceof ManageAcc){
            try{
                ManageAcc manageAcc = (ManageAcc) obj;
                if (manageAcc.getAvatar_id()==null||manageAcc.getAvatar_id().isEmpty()){
                    RandomStringGenerator rm=new RandomStringGenerator();
                    //获取唯一随机AvatarId
                    String uid=rm.generateUniqueRandomStringAvatar(cinemaRepository);

                    //向管理员用户添加AvatarId
                    manageAccService.updateManageAccAvatarId(accName,uid);
                    //头像
                    Images avatarImage = new Images(uid,ImageName,Extension,relativePath);
                    //向数据库表manage_avatar插入 avatarImage
                    imageUploadService.addManage_avatar(avatarImage);
                }else {
                    //获取老的照片信息
                    Images oldAvatar = imageUploadService.findOldManage_AvatarById(manageAcc.getAvatar_id());
                    //获取头像的id
                    String avatarId =manageAcc.getAvatar_id();
                    //设置avatarImage的属性
                    Images avatarImage = new Images(ImageName,Extension,relativePath);
                    //更新数据库信息
                    imageUploadService.addManage_AvatarById(avatarImage,avatarId);
                    //删除原本的照片
                    removeImage(relativePath,oldAvatar.getFilename()+oldAvatar.getExtension());
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
                return "cinema图片信息保存失败";
            }
        }else {
            return "obj不是用户";
        }

        return "图片信息保存成功";
    }


    /**
     * 保存电影图片
     * @param image 图片
     * @param movie_id 电影的id
     * @return String
     */
    public String saveMovieImage(
            MultipartFile image,
            int movie_id,
            MovieMapper movieMapper,
            ImageUploadService imageUploadService
    ){
        String relativePath = "src/main/resources/static/images/movieImage";
        try {
            System.out.println("image: "+image);
            System.out.println("movie_id: "+movie_id);
            //保存图片到指定路径并获取图片名称
            String newFilename=saveImageToPath(image,relativePath);
            System.out.println("newFilename:"+newFilename);


            //获取图片名称ImageName和后缀Extension
            String ImageName = newFilename.substring(0, newFilename.lastIndexOf("."));
            String Extension = newFilename.substring(newFilename.lastIndexOf("."));

            //获取唯一随机ImageId
            RandomStringGenerator rm=new RandomStringGenerator();
            String uid=rm.generateUniqueRandomStringImage(movieMapper);
            System.out.println("uid:"+uid);

            //根据Id获取movie
            Movie movie = movieMapper.findById(movie_id);
            System.out.println("movie:"+movie);

            //判断ImageId是否有值
            if (movie.getImageId()==null||movie.getImageId().equals("")){
                //更新ImageId
                imageUploadService.updateMovieImageId(movie_id,uid);
                System.out.println("更新ImageId成功！");

                //把ImageId和newFilename插入image表
                Images movieImage = new Images(uid,ImageName,Extension,relativePath);
                imageUploadService.addImages(movieImage);
                System.out.println("ImageId和newFilename插入image表成功！");
            }else {
                //获取已存在的Image
                Images oldMovie_imageById = imageUploadService.findOldMovie_ImageById(movie.getImageId());
                System.out.println("oldMovie_imageById："+oldMovie_imageById);
                //把ImageId和newFilename插入image表
                Images movieImage = new Images(ImageName,Extension,relativePath);
                imageUploadService.updateImageById(movieImage,oldMovie_imageById.getId());
                System.out.println("ImageId和newFilename插入image表成功！");

                //删除原本的照片
                removeImage(relativePath,oldMovie_imageById.getFilename()+oldMovie_imageById.getExtension());
                System.out.println("删除原本的照片成功！");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "图片上传失败!";
        }
        return "图片上传成功！";
    }

    /**
     * 把前端的图片存到src/main/resources/static/images/avatarImage路径
     * @param image 前端formData数据中必须有个名为image的键值对
     * @return 判断图片是否上传成功
     */
    @PostMapping("/uploadImage")
    public String uploadImage(
            @RequestParam("image") MultipartFile image,
            @RequestParam("accName") String accName,
            @RequestParam("type") String type
    ) {
        switch (type){
            case "userAvatar":
                //判断用户是否存在
                if(!userAccService.isUsername(accName)){
                    return "用户不存在！！！";
                }
                // 检查图片是否为空
                if (image.isEmpty()) {
                    return "图片为空";
                }else {
                    String relativePath = "src/main/resources/static/images/avatarImage/userAvatar";
                    try {
                        //保存图片到指定路径并获取图片名称
                        String newFilename=saveImageToPath(image,relativePath);
                        //获取账号为accName的用户
                        User user=userAccMapper.findUserByName(accName);
                        System.out.println(saveUserAvatar(newFilename,accName,relativePath,user));
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                        return "图片上传失败!";
                    }
                }
                break;
            case "cinemaAvatar":
                //判断用户是否存在
                if(!cinemaAccService.isUsername(accName)){
                    return "用户不存在！！！";
                }
                // 检查图片是否为空
                if (image.isEmpty()) {
                    return "图片为空";
                }else {
                    String relativePath = "src/main/resources/static/images/avatarImage/cinemaAvatar";
                    try {
                        //保存图片到指定路径并获取图片名称
                        String newFilename=saveImageToPath(image,relativePath);
                        //查找cinema用户中账号为accName的id
                        int cinema_id = cinemaAccMapper.findCinemaId(accName);
                        Cinema cinema = cinemaRepository.findById(cinema_id).orElse(null);
                        System.out.println(saveUserAvatar(newFilename,accName,relativePath,cinema));
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                        return "图片上传失败!";
                    }
                }
                break;
            case "manageAvatar":
                if(!manageAccService.isUsername(accName)){
                    return "用户不存在！！！";
                }
                // 检查图片是否为空
                if (image.isEmpty()) {
                    return "图片为空";
                }else {
                    String relativePath = "src/main/resources/static/images/avatarImage/manageAvatar";
                    try {
                        //保存图片到指定路径并获取图片名称
                        String newFilename=saveImageToPath(image,relativePath);
                        //查找cinema用户中账号为accName的id
                        ManageAcc manageAcc = manageAccService.findManageAcc(accName);

                        System.out.println(saveUserAvatar(newFilename,accName,relativePath,manageAcc));
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                        return "图片上传失败!";
                    }
                }
                break;
            default:
                return "不是用户!";
        }
        return "图片上传成功!";
    }
    /**
     * 根据图片ID获取图片URL
     * @param request 前端数据
     * @return 图片URL
     */
    @PostMapping("/imageIdByUrl")
    public ResponseEntity<String> getImageUrl(@RequestBody String request) {
        JSONObject jsonObject = JSON.parseObject(request);
        String dataStr = jsonObject.getString("data");
        JSONObject data = JSON.parseObject(dataStr);
        String userAccName = data.getString("userAccName");
        String type = data.getString("type");
        String imageId="";
        Images images= null;
        try {
            switch (type) {
                case "movie":
                    imageId = movieMapper.findByName(userAccName).getImageId();
                    images = movieMapper.findImagesById(imageId);
                    break;
                case "userAvatar":
                    imageId = userAccMapper.findAvatarIdByName(userAccName);
                    images = userAccMapper.findAvatarById(imageId);
                    break;
                case "cinemaAvatar":
                    int cinema_id = cinemaAccMapper.findCinemaId(userAccName);
                    imageId = cinemaRepository.findById(cinema_id).orElse(null).getAvatarId();
                    images = cinemaAccMapper.findAvatarById(imageId);
                    break;
                case "manageAvatar":
                    imageId = manageAccService.findManageAcc(userAccName).getAvatar_id();
                    images = imageUploadService.findOldManage_AvatarById(imageId);
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        String path=images.getPath();
        String fileName=images.getFilename();
        String extension=images.getExtension();

        // 根据图片标识符获取图片文件路径
        String imagePath = path.replace("src/main/resources/static/","")+"/" + fileName + extension;

        // 构建图片URL
        String imageUrl = "http://localhost:8181/" + imagePath;

        // 返回图片URL给前端
        return ResponseEntity.ok(imageUrl);
    }


    @PostMapping("/getImage")
    public String getImage(@RequestParam("image") MultipartFile image){
        if (image.isEmpty()){
            return "图片上传失败！";
        }
        return "图片上传成功！！！";
    }


}
