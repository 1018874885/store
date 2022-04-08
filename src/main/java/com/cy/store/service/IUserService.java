package com.cy.store.service;

import com.cy.store.entity.User;

/** 用户模块业务层接口 */
public interface IUserService {
    /**
     * 用户注册方法
     * @param user 用户的数据对象
     */
    void registry(User user);

    /**
     * 用户登录方法
     * @param username 用户名
     * @param password 密码
     * @return 用户对象，因为登录成功后需要获取id进行后续操作，同时还需要用户名，头像等信息展示在页面中，因此返回user对象
     * 状态管理：可以将数据保存在cookie或session中，可以避免重复度很高的数据多次频繁操作数据进行获取（用户名，id存session中，头像存cookie中）
     */
    User login(String username, String password);

    /**
     * 用户修改密码方法
     * @param uid
     * @param username
     * @param oldPassword
     * @param newPassword
     */
    void updatePassword(Integer uid,String username,String oldPassword,String newPassword);

    /**
     * 根据uid获取用户 是从数据库中获取到完整的user对象
     * @param uid
     * @return user对象
     */
    User getUserByUid(Integer uid);

    /**
     * 更新用户的数据信息  是从前端获取到不完整的user对象 （并非包含了所有属性的）
     * @param uid  由controller中的session传递到service层，然后传到dao层封装
     * @param username  由controller中的session传递到service层，然后传到dao层封装
     * @param user //由于此页面前端只需要传递email,phone，gender  只包含了这些信息的user对象
     */
    void updateUserInfo(Integer uid,String username,User user);

    /**
     * 更新用户的头像 service层接口的参数根据mapper的参数决定，看需要传递哪些参数给mapper
     * @param uid
     * @param username
     * @param avatar
     */
    void updateUserAvatar(Integer uid,String username,String avatar);
}
