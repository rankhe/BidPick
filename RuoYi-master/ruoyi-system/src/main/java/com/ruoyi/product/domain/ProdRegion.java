package com.ruoyi.product.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 地区区域对象 prod_region
 * 
 * @author ruoyi
 * @date 2024-07-29
 */
public class ProdRegion extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 区域名称 */
    @Excel(name = "区域名称")
    private String name;

    /** 区域描述 */
    @Excel(name = "区域描述")
    private String introduction;

    /** 状态1 有效 */
    @Excel(name = "状态1 有效")
    private Long status;

    /** 所有人 */
    @Excel(name = "部门Id")
    private Long deptId;

    /** 创建人 */
    @Excel(name = "创建人")
    private String creator;

    /** 更新人 */
    @Excel(name = "更新人")
    private String updator;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setIntroduction(String introduction) 
    {
        this.introduction = introduction;
    }

    public String getIntroduction() 
    {
        return introduction;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    public Long getDeptId()
    {
        return deptId;
    }

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    public String getCreator() 
    {
        return creator;
    }
    public void setUpdator(String updator) 
    {
        this.updator = updator;
    }

    public String getUpdator() 
    {
        return updator;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("introduction", getIntroduction())
            .append("status", getStatus())
            .append("deptId", getDeptId())
            .append("updateTime", getUpdateTime())
            .append("createTime", getCreateTime())
            .append("creator", getCreator())
            .append("updator", getUpdator())
            .toString();
    }
}
