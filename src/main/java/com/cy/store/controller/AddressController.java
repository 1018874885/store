package com.cy.store.controller;

import com.cy.store.controller.BaseController;
import com.cy.store.entity.Address;
import com.cy.store.service.IAddressService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("addresses")
public class AddressController extends BaseController {
    @Autowired
    private IAddressService addressService;

    @RequestMapping("add_new_address")
    public JsonResult<Void> addNewAddress(Address address, HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.addNewAddress(uid,username,address);
        return new JsonResult<Void>(OK);
    }

    @GetMapping({"","/"})
    public JsonResult<List<Address>> getAddressByUid(HttpSession session){
        Integer uid = getUidFromSession(session);
        List<Address> data = addressService.getAddressByUid(uid);
        return new JsonResult<List<Address>>(OK,data);
    }

    // Restful风格传递参数 {aid}/url
    // 因为这是需要前端点击某个按钮来传递这个特定的参数aid去找到某条特定的数据然后进行操作，并且数据没有保存在session中 因此要使用url传参？
    @RequestMapping("{aid}/set_default")
    public JsonResult<Void> setDefault(@PathVariable("aid") Integer aid, HttpSession session) {
        System.out.println("进入了set");
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.setDefault(aid, uid, username);
        return new JsonResult<Void>(OK);
    }

    @RequestMapping("{aid}/delete")
    public JsonResult<Void> delete(@PathVariable("aid") Integer aid, HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.deleteAddressByAid(aid,uid,username);
        return new JsonResult<Void>(OK);
    }


}
