package com.cy.store.service;

import com.cy.store.entity.User;
import com.cy.store.service.ex.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest: 标注当前的类是一个测试类，不会随同项目一起打包
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private IUserService userService;
    /**
     * 单元测试方法：可以独立运行，可以做单元测试
     * 1.必须被@Test注解修饰
     * 2.返回值必须为void
     * 3.方法的参数列表不指定任何类型
     * 4.方法的访问修饰符必须为public
     */

    @Test
    public void registry(){
        try {
            User user = new User();
            user.setUsername("tim004");
            user.setPassword("12345");
            userService.registry(user);
            System.out.println("ok");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login(){
        User user = userService.login("Tim001", "123456");
        System.out.println(user);
    }

    @Test
    public void updatePassword(){
        try {
            userService.updatePassword(11,"tim004","1234567","12345");
            System.out.println("密码修改成功");
        }catch (ServiceException e) {
            System.out.println("密码修改失败！" + e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void getByUid() {
        try {
            Integer uid = 10;
            User user = userService.getUserByUid(uid);
            System.out.println(user);
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void changeInfo() {
        try {
            Integer uid = 8;
            String username = "数据管理员";
            User user = new User();
            user.setPhone("17709710903");
            user.setEmail("admin07@cy.cn");
            user.setGender(0);
            userService.updateUserInfo(uid, username, user);
            System.out.println("OK.");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void updateUserAvatar() {
        try {
            Integer uid = 11;
            String username = "头像管理员";
            String avatar = "/upload/avatar.png";
            userService.updateUserAvatar(uid, username, avatar);
            System.out.println("OK.");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }




}
