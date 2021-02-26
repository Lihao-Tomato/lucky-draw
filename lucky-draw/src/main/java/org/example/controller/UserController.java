package org.example.controller;

import org.example.exception.AppException;
import org.example.model.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: LiHao
 * Date: 2021-02-02
 * Time: 12:07
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Object register(User user, MultipartFile headFile) {
        //校验请求数据，后端必须要有

        //保存上传的用户头像到服务器本地
        if(headFile != null) {
            //上传的路径映射为http服务路径
            //用户头像的http路径设置到user.head，把user插入数据库
            String head = userService.saveHead(headFile);
            user.setHead(head);
        }
        userService.register(user);

        return null;
    }

    @PostMapping("/login")
    public Object login(@RequestBody User user, HttpServletRequest req){//username, password
        //数根据账号从据库查用户
        User exist = userService.queryByUsername(user.getUsername());
        //用户不存在
        if(exist == null){
            throw new AppException("LOG001", "用户不存在");
        }
        //用户存在，校验密码
        if (!user.getPassword().equals(exist.getPassword())){
            throw new AppException("LOG002", "账号或密码错误");
        }
        //校验通过，保存数据库的用户（数据库用户信息更加完整）到session中
        HttpSession session = req.getSession();//县创建一个session
        session.setAttribute("user",exist);
        return null;//登录成功
    }

    @GetMapping("/logout")
    public Object logout(HttpSession session){
        session.removeAttribute("user");
        return null;
    }
}
