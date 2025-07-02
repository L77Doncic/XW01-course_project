package com.xm01.web.controller.system;

import com.xm01.common.annotation.Anonymous;
import com.xm01.common.annotation.Log;
import com.xm01.common.constant.Constants;
import com.xm01.common.core.domain.entity.SysUser;
import com.xm01.common.core.domain.model.LoginBody;
import com.xm01.common.core.domain.model.LoginUser;
import com.xm01.common.enums.BusinessType;
import com.xm01.common.utils.SecurityUtils;
import com.xm01.common.utils.email.EmailServer;
import com.xm01.framework.web.service.SysLoginService;
import com.xm01.framework.web.service.TokenService;
import com.xm01.system.service.ISysUserService;
import com.xm01.web.controller.common.CommonController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.gson.GsonProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.xm01.common.core.controller.BaseController;
import com.xm01.common.core.domain.AjaxResult;
import com.xm01.common.core.domain.model.RegisterBody;
import com.xm01.common.utils.StringUtils;
import com.xm01.framework.web.service.SysRegisterService;
import com.xm01.system.service.ISysConfigService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 注册验证
 * 
 * @author ruoyi
 */
@RestController
public class SysRegisterController extends BaseController
{
    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private ISysConfigService configService;


    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterBody user)
    {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser"))))
        {
            return error("当前系统没有开启注册功能！");
        }
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }
    /**
     * 以下是为实现用户通过邮箱验证找回密码功能添加的，等有能力了应该剥离出来
     *
     *
     * java 2025.12.15
     */
    @Autowired
    private ISysUserService userService;
    /**
     * 发送验证码之前的有效检验方法
     *
     * @param user 传参信息
     * @return 结果
     */
    @Anonymous//没有登录也能用
    @PostMapping("/sendcode")
    public AjaxResult sendcode(@RequestBody RegisterBody user)
    {
        String msg = "",
                userName=user.getUsername(),
                userEmail=user.getEamil();
        System.out.println("sendcode"+userName+userEmail);
        SysUser use= userService.selectUserByUserName(userName);
        System.out.println("sendcode"+use);
        if(use == null){
            msg = "没有该用户";
        } else if (!use.getEmail().equals( userEmail) ) {
            msg = "用户邮箱输入错误";
        }
        else{
            AjaxResult ajax = AjaxResult.success();
            //ajax
        }
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }

    /**
     * 通过邮箱验证重置密码
     */

    @Anonymous
    @PostMapping("/repassword")
    public AjaxResult repassWord(@RequestBody RegisterBody user)
    {
        String msg = "",
                userName=user.getUsername(),
                password=user.getPassword();
        System.out.println("[repassWord]"+userName+password);

        String newPassword = SecurityUtils.encryptPassword(password);
        if (userService.resetUserPwd(userName, newPassword) > 0)
        {
            return success();
        }
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }


    /**
     * 发送短信
     */
    @Anonymous
    @PostMapping("/email")
    public AjaxResult sendemail(@RequestBody EmailRequest emailRequest ){
            String msg = "";
            String email =emailRequest.getEmail();
            String code = emailRequest.getCode();
            System.out.println("sendemail:"+"email="+email+"code="+code);
            if (email == null || code == null) {
                msg="缺少必需的参数";
            }

            /*
             * 只需要调用两条语句即可发送邮件：
             * 1.Email mail = new Email();			//声明对象
             * 2.mail.send("要发送到的邮件地址，例如：xx@163.com", "你注册的邮件所在smtp服务器", "你注册的邮箱", "登录密码", subject, content);
             */
            // 获取当前日期时间
            LocalDateTime now = LocalDateTime.now();
            // 格式化当前时间
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedNow = now.format(formatter);


            EmailServer emailServer= new EmailServer();
            String[] emailbox={email};
            String smtp="smtp.qq.com";
            String from="xu2044247729@qq.com";
            String passwd="bbrfnfmcinxyjgef";
            String subject="【交通视频分析系统】找回密码邮箱验证";
            String content="<p>您本次找回密码的邮箱验证码是:</p>\n" +
                    "<p>"+code+"</p>\n" +
                    "<p>有效期5分钟，请勿泄露，妥善保管</p>\n"+
                    "<p>【交通视频分析系统xm01】</p>\n【"+
                    formattedNow +"】";
            emailServer.send(emailbox,smtp,from,passwd,subject,content);

        return  StringUtils.isEmpty(msg) ? success() : error(msg);

    }
    public static  class EmailRequest {
        private String email;
        private String code;

        // Getters and Setters
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
