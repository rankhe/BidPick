package com.ruoyi.product.service.impl;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.product.domain.BatchInfo;
import com.ruoyi.product.service.IBatchInfoService;
import com.ruoyi.product.mapper.BatchInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 批次信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-09-01
 */
@Service
public class BatchInfoServiceImpl implements IBatchInfoService
{
    @Autowired
    private BatchInfoMapper batchInfoMapper;

    /**
     * 查询批次信息
     * 
     * @param id 批次信息主键
     * @return 批次信息
     */
    @Override
    public BatchInfo selectBatchInfoById(Long id)
    {
        return batchInfoMapper.selectBatchInfoById(id);
    }

    /**
     * 查询批次信息列表
     * 
     * @param batchInfo 批次信息
     * @return 批次信息
     */
    @Override
    public List<BatchInfo> selectBatchInfoList(BatchInfo batchInfo)
    {
        return batchInfoMapper.selectBatchInfoList(batchInfo);
    }


    @Override public List<BatchInfo> selectProductBatchInfoList(Long ownerId)
    {
        BatchInfo batchInfo = new BatchInfo();
        batchInfo.setOwnerId(ownerId);
        return batchInfoMapper.selectBatchInfoList(batchInfo);
    }

    /**
     * 新增批次信息
     * 
     * @param batchInfo 批次信息
     * @return 结果
     */
    @Override
    public int insertBatchInfo(BatchInfo batchInfo)
    {
        batchInfo.setCreateTime(DateUtils.getNowDate());
        return batchInfoMapper.insertBatchInfo(batchInfo);
    }

    /**
     * 修改批次信息
     * 
     * @param batchInfo 批次信息
     * @return 结果
     */
    @Override
    public int updateBatchInfo(BatchInfo batchInfo)
    {
        batchInfo.setUpdateTime(DateUtils.getNowDate());
        return batchInfoMapper.updateBatchInfo(batchInfo);
    }

    /**
     * 批量删除批次信息
     * 
     * @param ids 需要删除的批次信息主键
     * @return 结果
     */
    @Override
    public int deleteBatchInfoByIds(String ids)
    {
        return batchInfoMapper.deleteBatchInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除批次信息信息
     * 
     * @param id 批次信息主键
     * @return 结果
     */
    @Override
    public int deleteBatchInfoById(Long id)
    {
        return batchInfoMapper.deleteBatchInfoById(id);
    }
}
