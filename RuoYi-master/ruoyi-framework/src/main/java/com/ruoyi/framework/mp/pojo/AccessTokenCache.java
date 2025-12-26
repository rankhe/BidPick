package com.ruoyi.framework.mp.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccessTokenCache implements Serializable
{
    String  accessToken;
    Long expireTime;
}
