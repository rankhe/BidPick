package com.ruoyi.product.mapper;

import com.ruoyi.product.domain.BatchProduct;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BatchProductMapper
{
    BatchProduct selectBatchProductById(Long id);
    List<BatchProduct> selectBatchProductList(BatchProduct cond);
    int insertBatchProduct(BatchProduct bp);
    int updateBatchProduct(BatchProduct bp);
    int deleteBatchProductById(Long id);

    BatchProduct selectByBatchAndProd(@Param("batchId") Long batchId, @Param("prodId") Long prodId);
    int increaseLockedCount(@Param("id") Long id, @Param("delta") Integer delta);
}

