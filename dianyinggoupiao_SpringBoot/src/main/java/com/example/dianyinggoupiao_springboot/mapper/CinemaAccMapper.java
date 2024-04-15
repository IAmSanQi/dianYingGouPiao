package com.example.dianyinggoupiao_springboot.mapper;

import com.example.dianyinggoupiao_springboot.entity.Cinema;
import com.example.dianyinggoupiao_springboot.entity.CinemaAcc;
import com.example.dianyinggoupiao_springboot.entity.Images;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CinemaAccMapper {
    @Select("select * from cinema_acc")
    public List<CinemaAcc> findCinemaAcc();

    /**
     * 查询影院对应的id号
     * @param cinemaName 影院账号
     * @return 影院id
     */
    @Select("select cinema_id from cinema_acc where name = #{cinemaName}")
    public int findCinemaId(String cinemaName);


    /**
     * 根据影院id查询影院中所有电影的name
     * @param cinema_id cinema_id 影院id
     * @return 当前影院中所有电影名称
     */
    @Select("select m.name " +
            "from cinema_movie_table cmt " +
            "join movie m on cmt.movie_id=m.id " +
            "where cmt.cinema_id=#{cinema_id}")
    public List<String> findMovieById(int cinema_id);


    //-------------------------------cinema_avatar------------------------------------

    /**
     * 根据id查找cinema_avatar
     * @param id 标识
     * @return Images
     */
    @Select("select * from cinema_avatar where id=#{id}")
    public Images findAvatarById(@Param("id") String id);

    /**
     * 向 cinema_avatar 表插入一条信息
     * @param AvatarImage 插入的信息
     * 提示：必须是cinema表中avatarId已经有的值
     */
    @Insert("insert into cinema_avatar(id,filename,extension,path) values(#{id},#{filename},#{extension},#{path})")
    public void addCinema_avatar(Images AvatarImage);

    /**
     * 更新 user_avatar 表中id为avatarId的数据
     * @param AvatarImage 图片信息
     * @param avatarId 唯一标识
     */
    @Update("update cinema_avatar set filename=#{AvatarImage.filename}, extension=#{AvatarImage.extension}, path=#{AvatarImage.path} where id=#{avatarId}")
    public void addAvatarId(@Param("AvatarImage") Images AvatarImage, @Param("avatarId") String avatarId);
}
