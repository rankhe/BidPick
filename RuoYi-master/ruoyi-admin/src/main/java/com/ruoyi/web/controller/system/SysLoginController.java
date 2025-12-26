package com.ruoyi.web.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.mp.WechatService;
import com.ruoyi.framework.shiro.auth.MiniprogramPhoneToken;
import com.ruoyi.framework.web.service.ConfigService;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.service.ISysMenuService;
import com.ruoyi.system.service.ISysRoleService;
import java.util.Set;

/**
 * 登录验证
 * 
 * @author ruoyi
 */
@Controller
public class SysLoginController extends BaseController
{
    /**
     * 是否开启记住我功能
     */
    @Value("${shiro.rememberMe.enabled: false}")
    private boolean rememberMe;

    @Autowired
    private ConfigService configService;
    
    @Autowired
    private WechatService wechatService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private ISysRoleService roleService;

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, ModelMap mmap)
    {
        // 如果是Ajax请求，返回Json字符串。
        if (ServletUtils.isAjaxRequest(request))
        {
            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }
        // 是否开启记住我
        mmap.put("isRemembered", rememberMe);
        // 是否开启用户注册
        mmap.put("isAllowRegister", Convert.toBool(configService.getKey("sys.account.registerUser"), false));
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public AjaxResult ajaxLogin(String username, String password, Boolean rememberMe)
    {
        // 确保rememberMe不为null，默认值为false
        boolean remember = rememberMe != null && rememberMe;
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, remember);
        Subject subject = SecurityUtils.getSubject();
        try
        {
            subject.login(token);
            return success();
        }
        catch (AuthenticationException e)
        {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage()))
            {
                msg = e.getMessage();
            }
            return error(msg);
        }
    }

    @GetMapping("/unauth")
    public String unauth()
    {
        return "error/unauth";
    }
    
    /**
     * 微信小程序登录
     */
    @PostMapping("/wx/login")
    @ResponseBody
    public AjaxResult wechatLogin(String code)
    {
        if (StringUtils.isBlank(code))
        {
            return error("微信登录失败，code不能为空");
        }
        
        try
        {
            // 使用微信小程序登录令牌
            MiniprogramPhoneToken token = new MiniprogramPhoneToken(code);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            // 新增：返回sessionId作为token
            AjaxResult ajax = success();
            ajax.put("code", 200);
            ajax.put("token", subject.getSession().getId());
            return ajax;
        }
        catch (AuthenticationException e)
        {
            String msg = "微信登录失败";
            if (StringUtils.isNotEmpty(e.getMessage()))
            {
                msg = e.getMessage();
            }
            logger.error("微信登录失败: code={}, msg={}", code, msg, e);
            return error(msg);
        }
    }

    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    @GetMapping("/getInfo")
    @ResponseBody
    public AjaxResult getInfo()
    {
        SysUser user = ShiroUtils.getSysUser();
        // 角色集合
        Set<String> roles = roleService.selectRoleKeys(user.getUserId());
        // 权限集合
        Set<String> permissions = menuService.selectPermsByUserId(user.getUserId());
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        ajax.put("code", 200);
        return ajax;
    }
}
