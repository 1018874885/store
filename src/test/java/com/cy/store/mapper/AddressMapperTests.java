package com.cy.store.mapper;

import com.cy.store.entity.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class AddressMapperTests {

    @Autowired
    private AddressMapper addressMapper;


    @Test
    public void insert() {
        Address address = new Address();
        address.setUid(8);
        address.setName("admin");
        address.setPhone("17858802974");
        address.setAddress("雁塔区小寨赛格");
        Integer rows = addressMapper.insert(address);
        System.out.println("rows=" + rows);
    }

    @Test
    public void getCountByUid(){
        Integer result = addressMapper.getCountByUid(8);
        System.out.println(result);
    }

    @Test
    public void getAddressByUid(){
        List<Address> addresses = addressMapper.getAddressByUid(11);
        for (Address address : addresses) {
            System.out.println(address);
        }
    }

    @Test
    public void getAddressByAid(){
        Address address = addressMapper.getAddressByAid(11);
        System.out.println(address);
    }

    @Test
    public void updateNonDefaultByUid(){
        addressMapper.updateNonDefaultByUid(11);
    }
    @Test
    public void updateDefaultByAid(){
        addressMapper.updateDefaultByAid(11,"admin",new Date());
    }

    @Test
    public void deleteByAid() {
        Integer aid = 4;
        Integer rows = addressMapper.deleteByAid(aid);
        System.out.println("rows=" + rows);
    }

    @Test
    public void findLastModified() {
        Integer uid = 11;
        Address result = addressMapper.findLastModified(uid);
        System.out.println(result);
    }
}
