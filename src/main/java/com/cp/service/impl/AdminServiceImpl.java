package com.cp.service.impl;

import com.cp.entity.Admin;
import com.cp.mapper.AdminMapper;
import com.cp.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Override
    public Admin login(Admin admin) {
        return adminMapper.login(admin);
    }
}
