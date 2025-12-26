package com.ruoyi.web.controller.product.vo;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProdQuotationHistoryVO implements Serializable
{
    /** $column.columnComment */
    private Long id;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String name;

    /** 产品分类id */
    @Excel(name = "产品分类id")
    private Long prodId;

    /** 产品名称 */
    @Excel(name = "产品类别")
    private String prodCategory;

    /** 报价人手机号 */
    @Excel(name = "报价人手机号")
    private Long quoterUserMobile;

    /** 产品批次ID */
    @Excel(name = "产品批次ID")
    private String batchNo;

    /** 报价人微信昵称 */
    @Excel(name = "报价人微信昵称")
    private String nickName;

    /** 意向数量 */
    @Excel(name = "意向数量")
    private Long count;

    /** 报单价 */
    @Excel(name = "报单价")
    private Long price;

    /** 状态1 正常 */
    @Excel(name = "状态1 正常")
    private Long status;

    private Integer awardStatus;

    private java.util.Date awardTime;
}
