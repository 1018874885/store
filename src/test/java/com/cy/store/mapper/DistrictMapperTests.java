package com.cy.store.mapper;

import com.cy.store.entity.District;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DistrictMapperTests {
    @Autowired
    private DistrictMapper districtMapper;

    @Test
    public void findByParent(){
        List<District> districts = districtMapper.findByParent("630000");
        for (District district : districts) {
            System.out.println(district.getName());
        }
    }

    @Test
    public void getNameByCode(){
        String name = districtMapper.getNameByCode("630000");
        System.out.println(name);
    }
}
