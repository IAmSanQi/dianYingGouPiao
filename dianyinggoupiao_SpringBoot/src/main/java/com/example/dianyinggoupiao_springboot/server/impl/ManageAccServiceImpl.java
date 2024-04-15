package com.example.dianyinggoupiao_springboot.server.impl;

import com.example.dianyinggoupiao_springboot.entity.ManageAcc;
import com.example.dianyinggoupiao_springboot.mapper.ManageAccMapper;
import com.example.dianyinggoupiao_springboot.server.ManageAccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageAccServiceImpl implements ManageAccService {
    @Autowired
    private ManageAccMapper manageAccMapper;

    @Override
    public List<ManageAcc> findManageAcc() {
        return manageAccMapper.findManageAcc();
    }

    @Override
    public ManageAcc findManageAcc(int id) {
        return manageAccMapper.findManageAccById(id);
    }

    @Override
    public ManageAcc findManageAcc(String manageAcc) {
        return manageAccMapper.findManageAccByName(manageAcc);
    }

    @Override
    public int findIdManageAccByName(String manageAcc) {
        return manageAccMapper.findIdManageAccByName(manageAcc);
    }

    @Override
    public boolean updateManageAccAvatarId(String manageAcc, String avatar_Id) {
        try {
            manageAccMapper.updateManageAccAvatarId(manageAcc,avatar_Id);
            return true;
        } catch (Exception e) {
            System.out.println("更新manage头像uid失败");
            return false;
        }
    }

    @Override
    public boolean isUsername(String accName) {
        List<ManageAcc> manageAcc = manageAccMapper.findManageAcc();
        for (ManageAcc acc : manageAcc) {
            if (acc.getName().equals(accName)){
                return true;
            }
        }
        return false;
    }
}
