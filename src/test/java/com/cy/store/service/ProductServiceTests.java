package com.cy.store.service;

import com.cy.store.entity.Product;
import com.cy.store.service.ex.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProductServiceTests {
    @Autowired
    private IProductService productService;

    @Test
    public void findHotList(){
        List<Product> list = productService.findHotList();
        for (Product product : list) {
            System.out.println(product);
        }
    }

    @Test
    public void findById() {
        try {
            Integer id = 10000017;
            Product result = productService.findById(id);
            System.out.println(result);
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }
}
