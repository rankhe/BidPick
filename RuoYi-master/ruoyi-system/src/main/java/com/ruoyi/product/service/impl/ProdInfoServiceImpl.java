package com.ruoyi.product.service.impl;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.product.domain.ProdInfo;
import com.ruoyi.product.service.IProdInfoService;
import com.ruoyi.product.mapper.ProdInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 产品信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-09-01
 */
@Service
public class ProdInfoServiceImpl implements IProdInfoService
{
    @Autowired
    private ProdInfoMapper prodInfoMapper;

    /**
     * 查询产品信息
     * 
     * @param id 产品信息主键
     * @return 产品信息
     */
    @Override
    public ProdInfo selectProdInfoById(Long id)
    {
        return prodInfoMapper.selectProdInfoById(id);
    }

    /**
     * 查询产品信息列表
     * 
     * @param prodInfo 产品信息
     * @return 产品信息
     */
    @Override
    public List<ProdInfo> selectProdInfoList(ProdInfo prodInfo)
    {
        return prodInfoMapper.selectProdInfoList(prodInfo);
    }

    /**
     * 新增产品信息
     * 
     * @param prodInfo 产品信息
     * @return 结果
     */
    @Override
    public int insertProdInfo(ProdInfo prodInfo)
    {
        prodInfo.setCreateTime(DateUtils.getNowDate());
        return prodInfoMapper.insertProdInfo(prodInfo);
    }

    /**
     * 修改产品信息
     * 
     * @param prodInfo 产品信息
     * @return 结果
     */
    @Override
    public int updateProdInfo(ProdInfo prodInfo)
    {
        prodInfo.setUpdateTime(DateUtils.getNowDate());
        return prodInfoMapper.updateProdInfo(prodInfo);
    }

    /**
     * 批量删除产品信息
     * 
     * @param ids 需要删除的产品信息主键
     * @return 结果
     */
    @Override
    public int deleteProdInfoByIds(String ids)
    {
        return prodInfoMapper.deleteProdInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除产品信息信息
     * 
     * @param id 产品信息主键
     * @return 结果
     */
    @Override
    public int deleteProdInfoById(Long id)
    {
        return prodInfoMapper.deleteProdInfoById(id);
    }

    @Override
    public Integer importProduct(List<ProdInfo> prodInfoList, boolean updateSupport, String operName)
    {
        AtomicInteger count = new AtomicInteger(0);
        if(!CollectionUtils.isEmpty(prodInfoList))
        {
            prodInfoList.forEach(
                    p->{
                        if(updateSupport)
                        {
                            ProdInfo condition = new ProdInfo();
//                            condition.setBatchId(p.getBatchId());
                            condition.setName(p.getName());
                            condition.setCategoryId(p.getCategoryId());
                            condition.setDeptId(p.getDeptId());
                            List<ProdInfo> prodInfoList1 = prodInfoMapper.selectProdInfoList(condition);
                            if(!CollectionUtils.isEmpty(prodInfoList1))
                            {
                                ProdInfo originProd = prodInfoList1.get(0);
                                BeanUtils.copyProperties(p,originProd);
                                originProd.setUpdateBy(operName);
                                originProd.setUpdateTime(new Date());
                                prodInfoMapper.updateProdInfo(originProd);
                            }
                        }
                        else
                        {
                            p.setCreateTime(Calendar.getInstance().getTime());
                            p.setCreateBy(operName);
//                            p.setUpdateBy(operName);
//                            p.setUpdateTime();
                            prodInfoMapper.insertProdInfo(p);
                        }
                        count.intValue();
                    }
            );
        }
        return count.get();
    }
}
