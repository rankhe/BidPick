package com.ruoyi.product.service.impl;

import com.ruoyi.product.domain.BatchProduct;
import com.ruoyi.product.mapper.BatchProductMapper;
import com.ruoyi.product.service.IBatchProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchProductServiceImpl implements IBatchProductService
{
    @Autowired private BatchProductMapper mapper;

    @Override public BatchProduct selectBatchProductById(Long id) { return mapper.selectBatchProductById(id); }
    @Override public List<BatchProduct> selectBatchProductList(BatchProduct cond) { return mapper.selectBatchProductList(cond); }
    @Override public int insertBatchProduct(BatchProduct bp) { return mapper.insertBatchProduct(bp); }
    @Override public int updateBatchProduct(BatchProduct bp) { return mapper.updateBatchProduct(bp); }
    @Override public int deleteBatchProductById(Long id) { return mapper.deleteBatchProductById(id); }
    @Override public BatchProduct selectByBatchAndProd(Long batchId, Long prodId) { return mapper.selectByBatchAndProd(batchId, prodId); }
    @Override public int increaseLockedCount(Long id, Integer delta) { return mapper.increaseLockedCount(id, delta); }
}

