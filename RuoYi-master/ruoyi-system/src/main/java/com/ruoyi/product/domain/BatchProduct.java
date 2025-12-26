package com.ruoyi.product.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BatchProduct extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "批次ID")
    private Long batchId;

    @Excel(name = "产品ID")
    private Long prodId;

    @Excel(name = "发布数量")
    private Integer publishCount;

    @Excel(name = "锁定数量")
    private Integer lockedCount;

    private Integer idx;

    private Integer status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getBatchId() { return batchId; }
    public void setBatchId(Long batchId) { this.batchId = batchId; }
    public Long getProdId() { return prodId; }
    public void setProdId(Long prodId) { this.prodId = prodId; }
    public Integer getPublishCount() { return publishCount; }
    public void setPublishCount(Integer publishCount) { this.publishCount = publishCount; }
    public Integer getLockedCount() { return lockedCount; }
    public void setLockedCount(Integer lockedCount) { this.lockedCount = lockedCount; }
    public Integer getIdx() { return idx; }
    public void setIdx(Integer idx) { this.idx = idx; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("batchId", getBatchId())
            .append("prodId", getProdId())
            .append("publishCount", getPublishCount())
            .append("lockedCount", getLockedCount())
            .append("idx", getIdx())
            .append("status", getStatus())
            .append("updateTime", getUpdateTime())
            .append("createTime", getCreateTime())
            .toString();
    }
}

