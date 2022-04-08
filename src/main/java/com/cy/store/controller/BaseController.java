package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.service.ex.*;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/** 控制层类的基类 可以进行异常的捕获处理 */
public class BaseController {
    /** 操作成功的状态码 */
    public static  final int OK = 200;

    // 当前项目中产生了异常，service抛出该异常，被统一拦截到此方法中，此方法为请求处理方法 这个方法的返回值就是需要传递给前端的数据
    // 自动将异常对象传递给此方法的参数列表上
    @ExceptionHandler({ServiceException.class, FileUploadException.class}) //用于统一处理抛出的异常
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if(e instanceof UsernameDuplicatedException){
            result.setState(4000);
            result.setMessage("用户名已被注册");
        }else if(e instanceof UserNotFoundException){
            result.setState(4001);
            result.setMessage("用户数据不存在");
        }else if(e instanceof PasswordNotMatchException){
            result.setState(4002);
            result.setMessage("用户密码错误");
        }else if (e instanceof AddressCountLimitException) {
            result.setState(4003);
            result.setMessage("收货地址超出上限");
        }else if (e instanceof AddressNotFoundException) {
            result.setState(4004);
            result.setMessage("地址信息没有找到");
        } else if (e instanceof AccessDeniedException) {
            result.setState(4005);
            result.setMessage("地址信息访问异常");
        }else if(e instanceof ProductNotFoundException) {
            result.setState(4006);
            result.setMessage("商品信息未找到");
        }else if (e instanceof CartNotFoundException) {
            result.setState(4007);
            result.setMessage("购物车信息未找到");
        }else if(e instanceof InsertException){
            result.setState(5000);
            result.setMessage("注册时产生未知的错误");
        }else if(e instanceof UpdateException) {
            result.setState(5001);
            result.setMessage("更新密码时产生未知错误");
        }else if (e instanceof DeleteException) {
            result.setState(5002);
            result.setMessage("删除时产生异常");
        }else if (e instanceof FileEmptyException) {
            result.setState(6000);
            result.setMessage("上传文件为空");
        } else if (e instanceof FileSizeException) {
            result.setState(6001);
            result.setMessage("上传文件超出大小");
        } else if (e instanceof FileTypeException) {
            result.setState(6002);
            result.setMessage("上传文件类型不匹配");
        } else if (e instanceof FileStateException) {
            result.setState(6003);
            result.setMessage("上传文件状态异常");
        } else if (e instanceof FileUploadIOException) {
            result.setState(6004);
            result.setMessage("上传文件IO异常");
        }
        return result;
    }

    /** 在父类中封装获取两个数据 uid与username 的方法 获取用户头像的方法之后封装在cookie中 */
    /**
     * 获取session对象中的uid
     * @param session session对象
     * @return 当前登录的用户uid
     */
    public final Integer getUidFromSession(HttpSession session){
        // session.getAttribute()方法返回的是个对象，而此方法返回值为Integer类型的uid,因此要将该对象先转为String,再转为Integer
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 获取session对象中的username
     * @param session session对象
     * @return 当前登录的用户username
     */
    public final String getUsernameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }


}
