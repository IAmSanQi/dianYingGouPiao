package com.example.dianyinggoupiao_springboot.controller;

import com.example.dianyinggoupiao_springboot.entity.*;
import com.example.dianyinggoupiao_springboot.server.CinemaAccService;
import com.example.dianyinggoupiao_springboot.server.ManageAccService;
import com.example.dianyinggoupiao_springboot.server.UserAccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/acc")
public class AccController {

    @Autowired
    private UserAccService userAccService;

    @Autowired
    private ManageAccService manageAccService;
    @Autowired
    private CinemaAccService cinemaAccService;



    /**
     * 查询UserAcc表所有数据
     * @return 返回acc表所有数据
     */
    @GetMapping("/findUserAccAll")
    public List<UserAcc> findAccAll() {
        return userAccService.findAccAll();
    }

    /**
     * 查询CinemaAcc表所有数据
     * @return 返回acc表所有数据
     */
    @GetMapping("/findCinemaAccAll")
    public List<CinemaAcc>findCinemaAccAll(){
        return cinemaAccService.findCinemaAcc();
    }
    /**
     * 查询ManageAcc表所有数据
     * @return 返回acc表所有数据
     */
    @GetMapping("/findManageAccAll")
    public List<ManageAcc>findManageAccAll(){
        return manageAccService.findManageAcc();
    }
    /**
     * 查询acc和user表所有数据
     * @return 返回acc和user表所有数据
     */
    @GetMapping("/findUserAll")
    public List<User>  findUserAll() {
        return userAccService.findUserAll();
    }

    /**
     * 查询acc和user表所有数据
     * @return 返回acc和user表所有数据
     */
    @GetMapping("/findAccAndUserAll")
    public List<Account> findAccAndUserAll(String currentPage,String pageSize) {
        return userAccService.findAccAndUserAll(currentPage,pageSize);
    }

    /**
     * 查询user表中对应的name
     * @return 返回user表中对应的name
     */
    @GetMapping("/findUserByName")
    public User findUserByName(String name) {
        return userAccService.findUserByName(name);
    }

    /**
     * 查询Acc表中对应的name
     * @return 返回Acc表中对应的name的记录
     */
    @GetMapping("/findAccByName")
    public Account findAccByName(String name) {
        return userAccService.findAccByName(name);
    }

    /**
     *删除表Acc指定的某条数据
     * @param name 要删除的数据
     * @return  返回一个提示消息
     */
    @GetMapping("/deleteAccName")
    public String deleteAccName(String name) {
        return userAccService.deleteAccName(name);
    }

    /**
     * 向user表和acc表插入一条信息
     * @param account acc和User表的信息
     * @return 返回 字符串
     */
    @PostMapping("/addAccAndUserName")
    public String  addAccAndUserName(@RequestBody Account account) {
        List<UserAcc> userAcc =findAccAll();
        boolean boo=false;

        if (account.getName()==null){
            System.out.println("数据为空");
            return "账号不能为空";
        }else{
            for (UserAcc accs: userAcc) {
                if (Objects.equals(accs.getName(), account.getName())){
                    boo=true;
                    break;
                }
            }
        }
        if (boo) {
            System.out.println("用户已存在");
            return "用户已存在";
        }else {
            userAccService.addAccAndUser(account);
            return "插入成功";
        }

    }

    /**
     * 插入一条User数据
     * @param user user表
     * @return boolean
     */
    @PostMapping("/updateUser")
    public boolean updateUser(@RequestBody User user){
        if (user!=null){
            userAccService.updateUser(user);
            return true;
        }else {
            System.out.println("user为null");
        }
        return false;
    }

}
