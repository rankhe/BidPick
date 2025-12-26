package com.ruoyi.product.mapper;

import com.ruoyi.product.domain.ProdQuotationHistory;

import java.util.List;

/**
 * 产品报价信息Mapper接口
 * 
 * @author ruoyi
 * @date 2023-09-01
 */
public interface ProdQuotationHistoryMapper 
{
    /**
     * 查询产品报价信息
     * 
     * @param id 产品报价信息主键
     * @return 产品报价信息
     */
    public ProdQuotationHistory selectProdQuotationHistoryById(Long id);

    /**
     * 查询产品报价信息列表
     * 
     * @param prodQuotationHistory 产品报价信息
     * @return 产品报价信息集合
     */
    public List<ProdQuotationHistory> selectProdQuotationHistoryList(ProdQuotationHistory prodQuotationHistory);

    /**
     * 新增产品报价信息
     * 
     * @param prodQuotationHistory 产品报价信息
     * @return 结果
     */
    public int insertProdQuotationHistory(ProdQuotationHistory prodQuotationHistory);

    /**
     * 修改产品报价信息
     * 
     * @param prodQuotationHistory 产品报价信息
     * @return 结果
     */
    public int updateProdQuotationHistory(ProdQuotationHistory prodQuotationHistory);

    /**
     * 删除产品报价信息
     * 
     * @param id 产品报价信息主键
     * @return 结果
     */
    public int deleteProdQuotationHistoryById(Long id);

    /**
     * 批量删除产品报价信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProdQuotationHistoryByIds(String[] ids);

    /**
     * 检查报价记录的唯一性
     * @param history
     * @return
     */
    ProdQuotationHistory checkProdHistoryUnique(ProdQuotationHistory history);

    int sumAwardedCount(java.util.Map<String,Object> params);
}
