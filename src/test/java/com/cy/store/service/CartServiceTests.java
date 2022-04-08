package com.cy.store.service;

import com.cy.store.service.ex.ServiceException;
import com.cy.store.vo.CartVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CartServiceTests {
    @Autowired
    private ICartService cartService;

    @Test
    public void addToCart() {
        try {
            Integer uid = 11;
            Integer pid = 10000007;
            Integer amount = 1;
            String username = "Tom";
            cartService.addToCart(uid, pid, amount, username);
            System.out.println("OK.");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void getVOByUid() {
        List<CartVo> list = cartService.getVOByUid(11);
        System.out.println("count=" + list.size());
        for (CartVo item : list) {
            System.out.println(item);
        }
    }

    @Test
    public void addNum() {
        try {
            Integer cid = 6;
            Integer uid = 10;
            String username = "管理员";
            Integer num = cartService.addNum(cid, uid, username);
            System.out.println("OK. New num=" + num);
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void getVOByCids() {
        Integer[] cids = {1, 2, 6, 7, 8, 9, 10};
        Integer uid = 11;
        List<CartVo> list = cartService.getVOByCids(uid, cids);
        System.out.println("count=" + list.size());
        for (CartVo item : list) {
            System.out.println(item);
        }
    }
}
