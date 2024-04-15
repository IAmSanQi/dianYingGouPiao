package com.example.dianyinggoupiao_springboot.mapper;

import com.example.dianyinggoupiao_springboot.entity.Images;
import com.example.dianyinggoupiao_springboot.entity.UserAcc;
import com.example.dianyinggoupiao_springboot.entity.Account;
import com.example.dianyinggoupiao_springboot.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserAccMapper {

    //---------------------------------------user------------------------------------------
    /**
     * 查询user表所以数据
     * @return 返回user表所以数据
     */
    @Select("select * from user")
    public List<User> findUserAll();

    /**
     * 根据账号查询avatarId
     * @param name 账号
     * @return avatarId
     */
    @Select("select avatarId from user where name=#{name}")
    public String findAvatarIdByName(@Param("name") String name);


    /**
     * 查询user表用户的账号为name所有数据
     * @param name 用户的账号
     * @return 返回user表用户的账号为name所有数据
     */
    @Select("select * from user where name = #{name}")
    public User findUserByName(@Param("name") String name);

    /**
     * 向user表插入一条信息
     * @param user 插入的账号信息
     */
    @Insert("insert into user(name,userName,age,sex,phone,mail,birthday,area,avatarId) values(#{name},#{userName},#{age},#{sex},#{phone},#{mail},#{birthday},#{area},#{avatarId})")
    public void addUserName(User user);

    /**
     * 向user表插入一条信息
     * @param user 插入的账号信息
     * @param name 账号
     */
    @Update("update user set " +
            "userName=#{user.userName}," +
            "age=#{user.age}," +
            "sex=#{user.sex}," +
            "phone=#{user.phone}," +
            "mail=#{user.mail}," +
            "birthday=#{user.birthday}," +
            "area=#{user.area}," +
            "avatarId=#{user.avatarId} " +
            "where name=#{name}")
    public void updateUser(@Param("user") User user,@Param("name") String name);


    /**
     * 向当前用户表更新一条头像唯一标识信息
     * @param userName 账号
     * @param avatarId 唯一标识
     */
    @Update("update user set avatarId=#{avatarId} where name=#{userName}")
    public void addUserAvatarId(@Param("userName")String userName,@Param("avatarId")String avatarId);


    //-------------------------------------user_acc-------------------------------------------

    /**
     * 查询user_acc表所以数据
     * @return 返回user_acc表所以数据
     */
    @Select("select * from user_acc")
    public List<UserAcc> findAccAll();

    /**
     * 查询acc表用户的账号为name所以数据
     * @param name 用户的账号
     * @return 返回user_acc表用户的账号为name所有数据
     */
    @Select("select * from user_acc where name = #{name}")
    public Account findAccByName(String name);

    /**
     * 查询user_acc表和name表所有数据
     * @return 返回user_acc表和name表所有数据
     */
    @Select("select * from user_acc")
    @Results(id = "AccAndUserSelectMap", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "name", property = "user", one = @One(select = "com.example.dianyinggoupiao_springboot.mapper.UserAccMapper.findUserByName", fetchType = FetchType.EAGER))
    })
    public List<Account> findAccAndUserAll();

    /**
     * 删除 user_acc表 账号为name的一条数据
     * @param name 账号
     * @return 返回一串提示信息
     */
    @Select("delete from user_acc where name = #{name}")
    public String deleteAccName(String name);

    /**
     * 向user_acc表插入一条信息
     * @param userAcc 插入的账号信息
     */
    @Insert("insert into user_acc(name,password) values(#{name},#{password})")
    public void addAccName(UserAcc userAcc);



    //-------------------------user_avatar表--------------------------------------
    /**
     * 根据id查找cinema_avatar
     * @param id 标识
     * @return Images
     */
    @Select("select * from user_avatar where id=#{id}")
    public Images findAvatarById(@Param("id") String id);
    /**
     * 更新 user_avatar 表中id为avatarId的数据
     * @param AvatarImage 头像图片的信息
     * @param avatarId 插入的账号信息
     */
    @Update("update user_avatar set filename=#{AvatarImage.filename}, extension=#{AvatarImage.extension}, path=#{AvatarImage.path} where id=#{avatarId}")
    public void addAvatarId(@Param("AvatarImage") Images AvatarImage, @Param("avatarId") String avatarId);
    /**
     * 向user_avatar表插入一条信息
     * @param AvatarImage 插入的信息
     * 提示：必须是user表中avatarId已经有的值
     */
    @Insert("insert into user_avatar(id,filename,extension,path) values(#{id},#{filename},#{extension},#{path})")
    public void addUser_avatar(Images AvatarImage);

}
