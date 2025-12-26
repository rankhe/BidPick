package com.ruoyi.product.service;


import net.sf.ehcache.util.ProductInfo;

import java.util.List;

public interface IProductInfoService
{
    /**
     * 产品融资信息
     *
     * @param batchId 产品批次号
     * @return 融资历史集合
     */
    List<ProductInfo> selectProductInfoList(Long batchId);
}
