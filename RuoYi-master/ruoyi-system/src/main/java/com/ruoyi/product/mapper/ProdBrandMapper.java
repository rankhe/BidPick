package com.ruoyi.product.mapper;

import com.ruoyi.product.domain.ProdBrand;

import java.util.List;

/**
 * 产品品牌Mapper接口
 * 
 * @author ruoyi
 * @date 2023-09-08
 */
public interface ProdBrandMapper 
{
    /**
     * 查询产品品牌
     * 
     * @param id 产品品牌主键
     * @return 产品品牌
     */
    public ProdBrand selectProdBrandById(Long id);

    /**
     * 查询产品品牌列表
     * 
     * @param prodBrand 产品品牌
     * @return 产品品牌集合
     */
    public List<ProdBrand> selectProdBrandList(ProdBrand prodBrand);

    /**
     * 新增产品品牌
     * 
     * @param prodBrand 产品品牌
     * @return 结果
     */
    public int insertProdBrand(ProdBrand prodBrand);

    /**
     * 修改产品品牌
     * 
     * @param prodBrand 产品品牌
     * @return 结果
     */
    public int updateProdBrand(ProdBrand prodBrand);

    /**
     * 删除产品品牌
     * 
     * @param id 产品品牌主键
     * @return 结果
     */
    public int deleteProdBrandById(Long id);

    /**
     * 批量删除产品品牌
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProdBrandByIds(String[] ids);
}
