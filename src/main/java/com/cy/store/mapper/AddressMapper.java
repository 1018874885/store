package com.cy.store.mapper;

import com.cy.store.entity.Address;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AddressMapper {

    /**
     * 数据库中插入收货地址信息
     * @param address 收货地址数据
     * @return 受影响的行数
     */
    Integer insert(Address address);

    /**
     * 根据用户uid获取
     * @param uid 用户uid
     * @return 用户收货地址条数
     */
    Integer getCountByUid(Integer uid);

    /**
     * 根据用户uid获取其所有的地址列表
     * @param uid
     * @return
     */
    List<Address> getAddressByUid(Integer uid);

    /** 设置默认地址功能 先判断用户选中的地址记录是否存在，然后将所有地址设置为非默认，最后将当前选中的地址设置为默认
     *   否则的话需要先查询所有记录，然后将第一条设置为非默认，再将当前记录设置为默认  其实这样也行哈
     */

    /**
     * 根据地址aid查询地址记录
     * @param aid
     * @return
     */
    Address getAddressByAid(Integer aid);

    /**
     * 将某用户的所有收货地址设置为非默认地址
     * @param uid 收货地址归属的用户id
     * @return 受影响的行数
     */
    Integer updateNonDefaultByUid(Integer uid);

    /**
     * 将指定的收货地址设置为默认地址
     * @param aid 收货地址id
     * @param modifiedUser 修改执行人
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    Integer updateDefaultByAid(Integer aid, String modifiedUser, Date modifiedTime);


    /**
     * 根据收货地址aid删除数据
     * @param aid 收货地址id
     * @return 受影响的行数
     */
    Integer deleteByAid(Integer aid);

    /**
     * 查询某用户最后修改的收货地址  如果删除的地址为默认地址，则需要将他最后修改的地址设置为默认
     * @param uid 归属的用户id
     * @return 该用户最后修改的收货地址，如果该用户没有收货地址数据则返回null
     */
    Address findLastModified(Integer uid);

}
