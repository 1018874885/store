package com.cy.store.service;

import com.cy.store.entity.Address;

import java.util.List;

/** 地址模块业务层接口 */
public interface IAddressService {
    /**
     * 插入收货地址数据
     * @param uid
     * @param username
     * @param address
     */
    void addNewAddress(Integer uid, String username,Address address);

    /**
     * 查询某用户的收货地址列表数据
     * @param uid 收货地址归属的用户id
     * @return 该用户的收货地址列表数据
     */
    List<Address> getAddressByUid(Integer uid);

    /**
     * 设置默认收货地址
     * @param aid 收货地址id
     * @param uid 归属的用户id
     * @param username 当前登录的用户名  通过session传递
     */
    void setDefault(Integer aid,Integer uid,String username);

    /**
     * 删除收货地址
     * @param aid 收货地址aid
     * @param uid 归属的用户uid
     * @param username 当前登录的用户名  因为如果删除了默认地址 需要重新设置默认地址，此时setDefault()方法中需要username
     */
    void deleteAddressByAid(Integer aid,Integer uid,String username);
}
