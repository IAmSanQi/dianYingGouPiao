package com.example.dianyinggoupiao_springboot.mapper;

import com.example.dianyinggoupiao_springboot.entity.Images;
import com.example.dianyinggoupiao_springboot.entity.Movie;
import com.example.dianyinggoupiao_springboot.entity.MovieAllInfo;
import com.example.dianyinggoupiao_springboot.entity.Movie_info;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MovieMapper {
    /**
     * 查询影片表中的所有影片
     * @return List<Movie>
     */
    @Select("select * from movie")
    public List<Movie> findMovieAll();

    /**
     * 根据电影id查询movie中的信息
     * @param id 电影名称
     * @return Movie
     */
    @Select("select * from movie where id=#{id}")
    public Movie findById(@Param("id") int id);
    /**
     * 根据电影名称查询movie中的信息
     * @param name 电影名称
     * @return Movie
     */
    @Select("select * from movie where name=#{name}")
    public Movie findByName(@Param("name") String name);

    /**
     * 更新movie表的imageId；
     * @param id 要更新的某条数据的id
     * @param imageId imageId更新后的值
     */
    @Update("update movie set imageId=#{imageId} where id=#{id}")
    public void updateMovieImageId(@Param("id") int id,@Param("imageId") String imageId);

    /**
     * 向电影片库添加电影名称
     * @param movieImage movie类型
     */
    @Insert("insert into images(id, filename, extension, path) values(#{id}, #{filename}, #{extension}, #{path})")
    public void addImages(Images movieImage);


    /**
     * 查询影片信息表中的所有影片信息
     * @return List<Movie_info>
     */
    @Select("select * from movie_info")
    public List<Movie_info> findMovieInfoAll();

    /**
     * 根据name查询影片信息表中对应影片的信息
     * @return List<Movie_info>
     */
    @Select("select * from movie_info where name = #{name}")
    public Movie_info findMovieInfoByName(String name);

    /**
     * 根据imageId查询图片表中对应图片的信息
     * @return String
     */
    @Select("select * from images where images.id = #{imageId}")
    public Images findImagesById(String imageId);

    /**
     * 查询所有的电影以及电影信息
     * @return List<MovieAllInfo>
     */
    @Select("select * from movie")
    @Results(id = "MovieAllInfoSelectMap", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "name", property = "movie_info", one = @One(select = "com.example.dianyinggoupiao_springboot.mapper.MovieMapper.findMovieInfoByName", fetchType = FetchType.EAGER))
    })
    public List<MovieAllInfo> findMovieAllInfo();

    /**
     * 查询名称为name的电影以及电影信息
     * @return List<MovieAllInfo>
     */
    @Select("<script>" +
            "select * from movie where name IN " +
            "<foreach item='name' collection='nameArr' open='(' separator=',' close=')'>" +
            "#{name}" +
            "</foreach>" +
            "</script>")
    @Results(id = "MovieAllInfoSelectByNameArr", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "name", property = "movie_info", one = @One(select = "com.example.dianyinggoupiao_springboot.mapper.MovieMapper.findMovieInfoByName", fetchType = FetchType.EAGER))
    })
    public List<MovieAllInfo> findMovieAllInfoByNames(@Param("nameArr") List<String> nameArr);

    /**
     * 向电影片库添加电影名称
     * @param movie movie类型
     */
    @Insert("Insert into movie(name,ticketNumber,score,see,isBlockbuster,isRelease) value(" +
            "#{name},#{ticketNumber},#{score},#{see},#{isBlockbuster},#{isRelease})")
    public void addMovie(Movie movie);

    /**
     * 向电影信息库添加电影信息
     * @param movie_info movie_info类型
     */
    @Insert("Insert into movie_info(name,type,time,play_time,location,introduce,Starring) value(" +
            "#{name},#{type},#{time},#{play_time},#{location},#{introduce},#{Starring})")
    public void addMovieInfo(Movie_info movie_info);

    /**
     * 修改电影信息库中某一个电影信息
     * @param movie_info movie_info类型
     */
    @Update("UPDATE movie_info SET type=#{type}, play_time=#{play_time}, location=#{location}, " +
            "introduce=#{introduce}, Starring=#{Starring} WHERE name=#{name}")
    public void updateMovieInfo(Movie_info movie_info);



    /**
     * 更新 images 表中 id 的数据
     * @param Image 图片信息
     * @param id 唯一标识
     */
    @Update("update images set filename=#{Image.filename}, extension=#{Image.extension}, path=#{Image.path} where id=#{Id}")
    public void updateImageById(@Param("Image") Images Image, @Param("Id") String id);

    /**
     * 根据影片名称删除影片
     * @param movieName 影片名称
     */
    @Delete("delete from movie where name=#{movieName}")
    public void delMovieByName(@Param("movieName") String movieName);

    /**
     * 根据影片名称删除影片信息
     * @param movieName 影片名称
     */
    @Delete("delete from movie_info where name=#{movieName}")
    public void delMovieInfoByName(@Param("movieName") String movieName);
}
