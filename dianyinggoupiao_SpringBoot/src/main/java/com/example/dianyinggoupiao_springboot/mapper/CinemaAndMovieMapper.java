package com.example.dianyinggoupiao_springboot.mapper;

import com.example.dianyinggoupiao_springboot.entity.MovieAllInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CinemaAndMovieMapper {
    /**
     * 查找某个影院中的所有影片
     * @param cinema_id 影院的id号
     * @return 所有影片
     */
    @Select("select m.*,mi.* " +
            "from cinema_movie_table mc " +
            "JOIN movie m ON mc.movie_id=m.id " +
            "JOIN movie_info mi ON m.name=mi.name " +
            "where mc.cinema_id = #{cinema_id}")
    @Result(property = "movie_info", column = "name",
            one = @One(
                    select = "com.example.dianyinggoupiao_springboot.mapper.MovieMapper.findMovieInfoByName",
                    fetchType = FetchType.EAGER
            )
    )
    public List<MovieAllInfo> findCinemaAllMovieInfo(int cinema_id);

    /**
     * 删除某个影院中的某个影片
     * @param cinema_id 影片id
     * @param movie_id 影院id
     */
    @Delete("delete from cinema_movie_table mc where mc.cinema_id=#{cinema_id} and mc.movie_id=#{movie_id}")
    public void delCinemaAndMovie(int cinema_id,int movie_id);

    /**
     * 插入某个影院中没有的新电影
     * @param cinema_id 影院id
     * @param movie_id 电影id
     */
    @Insert("Insert into cinema_movie_table(cinema_id,movie_id) value(#{cinema_id},#{movie_id})")
    public void addCinemaAndMovie(@Param("cinema_id") int cinema_id,@Param("movie_id") int movie_id);

    /**
     * 根据id删除影院和影片关联
     * @param movie_id 影片id
     */
    @Delete("delete from cinema_movie_table where movie_id=#{movie_id}")
    public void delByMovie_id(@Param("movie_id") int movie_id);

}
