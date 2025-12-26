package com.ruoyi.product.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 产品品牌对象 prod_brand
 * 
 * @author ruoyi
 * @date 2023-09-08
 */
public class ProdBrand extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 品牌名称 */
    @Excel(name = "品牌名称")
    private String name;

    /** 品牌描述 */
    @Excel(name = "品牌描述")
    private String introduction;

    /** 状态1 有效 */
    @Excel(name = "状态1 有效")
    private Integer status;


    @Excel(name = "部门ID")
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

    public String getIntroduction()
    {
        return introduction;
    }

    public void setIntroduction(String introduction)
    {
        this.introduction = introduction;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getStatus() 
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
