package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper1;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

/** 用户模块业务层的实现类 */
@Service   //@Service注解 将当前类的对象交给spring管理，自动创建对象以及对象的维护
public class UserServiceImpl implements IUserService {

    /** 业务层需要调用UserMapper对象来实现业务 */
    @Autowired
    private UserMapper1 userMapper;

    /** 用户注册的业务层实现 */
    @Override
    public void registry(User user) {
        //通过User获取username
        String username = user.getUsername();
        //调用getUserByName()判断用户名是否已经被注册
        User result = userMapper.getUserByName(username);
        //判断结果集是否为Null
        if(result != null){
            //抛出异常
            throw new UsernameDuplicatedException("用户名被占用");
        }

        //密码加密处理 MD5算法实现
        //串（盐值） + password + 串（盐值）  ----整体交给md5加密，连续加密三次   盐值：随机的字符串
        String oldPassword = user.getPassword();
        //获取盐值 随机生成一个字符串
        String salt = UUID.randomUUID().toString().toUpperCase(); //全大写的盐值
        //将密码和盐值作为一个整体进行加密处理
        String md5Password = getMD5Password(oldPassword, salt);
        //将加密后的密码重新补全设置到的user对象中
        user.setPassword(md5Password);
        //将盐值存入user对象中
        user.setSalt(salt);


        //补全数据 is_delete设置为0
        user.setIsDelete(0);
        //补全数据 4个日志字段信息
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);

        //执行注册业务功能的实现 rows==1时 插入成功 否则抛出异常
        Integer rows = userMapper.insert(user);
        if(rows != 1){
            throw new InsertException("在用户注册过程中产生了未知的异常");
        }
    }

    /** 用户登录的业务层实现 */
    @Override
    public User login(String username, String password) {

        // 根据用户名称查询用户数据是否存在，如果不存在，则抛出异常
        User result = userMapper.getUserByName(username);
        if(result == null){
            throw new UserNotFoundException("用户数据不存在");
        }

        // 检测用户密码是否匹配
        //1. 先获取到数据库中的加密之后的密码
        String oldPassword = result.getPassword();
        //2. 和用户传递过来的密码进行比较
        //  2.1 先获取用户的盐值
        String salt = result.getSalt();
        //  2.2 将用户的密码按照相同的MD5算法的规则进行加密
        String newMD5Password = getMD5Password(password, salt);
        // 3. 将密码进行比较
        if(!newMD5Password.equals(oldPassword)){
            throw new PasswordNotMatchException("用户密码错误");
        }

        //判断is_delete字段的值是否为1 表示该用户已被删除
        if(result.getIsDelete() == 1){
            throw new UserNotFoundException("用户数据不存在");
        }

        // 用户数据正常，返回用户对象
        // 重新封装user 减少冗余无关字段  提升性能
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());

        return user;
    }

    /**
     * 定义一个MD5加密算法实现
     * @param password 密码
     * @param salt 盐值
     * @return 加密后的密文
     */
    private String getMD5Password(String password,String salt){
        for(int i=0;i<3;i++){
            //MD5加密算法的调用
            password = DigestUtils.md5DigestAsHex((salt+password+salt).getBytes()).toUpperCase();
        }
        //返回加密后的密码
        return password;
    }

    /** 用户修改密码的业务层实现 */
    @Override
    public void updatePassword(Integer uid, String username, String oldPassword, String newPassword) {
        //根据uid获取用户对象
        User result = userMapper.getUserByUid(uid);
        //判断用户是否存在
        if(result == null || result.getIsDelete() == 1){
            throw new UserNotFoundException("用户信息不存在");
        }

        //将用户输入的原密码使用MD5加密后与从数据库中获取的密码进行比较，若不一致则原密码错误
        String salt = result.getSalt();
        String oldMD5Password = getMD5Password(oldPassword,salt);
        if(!oldMD5Password.equals(result.getPassword())){
            throw new PasswordNotMatchException("原密码错误");
        }

        //将用户输入的新密码使用MD5加密，调用Mapper中的更新密码sql修改数据库
        String newMD5Password = getMD5Password(newPassword,salt);
        Integer rows = userMapper.UpdatePasswordByUid(result.getUid(),newMD5Password,result.getUsername(),new Date());
        //根据修改数据库时受影响的行数判断是否产生未知错误
        if(rows != 1){
            throw new UpdateException("密码更新时产生未知异常");
        }
    }

    /** 根据uid获取用户数据 */
    @Override
    public User getUserByUid(Integer uid) {
        //根据uid获取用户对象
        User result = userMapper.getUserByUid(uid);
        //判断用户是否存在
        if(result == null || result.getIsDelete() == 1){
            throw new UserNotFoundException("用户信息不存在");
        }
        //由于前端此页面只需要展示用户名，email，phone与gender 所以重新封装以减少冗余
        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());

        return user;
    }

    /** 更新用户数据的业务层实现 */
    @Override
    public void updateUserInfo(Integer uid, String username, User user) {
        //这部分是查询数据库，来看此用户是否存在与系统中
        //根据uid获取用户对象
        User result = userMapper.getUserByUid(uid);
        //判断用户是否存在
        if(result == null || result.getIsDelete() == 1){
            throw new UserNotFoundException("用户信息不存在");
        }

        //此user对象是从controller中传递过来的user对象，只包含了3个属性 phone email gender 要给此对象加入uid和其他属性之后才能调用mapper中的方法
        user.setUid(uid);
        // 向参数user中补全数据：modifiedUser(username)
        user.setModifiedUser(username);
        // 向参数user中补全数据：modifiedTime(new Date())
        user.setModifiedTime(new Date());
        // 调用userMapper的updateInfoByUid(User user)方法执行修改，并获取返回值
        Integer rows = userMapper.updateUserInfoByUid(user);
        // 判断以上返回的受影响行数是否不为1
        if (rows != 1) {
            // 是：抛出UpdateException异常
            throw new UpdateException("更新用户数据时出现未知错误，请联系系统管理员");
        }
    }

    /** 更新用户头像的业务层实现 */
    @Override
    public void updateUserAvatar(Integer uid, String username, String avatar) {
        //这部分是查询数据库，来看此用户是否存在与系统中
        //根据uid获取用户对象
        User result = userMapper.getUserByUid(uid);
        //判断用户是否存在
        if(result == null || result.getIsDelete() == 1){
            throw new UserNotFoundException("用户信息不存在");
        }
        Integer rows = userMapper.updateUserAvatarByUid(uid,avatar,username,new Date());
        // 判断以上返回的受影响行数是否不为1
        if (rows != 1) {
            // 是：抛出UpdateException异常
            throw new UpdateException("更新用户数据时出现未知错误，请联系系统管理员");
        }
    }
}
