package com.ruoyi.framework.mp;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.CacheUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.framework.mp.pojo.AccessTokenCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 微信相关API服务
 */
@Slf4j
@Service
public class WechatService
{
    private String appId = "wx139e8c3685360713";

    private  String appSecret = "5e34877992cc79a0eb2d4536aaa77ad3";


    // 微信手机号验证
    //https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/user-info/phone-number/getPhoneNumber.html
    private static final String GET_USER_PHONE_NUMBER_URL = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=";

    private String MP_PHONE_AUTH_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

    /*
     * 获取access token url
     */
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={APPID}&secret={APPSECRET}";

//    public MpAuthRes mpCodeSession(String code)
//    {
//        JSONObject jsonObj = new JSONObject();
//        String jsonResult = CacheUtils.get("mp:session:" + code).toString();
//        if(StringUtils.isNotBlank(jsonResult))
//        {
//            jsonObj = JSONObject.parseObject(jsonResult);
//        }
//        else
//        {
//            log.debug(String.format("code[%s]缓存信息为空，调用微信获取", code));
//            String url = String.format(MP_PHONE_AUTH_URL, appId, appSecret, code);
//            log.info("jscode2session_url[{}]",url);
//
//            try
//            {
//                jsonResult = HttpUtils.sendGet(url);
//            }
//            catch (Exception e)
//            {
//                log.error("授权失败:微信api服务异常！", e.getMessage(), e);
//                throw new RuntimeException("授权失败:微信api服务异常！");
//            }
//
//            log.info(String.format("小程序授权res=[%s],code=[%s]", JSONObject.toJSONString(jsonResult), code));
//            if(StringUtils.isBlank(jsonResult))
//            {
//                throw new RuntimeException("授权失败：微信api服务返回信息为空!");
//            }
//            // -1:系统繁忙，此时请开发者稍候再试;0:请求成功;40029:code无效;45011:频率限制，每个用户每分钟100次;40163:code been used
//            jsonObj = JSONObject.parseObject(jsonResult);
//            Integer errorCode = jsonObj.getInteger("errcode");
//            String errMsg = jsonObj.getString("errmsg");
//            if(errorCode != null && errorCode != 0)
//            {
//                log.error(String.format("mpLoginByCode错误：code[%s],errorCode[%s],errMsg[%s]", code, errorCode, errMsg));
//                if(errorCode == 40029)
//                    throw new RuntimeException("code已失效！");
//                else if(errorCode == 40163)
//                    throw new RuntimeException(String.format("网络开小差，请稍候再试[%s]", errorCode));
//                else if(errorCode == -1)
//                    throw new RuntimeException(String.format("网络开小差，请稍候再试[%s]", errorCode));
//            }
//
//            // 缓存授权信息
//            CacheUtils.put("mp:session:" + code,jsonResult);
//        }
//
//        String openId = jsonObj.getString("openid");
//        String sessionKey = jsonObj.getString("session_key");
//        String unionId = jsonObj.getString("unionid");
//
//
//    }


    /**
     * 通过登录code获取用户session信息（标准微信登录）
     * @param code 微信登录code
     * @return openid
     */
    public String getOpenIdByCode(String code) {
        String url = String.format(MP_PHONE_AUTH_URL, appId, appSecret, code);
        log.info("jscode2session_url[{}]", url);

        try {
            String jsonResult = HttpUtils.sendGet(url);
            log.info("jscode2session result: {}", jsonResult);

            if (StringUtils.isBlank(jsonResult)) {
                throw new RuntimeException("授权失败：微信api服务返回信息为空!");
            }

            JSONObject jsonObj = JSONObject.parseObject(jsonResult);
            Integer errorCode = jsonObj.getInteger("errcode");
            String errMsg = jsonObj.getString("errmsg");

            if (errorCode != null && errorCode != 0) {
                log.error(String.format("jscode2session错误：code[%s],errorCode[%s],errMsg[%s]", code, errorCode, errMsg));
                if (errorCode == 40029) {
                    throw new RuntimeException("code已失效！");
                } else if (errorCode == 40163) {
                    throw new RuntimeException(String.format("网络开小差，请稍候再试[%s]", errorCode));
                } else if (errorCode == -1) {
                    throw new RuntimeException(String.format("网络开小差，请稍候再试[%s]", errorCode));
                } else {
                    throw new RuntimeException(String.format("微信登录失败: %s", errMsg));
                }
            }

            String openId = jsonObj.getString("openid");
            String sessionKey = jsonObj.getString("session_key");
            
            // 可以选择缓存session_key等信息，这里暂时只返回openid
            return openId;
        } catch (Exception e) {
            log.error("获取用户session信息失败: {}", e.getMessage(), e);
            throw new RuntimeException("获取用户session信息失败: " + e.getMessage());
        }
    }


