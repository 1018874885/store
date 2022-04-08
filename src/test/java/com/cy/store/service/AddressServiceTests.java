package com.cy.store.service;

import com.cy.store.entity.Address;
import com.cy.store.service.ex.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AddressServiceTests {
    @Autowired
    private IAddressService addressService;

    @Test
    public void addNewAddress(){
        try {
            Integer uid = 8;
            String username = "管理员";
            Address address = new Address();
            address.setName("张三");
            address.setPhone("17858805555");
            address.setAddress("雁塔区小寨华旗");
            addressService.addNewAddress(uid, username, address);
            System.out.println("OK.");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void getAddressByUid(){
        List<Address> list = addressService.getAddressByUid(11);
        for (Address address : list) {
            System.out.println(address);
        }
    }

    @Test
    public void setDefault(){
        addressService.setDefault(10,11,"admin");
    }

    @Test
    public void delete() {
        try {
            Integer aid = 8;
            Integer uid = 11;
            String username = "明明";
            addressService.deleteAddressByAid(aid, uid, username);
            System.out.println("OK.");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }
}
