package com.ruoyi.product.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 产品信息对象 prod_info
 * 
 * @author ruoyi
 * @date 2023-09-01
 */
public class ProdInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 产品名称 */
    @Excel(name = "名称/型号")
    private String name;

    /** 产品名称 */
    @Excel(name = "品牌")
    private String brandName;

    /** 产品分类id */
    @Excel(name = "产品分类id")
    private Long categoryId;

    @Excel(name = "分类")
    private String categoryName;

    /** 数量 */
    @Excel(name = "数量")
    private Long count;

    private Long lockedCount;

    /** 展示顺序 */
    @Excel(name = "展示顺序")
    private Long idx;

    /** 状态1 正常 */
    @Excel(name = "状态1 正常")
    private Long status;


    @Excel(name = "部门ID")
    private Long deptId;

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
    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }

    public void setCount(Long count) 
    {
        this.count = count;
    }

    public Long getCount() 
    {
        return count;
    }
    public void setLockedCount(Long lockedCount)
    {
        this.lockedCount = lockedCount;
    }

    public Long getLockedCount()
    {
        return lockedCount;
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

    public String getBrandName()
    {
        return brandName;
    }

    public void setBrandName(String brandName)
    {
        this.brandName = brandName;
    }


    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
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
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("brandName", getBrandName())
                .append("categoryName",getCategoryName())
                .append("categoryId", getCategoryId())
                .append("deptId", getDeptId())
                .append("count", getCount())
                .append("lockedCount", getLockedCount())
                .append("idx", getIdx())
                .append("status", getStatus())
                .append("updateTime", getUpdateTime())
                .append("createTime", getCreateTime())
                .toString();
    }
}
