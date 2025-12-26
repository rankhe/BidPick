package com.ruoyi.web.controller.product.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProdInfoVO implements Serializable
{

    private Long id;

    /** 产品名称 */
    @Excel(name = "品牌")
    private String brandName;

    @Excel(name = "产品名称")
    private String name;

    /** 产品名称 */
    @Excel(name = "产品类别")
    private String categoryName;

    /** 批次ID */
    @Excel(name = "批次ID")
    private String batchNo;

    /** 细分类别1数量 */
    @Excel(name = "细分类别1数量")
    private Long t1Cnt;

    /** 细分类别2数量 */
    @Excel(name = "细分类别2数量")
    private Long t2Cnt;

    /** 细分类别3数量 */
    @Excel(name = "细分类别3数量")
    private Long t3Cnt;

    /** 数量 */
    @Excel(name = "数量")
    private Long count;

    /** 细分类别1 */
    @Excel(name = "细分类别1")
    private String subtype1;

    /** 细分类别2 */
    @Excel(name = "细分类别2")
    private String subtype2;

    @Excel(name = "状态1 正常")
    private Long status;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
