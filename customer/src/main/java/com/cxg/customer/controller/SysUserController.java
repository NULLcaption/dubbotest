package com.cxg.customer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cxg.common.domin.SysUserDo;
import com.cxg.common.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SysUserController {

    @Resource
    @Reference(version = "1.0.0")
    private SysUserService sysUserService;

    @GetMapping("/getUser")
    public SysUserDo user(){
        System.out.println("进来了！！！！！！！！！！！！！！！！");
        System.out.println(sysUserService.hashCode()+"!!!!!!!!!!!!!!!!");
        return sysUserService.findSysUser();
    }
}
