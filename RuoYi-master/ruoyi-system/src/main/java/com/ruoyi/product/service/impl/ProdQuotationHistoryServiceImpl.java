package com.ruoyi.product.service.impl;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.product.domain.BatchInfo;
import com.ruoyi.product.domain.ProdQuotationHistory;
import com.ruoyi.product.mapper.BatchInfoMapper;
import com.ruoyi.product.mapper.ProdQuotationHistoryMapper;
import com.ruoyi.product.service.IProdQuotationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 产品报价信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-09-01
 */
@Service
public class ProdQuotationHistoryServiceImpl implements IProdQuotationHistoryService
{
    @Autowired
    private ProdQuotationHistoryMapper prodQuotationHistoryMapper;

    @Autowired
    private BatchInfoMapper batchInfoMapper;

    /**
     * 查询产品报价信息
     * 
     * @param id 产品报价信息主键
     * @return 产品报价信息
     */
    @Override
    public ProdQuotationHistory selectProdQuotationHistoryById(Long id)
    {
        return prodQuotationHistoryMapper.selectProdQuotationHistoryById(id);
    }

    /**
     * 查询产品报价信息列表
     * 
     * @param prodQuotationHistory 产品报价信息
     * @return 产品报价信息
     */
    @Override
    public List<ProdQuotationHistory> selectProdQuotationHistoryList(ProdQuotationHistory prodQuotationHistory)
    {
        return prodQuotationHistoryMapper.selectProdQuotationHistoryList(prodQuotationHistory);
    }

    /**
     * 新增产品报价信息
     * 
     * @param prodQuotationHistory 产品报价信息
     * @return 结果
     */
    @Override
    public int insertProdQuotationHistory(ProdQuotationHistory prodQuotationHistory)
    {
        prodQuotationHistory.setCreateTime(DateUtils.getNowDate());
        Integer result = prodQuotationHistoryMapper.insertProdQuotationHistory(prodQuotationHistory);
        if(prodQuotationHistory.getBatchId() != null)
        {
            BatchInfo batchInfo = batchInfoMapper.selectBatchInfoById(prodQuotationHistory.getBatchId());
            if(batchInfo != null)
            {
                batchInfo.setQuotation(batchInfo.getQuotation() + 1);
                batchInfoMapper.updateBatchInfo(batchInfo);
            }
        }
        return result;
    }

    /**
     * 修改产品报价信息
     * 
     * @param prodQuotationHistory 产品报价信息
     * @return 结果
     */
    @Override
    public int updateProdQuotationHistory(ProdQuotationHistory prodQuotationHistory)
    {
        prodQuotationHistory.setUpdateTime(DateUtils.getNowDate());
        return prodQuotationHistoryMapper.updateProdQuotationHistory(prodQuotationHistory);
    }

    /**
     * 批量删除产品报价信息
     * 
     * @param ids 需要删除的产品报价信息主键
     * @return 结果
     */
    @Override
    public int deleteProdQuotationHistoryByIds(String ids)
    {
        return prodQuotationHistoryMapper.deleteProdQuotationHistoryByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除产品报价信息信息
     * 
     * @param id 产品报价信息主键
     * @return 结果
     */
    @Override
    public int deleteProdQuotationHistoryById(Long id)
    {
        return prodQuotationHistoryMapper.deleteProdQuotationHistoryById(id);
    }

    @Override public ProdQuotationHistory checkProdHistoryUnique(ProdQuotationHistory history)
    {
        return prodQuotationHistoryMapper.checkProdHistoryUnique(history);
    }

    @Override
    public int sumAwardedCount(Long batchProductId)
    {
        java.util.HashMap<String,Object> p = new java.util.HashMap<>();
        p.put("batchProductId", batchProductId);
        return prodQuotationHistoryMapper.sumAwardedCount(p);
    }


}
