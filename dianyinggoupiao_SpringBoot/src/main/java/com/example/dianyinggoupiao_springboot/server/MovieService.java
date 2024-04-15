package com.example.dianyinggoupiao_springboot.server;


import com.example.dianyinggoupiao_springboot.entity.Movie;
import com.example.dianyinggoupiao_springboot.entity.MovieAllInfo;
import com.example.dianyinggoupiao_springboot.entity.Movie_info;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public interface MovieService {
    /**
     * 查询movie表的数据
     */
    public List<Movie> findMovieAll();
    public Movie findById(int id);
    public Movie findByName(String name);
    public List<Movie> MovieIsBlockbuster();
    public List<Movie> MovieTicket(int num);
    public List<Movie> MovieSee(int num) ;
    public List<Movie> MovieScore(int num);
    public List<Movie> findMovieNameSomeEqual(String movieName);

    /**
     * 查询movie_info表的数据
     */
    public List<Movie_info> findMovieInfoAll();
    public List<MovieAllInfo> findMovieAllInfo();
    public MovieAllInfo findMovieAllInfoByName(String movieName);

    public List<MovieAllInfo> findByMovieNameInfo(String Str);

    public List<MovieAllInfo> findByTypeInfo(String type,String location,String year,String radio,String index);

    public List<MovieAllInfo> MovieAllInfoBlockbuster();

    public List<MovieAllInfo> MovieAllInfoRelease();

    public int countMovieBlockbuster();
    public int countMovieRelease();

    /**
     * 按时间排序
     * @param list List<MovieAllInfo>
     * @return List<MovieAllInfo>
     */
    public List<MovieAllInfo> TimeSort(List<MovieAllInfo> list);
    /**
     * 按评分排序
     * @param list List<MovieAllInfo>
     * @return List<MovieAllInfo>
     */
    public List<MovieAllInfo> ScoreSort(List<MovieAllInfo> list);
    /**
     * 按票房排序
     * @param list List<MovieAllInfo>
     * @return List<MovieAllInfo>
     */
    public List<MovieAllInfo> TicketSort(List<MovieAllInfo> list);
    /**
     * 按想看的人数排序
     * @param list List<MovieAllInfo>
     * @return List<MovieAllInfo>
     */
    public List<MovieAllInfo> SeeSort(List<MovieAllInfo> list);
    /**
     * 按播放时长排序
     * @param list List<MovieAllInfo>
     * @return List<MovieAllInfo>
     */
    public List<MovieAllInfo> PlayTimeSort(List<MovieAllInfo> list);

    /**
     *根据多条name查询对应的电影信息
     * @param nameArr 多条name
     * @return List<MovieAllInfo>
     */
    public List<MovieAllInfo> findMovieAllInfoByNames( List<String> nameArr);

    public void addMovie(Movie movie);
    public void addMovieInfo(Movie_info movie_info);
    public void updateMovieInfo(Movie_info movie_info);

    public void delMovie(int movie_id,String movieName);

}
