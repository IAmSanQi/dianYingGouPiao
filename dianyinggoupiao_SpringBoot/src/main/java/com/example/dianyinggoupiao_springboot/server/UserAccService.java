package com.example.dianyinggoupiao_springboot.server;

import com.example.dianyinggoupiao_springboot.entity.UserAcc;
import com.example.dianyinggoupiao_springboot.entity.Account;
import com.example.dianyinggoupiao_springboot.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserAccService {
    public List<UserAcc> findAccAll();
    public List<Account> findAccAndUserAll(String currentPage,String pageSize);
    public List<User> findUserAll();

    public User findUserByName(String name);

    public String deleteAccName(String name);
    public Account findAccByName(String name);

    public String addUserName(User user);
    public String addAccName(UserAcc userAcc);

    public void updateUser(User user);

    public Account addAccAndUser(Account account);

    public boolean isUsername(String name);
}
