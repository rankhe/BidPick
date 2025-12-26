package com.ruoyi.framework.shiro.auth;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

public class MiniprogramPhoneToken implements HostAuthenticationToken, RememberMeAuthenticationToken
{
    // 微信临时code
    private String code;

    private String mobile;

    /**
     * The password, in char[] format
     */
    private char[] password;


    private boolean rememberMe = false;

    private String host;



    public MiniprogramPhoneToken(String code)
    {
        this(code, false, null);
    }

    public MiniprogramPhoneToken(String code, boolean rememberMe, String host)
    {
        this.code = code;
        this.rememberMe = rememberMe;
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    @Override public boolean isRememberMe()
    {
        return rememberMe;
    }

    @Override public Object getPrincipal()
    {
        return getMobile();
    }

    @Override public Object getCredentials()
    {
        return "";
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public char[] getPassword()
    {
        return password;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }
}
