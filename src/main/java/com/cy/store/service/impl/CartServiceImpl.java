package com.cy.store.service.impl;

import com.cy.store.entity.Cart;
import com.cy.store.mapper.CartMapper;
import com.cy.store.service.ICartService;
import com.cy.store.service.IProductService;
import com.cy.store.service.ex.AccessDeniedException;
import com.cy.store.service.ex.CartNotFoundException;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UpdateException;
import com.cy.store.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private IProductService productService;   //需要通过pid查询商品价格

    @Override
    public void addToCart(Integer uid, Integer pid, Integer amount, String username) {
        Cart result = cartMapper.findByUidAndPid(uid, pid);  //根据pid uid查询购物车中的数据
        //判断查询结果是否为Null
        if (result == null) {  //该用户未将该商品添加到购物车中 需要创建新的购物车对象加入数据库
            Cart cart = new Cart();   //创建新的购物车对象并封装数据
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);
            Long price = productService.findById(pid).getPrice();  //根据商品id获取价格并封装
            cart.setPrice(price);
            //封装日志数据
            cart.setModifiedUser(username);
            cart.setModifiedTime(new Date());
            cart.setCreatedUser(username);
            cart.setCreatedTime(new Date());
            //数据库插入
            Integer rows = cartMapper.insert(cart);
            if (rows != 1) {
                throw new InsertException("插入商品数据时出现未知错误，请联系系统管理员");
            }
        } else {                                 //用户购物车中已经有该数据 则更新数量即可
            Integer cid = result.getCid();  //获取购物车的cid
            Integer num = result.getNum();  //获取购物车中商品数量
            num = num + amount;   //原始数量+新增数量=购物车中商品数量
            Integer rows = cartMapper.updateNumByCid(cid, num, username, new Date());
            if (rows != 1) {
                throw new UpdateException("修改商品数量时出现未知错误，请联系系统管理员");
            }
        }
    }

    @Override
    public List<CartVo> getVOByUid(Integer uid) {
        return cartMapper.findVOByUid(uid);
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        // 调用findByCid(cid)根据参数cid查询购物车数据
        Cart result = cartMapper.findByCid(cid);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：抛出CartNotFoundException
            throw new CartNotFoundException("尝试访问的购物车数据不存在");
        }

        // 判断查询结果中的uid与参数uid是否不一致
        if (!result.getUid().equals(uid)) {
            // 是：抛出AccessDeniedException
            throw new AccessDeniedException("非法访问");
        }

        // 可选：检查商品的数量是否大于多少(适用于增加数量)或小于多少(适用于减少数量)
        // 根据查询结果中的原数量增加1得到新的数量num
        Integer num = result.getNum() + 1;

        // 创建当前时间对象，作为modifiedTime
        Date now = new Date();
        // 调用updateNumByCid(cid, num, modifiedUser, modifiedTime)执行修改数量
        Integer rows = cartMapper.updateNumByCid(cid, num, username, now);
        if (rows != 1) {
            throw new InsertException("修改商品数量时出现未知错误，请联系系统管理员");
        }

        // 返回新的数量
        return num;
    }
    @Override
    public List<CartVo> getVOByCids(Integer uid, Integer[] cids) {
        List<CartVo> list = cartMapper.findVOByCids(cids);
        System.out.println("1   "+list);
        System.out.println(list.size());
        Iterator<CartVo> it = list.iterator();
        while (it.hasNext()) {
            CartVo cart = it.next();
            if (!cart.getUid().equals(uid)) {
                it.remove();
            }
        }
        System.out.println("2   "+list);
        System.out.println(list.size());
        return list;
    }
}
