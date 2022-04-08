package com.cy.store.service;

import com.cy.store.entity.District;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DistrictServiceTests {

    @Autowired
    private IDistrictService districtService;

    @Test
    public void findByParent(){
        List<District> list = districtService.findByParent("630000");
        for (District district : list) {
            System.out.println(district);
        }
    }

    @Test
    public void getNameByCode(){
        String name = districtService.getNameByCode("630000");
        System.out.println(name);
    }


}
