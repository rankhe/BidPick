package com.ruoyi.product.mapper;

import com.ruoyi.product.domain.BatchInfo;

import java.util.List;

/**
 * 批次信息Mapper接口
 * 
 * @author ruoyi
 * @date 2023-09-01
 */
public interface BatchInfoMapper 
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
     * 删除批次信息
     * 
     * @param id 批次信息主键
     * @return 结果
     */
    public int deleteBatchInfoById(Long id);

    /**
     * 批量删除批次信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBatchInfoByIds(String[] ids);
}
