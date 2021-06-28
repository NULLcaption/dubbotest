package com.cxg.common.domin;

import lombok.Data;

import java.io.Serializable;

/**
 * 系统用户实体类
 */
@Data
public class SysUserDo implements Serializable {
    private int user_id;
    private  String username;
    private  int dept_id;
    private String email;

}
