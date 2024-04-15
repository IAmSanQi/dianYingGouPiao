package com.example.dianyinggoupiao_springboot.server.impl;

import com.example.dianyinggoupiao_springboot.entity.Images;
import com.example.dianyinggoupiao_springboot.mapper.CinemaAccMapper;
import com.example.dianyinggoupiao_springboot.mapper.ManageAccMapper;
import com.example.dianyinggoupiao_springboot.mapper.MovieMapper;
import com.example.dianyinggoupiao_springboot.mapper.UserAccMapper;
import com.example.dianyinggoupiao_springboot.repositroy.CinemaRepository;
import com.example.dianyinggoupiao_springboot.server.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageUploadServiceImpl implements ImageUploadService {
    @Autowired
    private UserAccMapper userAccMapper;
    @Autowired
    private CinemaAccMapper cinemaAccMapper;

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private ManageAccMapper manageAccMapper;

    //--------------------user----------------------
    @Override
    public void addUserAvatarId(String userName, String avatarId) {
        userAccMapper.addUserAvatarId(userName,avatarId);
    }

    @Override
    public void addUser_AvatarId(Images AvatarImage, String avatarId) {
        userAccMapper.addAvatarId(AvatarImage,avatarId);
    }

    @Override
    public Images findOldUser_AvatarById(String id) {
        return userAccMapper.findAvatarById(id);
    }

    @Override
    public void addUser_avatar(Images AvatarImage) {
        userAccMapper.addUser_avatar(AvatarImage);
    }


    //--------------------cinema----------------------
    @Override
    public void addCinema_avatar(Images AvatarImage) {
        cinemaAccMapper.addCinema_avatar(AvatarImage);
    }

    @Override
    public void addCinema_AvatarId(Images AvatarImage, String avatarId) {
        cinemaAccMapper.addAvatarId(AvatarImage,avatarId);
    }

    public Images findOldCinema_AvatarById(String id) {
        return cinemaAccMapper.findAvatarById(id);
    }

    //----------------------manage---------------------------
    @Override
    public void addManageAvatarId(String userName, String avatarId) {

    }

    @Override
    public void addManage_avatar(Images AvatarImage) {
        manageAccMapper.addManage_avatar(AvatarImage);
    }

    @Override
    public void addManage_AvatarById(Images AvatarImage, String avatarId) {
        manageAccMapper.updateManage_avatarById(AvatarImage,avatarId);
    }

    @Override
    public Images findOldManage_AvatarById(String id) {
        return manageAccMapper.findManage_AvatarById(id);
    }

    @Override
    public void updateMovieImageId(int id, String imageId) {
        movieMapper.updateMovieImageId(id,imageId);
    }

    @Override
    public void addImages(Images movieImage) {
        movieMapper.addImages(movieImage);
    }

    @Override
    public void updateImageById(Images image, String id) {
        movieMapper.updateImageById(image,id);
    }

    @Override
    public Images findOldMovie_ImageById(String id) {
        return movieMapper.findImagesById(id);
    }


}
