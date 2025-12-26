package com.ruoyi.web.controller.product.vo;

import com.ruoyi.common.annotation.Excel;

import java.io.Serializable;

public class ProdCategoryVO implements Serializable
{
    /** $column.columnComment */
    private Long id;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String name;

    /** 展示顺序 */
    @Excel(name = "展示顺序")
    private Long idx;

    @Excel(name = "分类的等级")
    private String level;

    @Excel(name = "父级分类ID")
    private Long parentId;

    /** 分类状态1 有效 */
    @Excel(name = "分类状态")
    private Long status = 1l;

    /** 创建人 */
    @Excel(name = "创建人")
    private String creator;

    /** 更新人 */
    @Excel(name = "更新人")
    private String updator;

}
