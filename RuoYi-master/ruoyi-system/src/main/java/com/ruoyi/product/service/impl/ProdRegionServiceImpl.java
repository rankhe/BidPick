package com.ruoyi.product.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.ProdRegionMapper;
import com.ruoyi.product.domain.ProdRegion;
import com.ruoyi.product.service.IProdRegionService;
import com.ruoyi.common.core.text.Convert;

/**
 * 地区区域Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-07-29
 */
@Service
public class ProdRegionServiceImpl implements IProdRegionService 
{
    @Autowired
    private ProdRegionMapper prodRegionMapper;

    /**
     * 查询地区区域
     * 
     * @param id 地区区域主键
     * @return 地区区域
     */
    @Override
    public ProdRegion selectProdRegionById(Long id)
    {
        return prodRegionMapper.selectProdRegionById(id);
    }

    /**
     * 查询地区区域列表
     * 
     * @param prodRegion 地区区域
     * @return 地区区域
     */
    @Override
    public List<ProdRegion> selectProdRegionList(ProdRegion prodRegion)
    {
        return prodRegionMapper.selectProdRegionList(prodRegion);
    }

    /**
     * 新增地区区域
     * 
     * @param prodRegion 地区区域
     * @return 结果
     */
    @Override
    public int insertProdRegion(ProdRegion prodRegion)
    {
        prodRegion.setCreateTime(DateUtils.getNowDate());
        return prodRegionMapper.insertProdRegion(prodRegion);
    }

    /**
     * 修改地区区域
     * 
     * @param prodRegion 地区区域
     * @return 结果
     */
    @Override
    public int updateProdRegion(ProdRegion prodRegion)
    {
        prodRegion.setUpdateTime(DateUtils.getNowDate());
        return prodRegionMapper.updateProdRegion(prodRegion);
    }

    /**
     * 批量删除地区区域
     * 
     * @param ids 需要删除的地区区域主键
     * @return 结果
     */
    @Override
    public int deleteProdRegionByIds(String ids)
    {
        return prodRegionMapper.deleteProdRegionByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除地区区域信息
     * 
     * @param id 地区区域主键
     * @return 结果
     */
    @Override
    public int deleteProdRegionById(Long id)
    {
        return prodRegionMapper.deleteProdRegionById(id);
    }
}
