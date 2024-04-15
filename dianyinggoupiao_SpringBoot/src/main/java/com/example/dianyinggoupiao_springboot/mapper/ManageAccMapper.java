package com.example.dianyinggoupiao_springboot.mapper;

import com.example.dianyinggoupiao_springboot.entity.Images;
import com.example.dianyinggoupiao_springboot.entity.ManageAcc;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ManageAccMapper {
    /**
     * 查找管理员账号数据
     * @return List<ManageAcc>
     */
    @Select("select * from manage_acc")
    public List<ManageAcc> findManageAcc();

    /**
     * 根据id查找管理员账号数据
     * @param id 管理员id
     * @return ManageAcc
     */
    @Select("select * from manage_acc where manage_id=#{id}")
    public ManageAcc findManageAccById(@Param("id") int id);

    /**
     * 根据 manageName 查找管理员账号数据
     * @param manageAcc 管理员账户
     * @return ManageAcc
     */
    @Select("select * from manage_acc where name=#{name}")
    public ManageAcc findManageAccByName(@Param("name") String manageAcc);

    /**
     * 根据 manageName 查找管理员账号数据
     * @param manageAcc 管理员账户
     * @return ManageAcc
     */
    @Select("select manage_id from manage_acc where name=#{name}")
    public int findIdManageAccByName(@Param("name") String manageAcc);

    /**
     * 更新 manage_acc 表中的 avatar_id
     * @param manageAcc 管理员的账户
     * @param avatar_Id uid
     */
    @Update("update manage_acc set avatar_id=#{avatar_Id} where name=#{manageAcc} ")
    public void updateManageAccAvatarId(@Param("manageAcc") String manageAcc,@Param("avatar_Id") String avatar_Id);


    //-------------------------------manage_avatar----------------------------------
    /**
     * 向 manage_avatar 表插入一条信息
     * @param AvatarImage 插入的信息
     * 提示：必须是manage表中avatar_id已经有的值
     */
    @Insert("insert into manage_avatar(id,filename,extension,path) values(#{id},#{filename},#{extension},#{path})")
    public void addManage_avatar(Images AvatarImage);

    @Update("update manage_avatar set filename=#{AvatarImage.filename}, extension=#{AvatarImage.extension}, path=#{AvatarImage.path} where id=#{avatarId}")
    public void updateManage_avatarById(@Param("AvatarImage") Images AvatarImage, @Param("avatarId") String avatarId);


    @Select("select * from manage_avatar where id=#{id}")
    public Images findManage_AvatarById(@Param("id") String id);
}
