package com.cp.controller;


import com.cp.constant.SystemConstant;
import com.cp.entity.Admin;
import com.cp.entity.R;
import com.cp.service.AdminService;
import com.cp.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/login")
    public R login(@RequestBody Admin admin)throws Exception {
        Admin u = adminService.login(admin);
        R r = new R();
        if(u==null){
            return R.error("用户名或者密码错误");
        }else{
            String token = JwtUtils.createJWT(String.valueOf(u.getId()),u.getUserName(),SystemConstant.JWT_TTL);
            r.put("token",token);
        }
        return r;
    }
}
