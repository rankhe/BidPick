package com.ruoyi.web.controller.product.vo;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

@Data
public class BatchProductExportVO implements Serializable {
    @Excel(name = "产品名称")
    private String name;

    @Excel(name = "品牌")
    private String brandName;

    @Excel(name = "分类")
    private String categoryName;

    @Excel(name = "发布数量")
    private Integer publishCount;

    @Excel(name = "已锁定数量")
    private Integer lockedCount;

    @Excel(name = "状态")
    private String statusLabel;
}
