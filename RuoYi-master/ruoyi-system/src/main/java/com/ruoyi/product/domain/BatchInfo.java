package com.ruoyi.product.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 批次信息对象 batch_info
 * 
 * @author ruoyi
 * @date 2023-09-01
 */
public class BatchInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 批次编码 */
    @Excel(name = "批次编码")
    private String batchNo;

    /** 所属人 */
    @Excel(name = "所属人")
    private Long ownerId;

    /** 状态1 正常 */
    @Excel(name = "状态1 正常")
    private Long status;

    @Excel(name = "开始投标时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @Excel(name = "截止投标时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /** 已报价人数 */
    @Excel(name = "已报价人数")
    private Long quotation;

    /** 批次说明介绍 */
    @Excel(name = "批次说明介绍")
    private String remark;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setBatchNo(String batchNo) 
    {
        this.batchNo = batchNo;
    }

    public String getBatchNo() 
    {
        return batchNo;
    }
    public void setOwnerId(Long ownerId) 
    {
        this.ownerId = ownerId;
    }

    public Long getOwnerId() 
    {
        return ownerId;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setQuotation(Long quotation) 
    {
        this.quotation = quotation;
    }

    public Long getQuotation() 
    {
        return quotation;
    }

    @Override public String getRemark()
    {
        return remark;
    }

    @Override public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public LocalDateTime getStartTime()
    {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime)
    {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime()
    {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime)
    {
        this.endTime = endTime;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("batchNo", getBatchNo())
                .append("ownerId", getOwnerId())
                .append("createTime", getCreateTime())
                .append("status", getStatus())
                .append("startTime", getStartTime())
                .append("endTime", getEndTime())
                .append("updateTime", getUpdateTime())
                .append("quotation", getQuotation())
                .append("remark", getRemark())
                .toString();
    }
}
