package com.example.dianyinggoupiao_springboot.server.impl;

import com.example.dianyinggoupiao_springboot.entity.UserAcc;
import com.example.dianyinggoupiao_springboot.entity.Account;
import com.example.dianyinggoupiao_springboot.entity.User;
import com.example.dianyinggoupiao_springboot.mapper.UserAccMapper;
import com.example.dianyinggoupiao_springboot.server.UserAccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAccServiceImpl implements UserAccService {

    @Autowired
    private UserAccMapper userAccMapper;
    @Override
    public List<UserAcc> findAccAll() {
        return userAccMapper.findAccAll();
    }

    @Override
    public List<User> findUserAll() {

        return userAccMapper.findUserAll();
    }

    @Override
    public User findUserByName(String name) {
        User user = null;
        try {
            System.out.println(name);
            user=userAccMapper.findUserByName(name);
            System.out.println("用户已登录");
            return user;
        }catch(Exception e){
            System.out.println("用户未登录");
        }
       return null;
    }

    @Override
    public String deleteAccName(String name) {
        userAccMapper.deleteAccName(name);
        String msg="";
        Account account= userAccMapper.findAccByName(name);
        if (account==null){
            msg="删除成功";
        }
        return msg;
    }

    @Override
    public Account findAccByName(String name) {
        return userAccMapper.findAccByName(name);
    }

    @Override
    public String addUserName(User user) {
        try {
            userAccMapper.addUserName(user);
            return "User数据插入成功!";
        } catch (Exception e) {
            System.out.println("User数据插入失败!!!");
            throw new RuntimeException(e);
        }
    }


    /**
     *获取type=1 的pageSize条数据
     * @param currentPage   当前页
     * @param pageSize      总页数
     * @return              返回user表和acc表 type=1的pageSize条数据
     */
    @Override
    public List<Account> findAccAndUserAll(String currentPage,String pageSize) {
//        Integer currentPageInt = Integer.parseInt(currentPage);
//        Integer pageSizeInt = Integer.parseInt(pageSize);
//        currentPageInt=(currentPageInt-1)*pageSizeInt;
//        Condition condition = new Condition(currentPageInt,pageSizeInt);
        List<Account> list= userAccMapper.findAccAndUserAll();
        List<Account> AccountList= new ArrayList<>();
        for (Account accounts:list) {
            if (accounts.getUser()!=null){
                if (accounts.getUser().getSex().equals("0")){
                    accounts.getUser().setSex("女");
                }else {
                    accounts.getUser().setSex("男");
                }
            }
            if (accounts.getType().equals("1")){
                AccountList.add(accounts);
            }
        }
        if (AccountList==null || AccountList.size()==0){
            throw new RuntimeException("暂时还没有会员~");
        }
        return AccountList;
    }

    @Override
    public String addAccName(UserAcc userAcc) {

        userAccMapper.addAccName(userAcc);
        return "User数据插入成功";
    }

    /**
     * 向user表插入一条信息
     * @param user 插入的账号信息
     */
    @Override
    public void updateUser(User user) {
        if (user.getName()!=null){
            userAccMapper.updateUser(user,user.getName());
        }else {
            System.out.println("user账号为null");
        }
    }


    /**
     * 向user表和acc表插入一条信息
     * @param account acc表的信息
     * @return 返回 Account的实例
     */
    @Override
    public Account addAccAndUser( Account account) {
        UserAcc userAcc =new UserAcc();
        User user= new User();
        //把前端的account里acc的数据赋值给acc
        userAcc.setName(account.getName());
        userAcc.setPassword(account.getPassword());

        //把前端的account里user的数据赋值给User
        user.setUserName(account.getUser().getUserName());
        user.setMail(account.getUser().getMail());
        user.setPhone(account.getUser().getPhone());
        user.setSex(account.getUser().getSex());
        user.setAge(account.getUser().getAge());
        user.setName(account.getName());

        //插入数据到表acc和user里
        userAccMapper.addAccName(userAcc);
        userAccMapper.addUserName(user);
        return account;
    }

    /**
     * 判断用户是否存在
     * @param name 用户的账号
     * @return boolean
     */
    @Override
    public boolean isUsername(String name){
        List<User> users=userAccMapper.findUserAll();
        for (User user:users) {
            if (user.getName().equals(name)){
                return true;
            }
        }
        return false;
    }


}
