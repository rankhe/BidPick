package com.ruoyi.product.service;

import com.ruoyi.product.domain.BatchProduct;
import java.util.List;

public interface IBatchProductService
{
    BatchProduct selectBatchProductById(Long id);
    List<BatchProduct> selectBatchProductList(BatchProduct cond);
    int insertBatchProduct(BatchProduct bp);
    int updateBatchProduct(BatchProduct bp);
    int deleteBatchProductById(Long id);
    BatchProduct selectByBatchAndProd(Long batchId, Long prodId);
    int increaseLockedCount(Long id, Integer delta);
}

