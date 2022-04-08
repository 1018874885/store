package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.IDistrictService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/** 一般来说 insert操作是从controller->service->dao   get操作是从dao->service-controller的 */

@Service
public class AddressServiceImpl implements IAddressService {
    @Autowired
    private AddressMapper addressMapper;

    /** 在AddressServiceImpl类中声明处理省/市/区数据的业务层对象。 因为需要它来获取省市区名称代码等来补全数据*/
    @Autowired
    private IDistrictService districtService;

    @Value("${user.address.max-count}")
    private Integer addressMaxCount;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        //获取当前用户的收货地址条数
        Integer count = addressMapper.getCountByUid(uid);
        //判断收货地址数是否超出限制
        if(count >= addressMaxCount){
            throw new AddressCountLimitException("收货地址超出限制");
        }

        System.out.println(address);
        //address对象补全uid
        address.setUid(uid);

        // 补全数据：根据以上统计的数量，得到正确的isDefault值(是否默认：0-不默认，1-默认)，并封装
        Integer isDefault = count == 0 ? 1 : 0;
        address.setIsDefault(isDefault);

        //补全address中日志信息
        address.setCreatedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedUser(username);
        address.setModifiedTime(new Date());

        //补全address中的省市区的名称数据
        //获取省市区的名称
        String provinceName = districtService.getNameByCode(address.getProvinceCode());
        String cityName = districtService.getNameByCode(address.getCityCode());
        String areaName = districtService.getNameByCode(address.getAreaCode());
        System.out.println(provinceName+cityName+areaName);
        //补全名称到address对象中
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);
        System.out.println(address);

        //执行mapper中的插入方法
        Integer rows = addressMapper.insert(address);
        if(rows != 1){
            throw new InsertException("插入时产生未知错误");
        }

    }

    @Override
    public List<Address> getAddressByUid(Integer uid) {
        List<Address> list = addressMapper.getAddressByUid(uid);

        //对前端页面不需要展示的数据可以设置为Null 提升传输效率
        for (Address address : list) {
            //address.setAid(null);//需要根据aid判断当前收货地址是否存在以及编辑删除设为默认等功能，所有不设置为null
            address.setUid(null);
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setCreatedUser(null);
            address.setCreatedTime(null);
            address.setModifiedUser(null);
            address.setModifiedTime(null);
        }
        return list;
    }

    //事务：基于Spring JDBC的事务（Transaction）处理，使用事务可以保证一系列的增删改操作，要么全部执行成功，要么全部执行失败。
    //@Transactional注解可以用来修饰类也可以用来修饰方法。如果添加在业务类之前，则该业务类中的方法均以事务的机制运行，但是一般并不推荐这样处理。
    //因为此方法中有两步更新操作 事务要求的是一组操作 因此需要加这个注解
    @Transactional
    @Override
    public void setDefault(Integer aid, Integer uid, String username) {

        // 1.先判断当前收货地址是否存在
        //根据aid获取address对象 如果为null 则当前收货地址不存在
        Address address = addressMapper.getAddressByAid(aid);
        if(address == null){
            throw new AddressNotFoundException("地址信息未找到");
        }

        if(uid != address.getUid()){
            throw new AccessDeniedException("地址信息访问异常");
        }

        // 2.将该用户所有收货地址设为非默认
        Integer rows = addressMapper.updateNonDefaultByUid(uid);
        if(rows < 1){
            throw new UpdateException("设置默认收货地址时出现未知错误[1]");
        }

        // 3.将选中的收货地址设置为默认
        rows = addressMapper.updateDefaultByAid(aid,username,new Date());
        if(rows != 1){
            throw new UpdateException("设置默认收货地址时出现未知错误[2]");
        }
    }

    @Override
    public void deleteAddressByAid(Integer aid, Integer uid, String username) {
        // 1.判断当前选中的地址信息是否存在
        Address address = addressMapper.getAddressByAid(aid);
        if(address == null){
            throw new AddressNotFoundException("地址信息没有找到");
        }

        // 2.判断用户对当前地址信息是否具有权限
        if(!address.getUid().equals(uid)){
            throw new AccessDeniedException("非法访问");
        }

        // 3.删除当前选中的地址信息
        Integer rows = addressMapper.deleteByAid(aid);
        if(rows != 1){
            throw new DeleteException("删除产生异常");
        }

        // 4.判断删除的这条记录是否为默认地址  如果不是的话，就OK了 否则还需要进一步处理
        if(address.getIsDefault()==0){
            return;
        }
        // 5.查询删除该条默认地址信息后，用户是否还有其他地址信息  如果不存在，就OK了 否则还需要进一步处理
        Integer count = addressMapper.getCountByUid(uid);
        if(count == 0){
            return;
        }

        // 6.获取用户最后修改的地址信息
        Address lastAddress = addressMapper.findLastModified(uid);

        // 7.将该用户最后修改的地址信息设置为默认
        rows = addressMapper.updateDefaultByAid(lastAddress.getAid(), username, new Date());
        if(rows != 1){
            throw new UpdateException("更新收货地址数据时出现未知错误，请联系系统管理员");
        }

    }
}
