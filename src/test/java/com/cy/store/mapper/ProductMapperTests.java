package com.cy.store.mapper;

import com.cy.store.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProductMapperTests {
    @Autowired
    private ProductMapper productMapper;

    @Test
    public void findHotList(){
        List<Product> list = productMapper.findHotList();
        for (Product product : list) {
            System.out.println(product);
        }
    }

    @Test
    public void findById() {
        Integer id = 10000017;
        Product result = productMapper.findById(id);
        System.out.println(result);
    }
}
