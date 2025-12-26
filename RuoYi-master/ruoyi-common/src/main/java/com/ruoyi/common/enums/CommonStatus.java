package com.ruoyi.common.enums;

public enum CommonStatus
{
    DEFAULT(0l, "默认"),
    OK(1l, "正常"),
    DISABLE(-1l, "禁用");

    private final Long code;
    private final String info;

    CommonStatus(Long code, String info)
    {
        this.code = code;
        this.info = info;
    }

    public Long getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }
}
