package com.xm01.web.controller.system;

import com.xm01.common.annotation.Log;
import com.xm01.common.core.controller.BaseController;
import com.xm01.common.core.domain.AjaxResult;
import com.xm01.common.core.domain.model.LoginUser;
import com.xm01.common.enums.BusinessType;
import com.xm01.common.utils.SecurityUtils;
import com.xm01.common.utils.email.EmailServer;
import com.xm01.system.service.ISysUserService;
import com.xm01.web.controller.common.CommonController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 *通过邮箱找回密码
 * @author java
 * 2025.12.13
 */
public class SysRepasswordController extends BaseController
{
    @Autowired
    private ISysUserService userService;

    private static final Logger log = LoggerFactory.getLogger(SysRepasswordController.class);

}
