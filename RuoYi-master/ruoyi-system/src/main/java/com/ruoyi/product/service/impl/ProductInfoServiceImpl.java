package com.ruoyi.product.service.impl;

import com.ruoyi.product.service.IProductInfoService;
import com.ruoyi.product.mapper.ProductInfoMapper;
import net.sf.ehcache.util.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements IProductInfoService
{

    @Autowired ProductInfoMapper productInfoMapper;

    @Override public List<ProductInfo> selectProductInfoList(Long batchId)
    {
        return productInfoMapper.selectProductInfoList(batchId);
    }
}
