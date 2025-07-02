package com.xm01.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xm01.common.annotation.Excel;
import com.xm01.common.annotation.Excel.ColumnType;
import com.xm01.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 系统访问记录表 sys_logininfor
 * 
 * @author ruoyi
 */
public class SysLoginincount extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /** 用户账号 */
    @Excel(name = "用户账号")
    private String userName;

    /** 登录状态 0成功 1失败 */
    @Excel(name = "登录次数")
    private long count;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }


    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }


}
