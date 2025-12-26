package com.ruoyi.web.controller.product.vo;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProdInfoExcelVO implements Serializable
{
    /** $column.columnComment */
    private Long id;

//    /** 批次ID */
//    @Excel(name = "批次ID")
//    private String batchNo;

    /** 品牌 */
    @Excel(name = "品牌")
    private String brandName;

    /** 产品型号 */
    @Excel(name = "型号")
    private String name;


    /** 产品分类 */
    @Excel(name = "产品分类")
    private String categoryName;

    /** 产品分类 */
    @Excel(name = "分类等级")
    private Integer cateLevel;

    /** 数量 */
    @Excel(name = "数量")
    private Long count;


}
