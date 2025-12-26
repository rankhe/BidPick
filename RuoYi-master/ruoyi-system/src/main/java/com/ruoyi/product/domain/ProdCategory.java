package com.ruoyi.product.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 产品分类对象 prod_category
 * 
 * @author ruoyi
 * @date 2023-09-01
 */
public class ProdCategory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String name;

    /** 展示顺序 */
    @Excel(name = "展示顺序")
    private Long idx;

    @Excel(name = "分类的等级")
    private Integer level;

    @Excel(name = "父级分类ID")
    private Long parentId;

    @Excel(name = "品牌ID")
    private Long brandId;

    @Excel(name = "品牌")
    private String brandName;

    @Excel(name = "部门ID")
    private Long deptId;

    /** 分类状态1 有效 */
    @Excel(name = "分类状态")
    private Long status = 1l;

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
    public void setIdx(Long idx) 
    {
        this.idx = idx;
    }

    public Long getIdx() 
    {
        return idx;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
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

    public Integer getLevel()
    {
        return level;
    }

    public void setLevel(Integer level)
    {
        this.level = level;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public Long getBrandId()
    {
        return brandId;
    }

    public void setBrandId(Long brandId)
    {
        this.brandId = brandId;
    }

    public String getBrandName()
    {
        return brandName;
    }

    public void setBrandName(String brandName)
    {
        this.brandName = brandName;
    }

    public Long getDeptId()
    {
        return deptId;
    }

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("level", getLevel())
                .append("parentId", getParentId())
                .append("brandId", getBrandId())
                .append("brandName", getBrandName())
                .append("deptId", getDeptId())
                .append("idx", getIdx())
                .append("status", getStatus())
                .append("updateTime", getUpdateTime())
                .append("createTime", getCreateTime())
                .append("creator", getCreator())
                .append("updator", getUpdator())
                .toString();
    }
}
