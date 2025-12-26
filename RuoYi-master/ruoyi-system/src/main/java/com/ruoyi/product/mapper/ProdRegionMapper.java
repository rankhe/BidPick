package com.ruoyi.product.mapper;

import com.ruoyi.product.domain.ProdRegion;

import java.util.List;

/**
 * 地区区域Mapper接口
 * 
 * @author ruoyi
 * @date 2024-07-29
 */
public interface ProdRegionMapper 
{
    /**
     * 查询地区区域
     * 
     * @param id 地区区域主键
     * @return 地区区域
     */
    public ProdRegion selectProdRegionById(Long id);

    /**
     * 查询地区区域列表
     * 
     * @param prodRegion 地区区域
     * @return 地区区域集合
     */
    public List<ProdRegion> selectProdRegionList(ProdRegion prodRegion);

    /**
     * 新增地区区域
     * 
     * @param prodRegion 地区区域
     * @return 结果
     */
    public int insertProdRegion(ProdRegion prodRegion);

    /**
     * 修改地区区域
     * 
     * @param prodRegion 地区区域
     * @return 结果
     */
    public int updateProdRegion(ProdRegion prodRegion);

    /**
     * 删除地区区域
     * 
     * @param id 地区区域主键
     * @return 结果
     */
    public int deleteProdRegionById(Long id);

    /**
     * 批量删除地区区域
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProdRegionByIds(String[] ids);
}
