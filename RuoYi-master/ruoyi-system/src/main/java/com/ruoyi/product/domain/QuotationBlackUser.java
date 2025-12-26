package com.ruoyi.product.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 报价黑名单信息对象 quotation_balck_user
 * 
 * @author ruoyi
 * @date 2023-09-07
 */
public class QuotationBlackUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 手机号 */
    @Excel(name = "手机号")
    private String mobile;


    @Excel(name = "部门ID")
    private Long deptId;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setMobile(String mobile) 
    {
        this.mobile = mobile;
    }

    public String getMobile() 
    {
        return mobile;
    }

    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Long getDeptId()
    {
        return deptId;
    }

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public Integer getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("mobile", getMobile())
            .append("deptId", getDeptId())
            .append("updateBy", getUpdateBy())
            .append("status", getStatus())
            .append("updateTime", getUpdateTime())
            .append("createTime", getCreateTime())
            .toString();
    }
}
