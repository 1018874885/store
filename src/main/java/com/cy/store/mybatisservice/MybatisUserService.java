package com.cy.store.mybatisservice;

import com.cy.store.mybatis.dao.auto.UserMapper;
import com.cy.store.mybatis.model.auto.User;
import com.cy.store.mybatis.model.auto.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MybatisUserService {
    @Autowired
    private UserMapper userMapper;

    public Long countByExample(){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andGenderEqualTo(0)
                .andIsDeleteEqualTo(0);
        long result = userMapper.countByExample(userExample);
        System.out.println(result);
        return result;
    }

    public List<User> selectByExample(){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andGenderEqualTo(0).andIsDeleteEqualTo(0);
        List<User> result = userMapper.selectByExample(userExample);
        for (User user : result) {
            System.out.println(user.getUsername());
        }
        return result;
    }

    public int insert(){
        User user = new User();
        user.setUsername("jacklove");
        user.setPassword("123456");
        user.setCreatedTime(new Date());
        int result = userMapper.insert(user);
        return result;
    }

    public int deleteByExample(Integer uid){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUidEqualTo(uid).andIsDeleteEqualTo(0);
        int result = userMapper.deleteByExample(userExample);
        System.out.println(result);
        if(result!=1){
            System.out.println("未找到数据，删除失败");
        }
        System.out.println("删除成功");
        return result;
    }

    public int updateByExample(Integer uid){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUidEqualTo(uid).andUsernameEqualTo("jacklove");
        User user = new User();
        user.setUid(uid);
        user.setUsername("jacklove321");
        user.setPassword("7654321");
        user.setCreatedUser("admin");
        user.setCreatedTime(new Date());
        user.setGender(0);
        user.setPhone("17709710903");

        int result = userMapper.updateByExampleSelective(user, userExample);
        if(result!=1){
            System.out.println("更新失败");
            return 0;
        }else{
            System.out.println("更新成功");
            return 1;
        }
    }
}