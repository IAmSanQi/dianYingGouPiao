package com.example.dianyinggoupiao_springboot.server;

import com.example.dianyinggoupiao_springboot.entity.ManageAcc;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ManageAccService {
    public List<ManageAcc> findManageAcc();
    public ManageAcc findManageAcc(int id);
    public ManageAcc findManageAcc(String manageAcc);
    public int findIdManageAccByName(String manageAcc);
    public boolean updateManageAccAvatarId(String manageAcc,String avatar_Id);

    public boolean isUsername(String accName);
}
