package com.cy.store.mybatisservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServiceTest {
    @Autowired
    private MybatisUserService mybatisUserService;

    @Test
    public void test01(){
        mybatisUserService.countByExample();
    }

    @Test
    public void test02(){
        mybatisUserService.selectByExample();
    }

    @Test
    public void test03(){
        mybatisUserService.insert();
    }

    @Test
    public void test04(){
        mybatisUserService.deleteByExample(18);
    }

    @Test
    public void test05(){
        mybatisUserService.updateByExample(19);
    }
}
