package com.ruoyi.product.service.impl;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.product.domain.ProdCategory;
import com.ruoyi.product.service.IProdCategoryService;
import com.ruoyi.product.mapper.ProdCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 产品分类Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-09-01
 */
@Service
public class ProdCategoryServiceImpl implements IProdCategoryService
{
    @Autowired
    private ProdCategoryMapper prodCategoryMapper;

    /**
     * 查询产品分类
     * 
     * @param id 产品分类主键
     * @return 产品分类
     */
    @Override
    public ProdCategory selectProdCategoryById(Long id)
    {
        return prodCategoryMapper.selectProdCategoryById(id);
    }

    /**
     * 查询产品分类列表
     * 
     * @param prodCategory 产品分类
     * @return 产品分类
     */
    @Override
    public List<ProdCategory> selectProdCategoryList(ProdCategory prodCategory)
    {
        return prodCategoryMapper.selectProdCategoryList(prodCategory);
    }

    /**
     * 新增产品分类
     * 
     * @param prodCategory 产品分类
     * @return 结果
     */
    @Override
    public int insertProdCategory(ProdCategory prodCategory)
    {
        prodCategory.setCreateTime(DateUtils.getNowDate());
        return prodCategoryMapper.insertProdCategory(prodCategory);
    }

    /**
     * 修改产品分类
     * 
     * @param prodCategory 产品分类
     * @return 结果
     */
    @Override
    public int updateProdCategory(ProdCategory prodCategory)
    {
        prodCategory.setUpdateTime(DateUtils.getNowDate());
        return prodCategoryMapper.updateProdCategory(prodCategory);
    }

    /**
     * 批量删除产品分类
     * 
     * @param ids 需要删除的产品分类主键
     * @return 结果
     */
    @Override
    public int deleteProdCategoryByIds(String ids)
    {
        return prodCategoryMapper.deleteProdCategoryByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除产品分类信息
     * 
     * @param id 产品分类主键
     * @return 结果
     */
    @Override
    public int deleteProdCategoryById(Long id)
    {
        return prodCategoryMapper.deleteProdCategoryById(id);
    }

    @Override public List<ProdCategory> selectCategoryAll()
    {
        return prodCategoryMapper.selectCategoryAll();
    }
}
