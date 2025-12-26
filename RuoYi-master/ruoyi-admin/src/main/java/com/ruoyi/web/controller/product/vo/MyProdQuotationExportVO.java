package com.ruoyi.web.controller.product.vo;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

@Data
public class MyProdQuotationExportVO implements Serializable
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

    /** 产品名称 */
    @Excel(name = "产品子类别1")
    private String subCategory1;

    /** 产品名称 */
    @Excel(name = "产品子类别2")
    private String subCategory2;

    /** 产品批次ID */
    @Excel(name = "产品批次ID")
    private Long batchId;


    /** 意向数量 */
    @Excel(name = "意向数量")
    private Long count;

    /** 报单价 */
    @Excel(name = "报单价")
    private Long price;

}
