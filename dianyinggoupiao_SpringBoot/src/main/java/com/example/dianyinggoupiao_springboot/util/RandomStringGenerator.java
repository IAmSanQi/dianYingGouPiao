package com.example.dianyinggoupiao_springboot.util;

import com.example.dianyinggoupiao_springboot.entity.Cinema;
import com.example.dianyinggoupiao_springboot.entity.Movie;
import com.example.dianyinggoupiao_springboot.entity.User;
import com.example.dianyinggoupiao_springboot.mapper.CinemaAccMapper;
import com.example.dianyinggoupiao_springboot.mapper.MovieMapper;
import com.example.dianyinggoupiao_springboot.mapper.UserAccMapper;
import com.example.dianyinggoupiao_springboot.repositroy.CinemaRepository;

import java.util.List;
import java.util.Random;

public class RandomStringGenerator {
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH = 16;

    /**
     * 生成大小字母和数字组成的16位不同字符串
     * @return 字符串
     */
    public String RandomStringGenerator() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(LENGTH);

        for (int i = 0; i < LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    /**
     * 避免数据库中的标识与新的标识冲突（电影图片）
     * @param movieMapper bean
     * @return 字符串
     */
    public String generateUniqueRandomStringImage(MovieMapper movieMapper){
        String newString="";
        newString=RandomStringGenerator();
        if (movieMapper != null) {
            List<Movie> movie=movieMapper.findMovieAll();
            String[] mySqlImageId = new String[movie.size()];
            for (int i = 0; i < mySqlImageId.length-1; i++) {
                if (movie.get(i).getImageId()!=null){
                    mySqlImageId[i]=movie.get(i).getImageId();
                }else {
                    mySqlImageId[i]="";
                }
            }
            return ImageIdIsReplace(mySqlImageId,newString);

        }

        return newString;
    }

    /**
     * 避免数据库中User头像的标识与新的标识冲突（头像）
     * @param userAccMapper bean
     * @return 字符串
     */
    public String generateUniqueRandomStringAvatar(UserAccMapper userAccMapper){
        String newString="";
        newString=RandomStringGenerator();
        if (userAccMapper != null) {
            //获取所有user用户账号信息
            List<User> movie=userAccMapper.findUserAll();
            String[] mySqlAvatarId = new String[movie.size()];

            for (int i = 0; i < mySqlAvatarId.length-1; i++) {
                if (movie.get(i).getAvatarId()!=null){
                        mySqlAvatarId[i]=movie.get(i).getAvatarId();
                }else {
                    mySqlAvatarId[i]="";
                }
            }
            return ImageIdIsReplace(mySqlAvatarId,newString);
        }

        return newString;
    }

    /**
     * 避免数据库中Cinema头像的标识与新的标识冲突（头像）
     * @param cinemaRepository bean
     * @return 字符串
     */
    public String generateUniqueRandomStringAvatar(CinemaRepository cinemaRepository){
        String newString="";
        newString=RandomStringGenerator();
        if (cinemaRepository != null) {
            //获取所有cinema用户账号信息
            List<Cinema> cinemas=cinemaRepository.findAll();
            String[] mySqlAvatarId = new String[cinemas.size()];
            //把AvatarId赋值给mySqlAvatarId
            for (int i = 0; i < mySqlAvatarId.length-1; i++) {
                if (cinemas.get(i).getAvatarId()!=null){
                    mySqlAvatarId[i]=cinemas.get(i).getAvatarId();
                }else {
                    mySqlAvatarId[i]="";
                }
            }
            return ImageIdIsReplace(mySqlAvatarId,newString);
        }

        return newString;
    }


    /**
     * 防止重复的标识
     * @param mySqlImageId 某个表的所有图片唯一标识
     * @param ImageId 新的图片标识
     * @return 不重复的AvatarId
     */
    public String ImageIdIsReplace(String[] mySqlImageId,String ImageId){
        //判断是否有重复的AvatarId
        if (mySqlImageId.length>1){
            for (int i = 0; i <mySqlImageId.length-1 ; i++) {
                if (ImageId.equals(mySqlImageId[i])){
                    ImageId=RandomStringGenerator();
                    //开始从头判断
                    i=0;
                }
            }
        }else {
            return ImageId;
        }
        return ImageId;
    }


}
