package com.ruoyi.product.service;

import com.ruoyi.product.domain.BatchInfo;

import java.util.List;

/**
 * 批次信息Service接口
 * 
 * @author ruoyi
 * @date 2023-09-01
 */
public interface IBatchInfoService 
{
    /**
     * 查询批次信息
     * 
     * @param id 批次信息主键
     * @return 批次信息
     */
    public BatchInfo selectBatchInfoById(Long id);

    /**
     * 查询批次信息列表
     * 
     * @param batchInfo 批次信息
     * @return 批次信息集合
     */
    public List<BatchInfo> selectBatchInfoList(BatchInfo batchInfo);

    /**
     * 产品融资信息
     *
     * @param deptId 批次所属deptId
     * @return 融资历史集合
     */
    public List<BatchInfo> selectProductBatchInfoList(Long deptId);

    /**
     * 新增批次信息
     * 
     * @param batchInfo 批次信息
     * @return 结果
     */
    public int insertBatchInfo(BatchInfo batchInfo);

    /**
     * 修改批次信息
     * 
     * @param batchInfo 批次信息
     * @return 结果
     */
    public int updateBatchInfo(BatchInfo batchInfo);

    /**
     * 批量删除批次信息
     * 
     * @param ids 需要删除的批次信息主键集合
     * @return 结果
     */
    public int deleteBatchInfoByIds(String ids);

    /**
     * 删除批次信息信息
     * 
     * @param id 批次信息主键
     * @return 结果
     */
    public int deleteBatchInfoById(Long id);
}
