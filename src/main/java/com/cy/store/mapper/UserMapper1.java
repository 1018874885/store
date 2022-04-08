package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/** 用户模块的持久层接口 */
@Repository
public interface UserMapper1 {
    /**
     * 插入用户的数据
     * @param user 用户的数据
     * @return 受影响的行数（在增删改都有受影响的行数作为返回值，可以根据返回值判断是否执行成功）
     */
    Integer insert(User user);

    /**
     * 根据用户名查询用户的数据
     * @param username 用户名
     * @return  查询成功返回User  否则返回Null
     */
     User getUserByName(String username);

    /**
     * 根据用戶uid來修改用戶密碼
     * @param uid
     * @param password
     * @param modifiedUser
     * @param modifiedTime
     * @return 受影响的行数
     */
     Integer UpdatePasswordByUid(Integer uid, String password, String modifiedUser, Date modifiedTime);

    /**
     * 根据用户uid查询用户数据
     * @param uid
     * @return 用户对象
     */
     User getUserByUid(Integer uid);

    /**
     * 根据用户uid修改用户的个人信息
     * @param user
     * @return 受影响的行数
     */
     Integer updateUserInfoByUid(User user);

    /**
     * 根据uid修改用戶头像
     * @param uid
     * @param avatar
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
    //@Param("uid") Integer uid : Integer类型的uid会注入到sql语句中的uid
    //@Param("SQL映射文件中#{}占位符的变量名)：解决的问题，当SQL语句中的占位符和映射的接口方法参数名不一致时，将参数强行注入到占位符变量上
     Integer updateUserAvatarByUid(@Param("uid") Integer uid,
                                   @Param("avatar")String avatar,
                                   @Param("modifiedUser")String modifiedUser,
                                   @Param("modifiedTime")Date modifiedTime);
}