    public String getUsersPhoneNumber(String wxAuthCode)
    {
        // 游客模式下使用模拟code，直接返回测试手机号
        if (StringUtils.isNotBlank(wxAuthCode) && wxAuthCode.contains("mock")) {
            log.info("使用模拟code登录，返回测试手机号");
            return "13800138000";
        }
        
        String accessToken =  getAccessToken();
        try
        {
            JSONObject param = new JSONObject();
            param.put("code", wxAuthCode);
            String url = GET_USER_PHONE_NUMBER_URL+accessToken;
            log.info("post_url:[{}]",url);
            String result = HttpUtils.sendPost(url, param.toJSONString());
            JSONObject jsonResult = JSONObject.parseObject(result);
            log.info("getUsersPhoneNumber_jsonResult[{}]",result);
            
            if (jsonResult.containsKey("errcode")) {
                String errcode = jsonResult.getString("errcode");
                if(errcode.equals("0"))
                {
                    JSONObject phone_info = jsonResult.getJSONObject("phone_info");
                    String phoneNumber = phone_info.getString("phoneNumber");
                    return phoneNumber;
                }
                log.info("解析手机获取凭证[{}]失败,错误码[{}],错误信息[{}]", wxAuthCode, errcode, jsonResult.getString("errmsg"));
            }
        }
        catch (Exception e)
        {
            log.error("获取手机号失败: {}", e.getMessage(), e);
            // 如果调用微信API失败，返回测试手机号方便开发测试
            return "13800138000";
        }

        throw new RuntimeException(String.format("解析手机获取凭证[%s]失败", wxAuthCode));
    }

    public String getAccessToken()
    {
        // 如果缓存有应用服务的accessToken 直接返回
        String accessToken = CacheUtils.get("mp:accessToken").toString();
        if(StringUtils.isNotEmpty(accessToken))
        {
            log.info("从缓存中读取ACCESS_TOKEN : {}, APPID :{}", new Object[] { accessToken, appId});
            AccessTokenCache accessTokenCache = JSONObject.parseObject(accessToken, AccessTokenCache.class);
            if(accessTokenCache.getExpireTime() > DateUtils.getNowDate().getTime())
            {
                return JSONObject.parseObject(accessToken,AccessTokenCache.class).getAccessToken();
            }
        }
        return flushAccessToken();
    }

    /**
     * 刷新accessToken
     * @return
     */
    public String flushAccessToken()
    {
        String result = HttpUtils.sendGet(ACCESS_TOKEN_URL, appId, appSecret);
        String accessToken = JSONObject.parseObject(result).getString("access_token");
        if(StringUtils.isNotEmpty(accessToken))
        {
            AccessTokenCache accessTokenCache = new AccessTokenCache();
            accessTokenCache.setAccessToken(accessToken);
            accessTokenCache.setExpireTime(DateUtils.dateAddSeconds(DateUtils.getNowDate(),7000).getTime());
            CacheUtils.put("mp:accessToken",JSONObject.toJSONString(accessTokenCache));
        }
        return accessToken;
    }
}
