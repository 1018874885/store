package com.cy.store.mapper;

import com.cy.store.entity.User;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

//@SpringBootTest: 标注当前的类是一个测试类，不会随同项目一起打包
@SpringBootTest
public class UserMapperTests {

    @Autowired
    private UserMapper1 userMapper;
    /**
     * 单元测试方法：可以独立运行，可以做单元测试
     * 1.必须被@Test注解修饰
     * 2.返回值必须为void
     * 3.方法的参数列表不指定任何类型
     * 4.方法的访问修饰符必须为public
     */
    @Test
    public void insert(){
        User user = new User();
        user.setUsername("tim01");
        user.setPassword("123");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }

    @Test
    public void getUserByName(){
        User user = userMapper.getUserByName("tim");
        System.out.println(user);

    }

    @Test
    public void UpdatePasswordByUid(){
        userMapper.UpdatePasswordByUid(8,"123456","admin",new Date());
    }

    @Test
    public void getUserByUid(){
        System.out.println(userMapper.getUserByUid(8));

    }

    @Test
    public void updateUserInfoByUid(){
        User user = new User();
        user.setUid(8);
        user.setPhone("17858123");
        user.setEmail("admin@123.com");
        user.setGender(1);
        user.setModifiedUser("系统管理员");
        user.setModifiedTime(new Date());
        Integer rows = userMapper.updateUserInfoByUid(user);
        System.out.println("rows=" + rows);
    }

    @Test
    public void updateAvatarByUid() {
        Integer uid = 8;
        String avatar = "/upload/avatar.png";
        String modifiedUser = "超级管理员";
        Date modifiedTime = new Date();
        Integer rows = userMapper.updateUserAvatarByUid(uid,avatar, modifiedUser, modifiedTime);
        System.out.println("rows=" + rows);
    }


}
