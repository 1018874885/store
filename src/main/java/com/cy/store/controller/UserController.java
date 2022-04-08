package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UsernameDuplicatedException;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController  //相当于 @Controller + @ResponseBody
@RequestMapping("users")  //拦截users路径下的请求
public class UserController extends BaseController{

    @Autowired
    private IUserService userService;

    //用户注册
    /**
     * 1.接收数据方式：请求处理方法的参数列表设置为pojo类型，来接收前端的数据
     * Springboot会将前端的url地址中的参数名和pojo类的属性名进行比较，如果两个名称相同，则将值注入到pojo类中对应的属性上
     */
    @RequestMapping("reg")
    public JsonResult<Void> registry(User user) {
        userService.registry(user);
        return new JsonResult<>(OK);
    }
    /*
    @RequestMapping("reg") //拦截user/reg下的请求
    //@ResponseBody  //此方法的响应结果以json的格式进行数据响应给到前端
    public JsonResult<Void> registry(User user) {  //JsonResult<Void> 在注册时，后端无需给前端传递数据，只需要状态码和描述信息即可
        //System.out.println("进入了reg");

        //创建响应结果对象
        JsonResult<Void> result = new JsonResult<>();
        try {
            userService.registry(user);
            result.setState(200);
            result.setMessage("注册成功");
        } catch (UsernameDuplicatedException e) {
            result.setState(4000);
            result.setMessage("用户名被占用");
        } catch (InsertException e) {
            result.setState(5000);
            result.setMessage("注册时产生未知的异常");
        }
        //System.out.println(result.getState());
        //System.out.println(result.getMessage());

        return result;
    }
     */

    //用户登录

    /**
     * 2.接收数据方式：请求处理方法等等参数列表设置为非pojo类型
     * Springboot会直接将请求的参数名和方法中的参数名直接进行比较，如果名称相同则完成值的注入
     */
    /** 在登录的方法中将数据封装在session对象中
     *  服务本身自动创建有session对象，已经是一个全局的session对象，SpringBoot可以直接使用session对象，直接将HttpSession的对象
     *  作为请求处理方法的参数，会自动将全局的session对象注入到请求处理方法的形参上*/

     @RequestMapping("login")
    public JsonResult<User> login(String username, String password, HttpSession session){
        User data = userService.login(username, password);
        //封装设置session属性值（session是全局的）
        session.setAttribute("uid",data.getUid());
        session.setAttribute("username",data.getUsername());
         System.out.println(getUidFromSession(session));
         System.out.println(getUsernameFromSession(session));
        return new JsonResult<User>(OK,data);
    }

    @RequestMapping("update_password")
    public JsonResult<Void> updatePassword(String oldPassword,String newPassword,HttpSession session){
         Integer uid = getUidFromSession(session);
         String username = getUsernameFromSession(session);
         userService.updatePassword(uid,username,oldPassword,newPassword);
         return new JsonResult<Void>(OK);
    }

    @GetMapping("get_user_by_uid")
    public JsonResult<User> getUserByuid(HttpSession session){
         User user = userService.getUserByUid(getUidFromSession(session));
         return new JsonResult<User>(OK,user);
    }

    @RequestMapping("update_info")
    public JsonResult<Void> updateUserInfo(User user,HttpSession session){
         //此user对象是前端页面提交的 只包含4个属性，username phone email gender
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.updateUserInfo(uid,username,user);
        return new JsonResult<Void>(OK);


    }

    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;  //上传文件的最大值 10MB
    public static final List<String> AVATAR_TYPE = new ArrayList<>();  //限制上传文件的类型
    static{
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }

    /** MultipartFile是spring mvc提供的用于文件上传的api 返回值为String类型是因为需要记录头像路径，以便再次进入此页面时能够显示用户头像 */
    /** 该接口可以获取任何类型的文件，Springboot整合了spring mvc 只需要在处理请求的方法参数列表上声明一个参数类型为MultipartFile的参数，
     *  Springboot会自动将传递给服务的文件数据赋值给这个参数，因此这个参数的形参名需要与前端页面中的name对应
     * @RequestParam() 表示请求中的参数，将请求中的参数注入请求处理方法的某个参数上，解决名称不一致的问题
     */
    @RequestMapping("update_avatar")
    public JsonResult<String> updateUserAvatar(HttpSession session, MultipartFile file) {
        //判断文件是否为空
        if (file.isEmpty()) {
            System.out.println("文件为空");
            throw new FileEmptyException("文件为空");
        }
        //判断文件大小
        if (file.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("文件超出限制");
        }
        //判断文件类型
        String contentType = file.getContentType(); //获取到的形式是text/html这样的
        System.out.println(contentType);
        if (!AVATAR_TYPE.contains(contentType)) { //如果数组包含此类型则返回true
            throw new FileTypeException("文件类型不支持");
        }

        //上传的文件 要保存在项目的 .../upload/文件.jpeg 路径下
        // 获取项目的绝对路径
        String parent = session.getServletContext().getRealPath("upload");
        // File对象指向此路径 判断此目录文件是否存在
        File dir = new File(parent);
        if (!dir.exists()) { //目录不存在
            dir.mkdirs();//则创建目录
        }
        //获取文件名称，利用UUID生成一个新的文件名 以防止服务器中多个重名文件
        String originalFilename = file.getOriginalFilename();  //获取用户上传的文件名称 avatar01.jpeg
        //记录文件的后缀
        int index = originalFilename.lastIndexOf("."); //记录最后一个点的索引位置
        String suffix = originalFilename.substring(index); //记录文件后缀名
        String filename = UUID.randomUUID().toString() + suffix;//生成新的文件名称 XXXXXX-XXXXXX-XXXXXX-XXXXXX-XXXXXX-XXXXXX.jpeg

        //先在项目upload路径下创建一个与用户文件同名的空文件，然后直接传输过去
        File dest = new File(dir, filename);//在dir目录下创建了一个filename的空文件
        try {
            file.transferTo(dest);  //将file文件（用户文件）直接写入到dest文件中，前提是后缀必须相同
        } catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        } catch (FileStateException e) {
            throw new FileStateException("文件状态异常，文件可能被移动或删除");
        }

        //头像文件已经成功保存的服务器/upload/filename.jpeg，后续需要写入数据库
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        String avatar = "/upload/" + filename; //头像路径
        userService.updateUserAvatar(uid,username,avatar);
        return new JsonResult<String>(OK,avatar);  //返回用户头像的路径返回给前端页面，将来用于展示
    }


}
