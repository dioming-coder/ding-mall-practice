package com.dingyaoming.dingseckill.controller;

import com.dingyaoming.dingseckill.entity.User;
import com.dingyaoming.dingseckill.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/add")
    public String addUser() {
        User user = new User();

        user.setUsername("dioming");
        user.setPassword("123456");
        user.setPhone("15736923791");
        user.setRole("ADMIN");

        int result = userMapper.insert(user);

        if (result > 0){
            return "添加成功!用户ID" + user.getId();
        }else {
            return "添加失败";
        }

    }
    @GetMapping("/get/{id}")
    public User getUSer(@PathVariable Long id){
        return userMapper.selectById(id);
    }
}
