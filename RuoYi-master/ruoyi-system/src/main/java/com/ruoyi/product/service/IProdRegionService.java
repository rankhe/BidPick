package com.ruoyi.product.service;

import java.util.List;
import com.ruoyi.product.domain.ProdRegion;

/**
 * 地区区域Service接口
 * 
 * @author ruoyi
 * @date 2024-07-29
 */
public interface IProdRegionService 
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
     * 批量删除地区区域
     * 
     * @param ids 需要删除的地区区域主键集合
     * @return 结果
     */
    public int deleteProdRegionByIds(String ids);

    /**
     * 删除地区区域信息
     * 
     * @param id 地区区域主键
     * @return 结果
     */
    public int deleteProdRegionById(Long id);
}
