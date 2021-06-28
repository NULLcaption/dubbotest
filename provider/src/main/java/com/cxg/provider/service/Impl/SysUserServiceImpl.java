package com.cxg.provider.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cxg.common.domin.SysUserDo;
import com.cxg.common.service.SysUserService;
import com.cxg.provider.mapper.SysUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * DUBBO暴露接口
 */
@Service(version = "1.0.0")
public class SysUserServiceImpl implements SysUserService {

    private static Logger logger = LoggerFactory.getLogger(SysUserMapper.class);

    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public SysUserDo findSysUser() {
        try {
            return sysUserMapper.getOne(124L);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }
}
