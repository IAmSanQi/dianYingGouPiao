package com.example.dianyinggoupiao_springboot.server;

import com.example.dianyinggoupiao_springboot.entity.Images;
import org.apache.ibatis.annotations.Param;

public interface ImageUploadService {
    //---------------------------------user_avatar------------------------------
    public void addUserAvatarId(String userName,String avatarId);
    public void addUser_avatar(Images AvatarImage);
    public void addUser_AvatarId(Images AvatarImage, String avatarId);
    public Images findOldUser_AvatarById(String id);

    //---------------------------cinema_avatar-----------------------------------
    public void addCinema_avatar(Images AvatarImage);
    public void addCinema_AvatarId(Images AvatarImage, String avatarId);
    public Images findOldCinema_AvatarById(String id);

    //---------------------------manage_avatar-----------------------------------

    public void addManageAvatarId(String userName,String avatarId);
    public void addManage_avatar(Images AvatarImage);
    public void addManage_AvatarById(Images AvatarImage, String avatarId);
    public Images findOldManage_AvatarById(String id);

    //---------------------------movie的image-----------------------------------

    public void updateMovieImageId(int id,String imageId);
    public void addImages(Images movieImage);
    public void updateImageById(Images AvatarImage, String avatarId);
    public Images findOldMovie_ImageById(String id);


}
