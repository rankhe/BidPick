package com.ruoyi.web.controller.product.vo;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ProdBatchVO implements Serializable
{
    /** $column.columnComment */
    private Long id;

    /** 批次编码 */
    @Excel(name = "批次编码")
    private String batchNo;

    /** 所属人 */
    @Excel(name = "所属人")
    private Long ownerId;

    /** 所属人 */
    @Excel(name = "所属人")
    private String ownerUser;

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
}
