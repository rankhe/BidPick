package com.ruoyi.product.service;

import com.ruoyi.product.domain.ProdCategory;

import java.util.List;

/**
 * 产品分类Service接口
 * 
 * @author ruoyi
 * @date 2023-09-01
 */
public interface IProdCategoryService 
{
    /**
     * 查询产品分类
     * 
     * @param id 产品分类主键
     * @return 产品分类
     */
    public ProdCategory selectProdCategoryById(Long id);

    /**
     * 查询产品分类列表
     * 
     * @param prodCategory 产品分类
     * @return 产品分类集合
     */
    public List<ProdCategory> selectProdCategoryList(ProdCategory prodCategory);

    /**
     * 新增产品分类
     * 
     * @param prodCategory 产品分类
     * @return 结果
     */
    public int insertProdCategory(ProdCategory prodCategory);

    /**
     * 修改产品分类
     * 
     * @param prodCategory 产品分类
     * @return 结果
     */
    public int updateProdCategory(ProdCategory prodCategory);

    /**
     * 批量删除产品分类
     * 
     * @param ids 需要删除的产品分类主键集合
     * @return 结果
     */
    public int deleteProdCategoryByIds(String ids);

    /**
     * 删除产品分类信息
     * 
     * @param id 产品分类主键
     * @return 结果
     */
    public int deleteProdCategoryById(Long id);

    List<ProdCategory> selectCategoryAll();
}
