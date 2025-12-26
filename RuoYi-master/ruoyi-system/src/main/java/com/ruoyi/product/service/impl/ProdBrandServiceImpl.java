package com.ruoyi.product.service.impl;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.product.domain.ProdBrand;
import com.ruoyi.product.service.IProdBrandService;
import com.ruoyi.product.mapper.ProdBrandMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 产品品牌Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-09-08
 */
@Service
public class ProdBrandServiceImpl implements IProdBrandService
{
    @Autowired
    private ProdBrandMapper prodBrandMapper;

    /**
     * 查询产品品牌
     * 
     * @param id 产品品牌主键
     * @return 产品品牌
     */
    @Override
    public ProdBrand selectProdBrandById(Long id)
    {
        return prodBrandMapper.selectProdBrandById(id);
    }

    /**
     * 查询产品品牌列表
     * 
     * @param prodBrand 产品品牌
     * @return 产品品牌
     */
    @Override
    public List<ProdBrand> selectProdBrandList(ProdBrand prodBrand)
    {
        return prodBrandMapper.selectProdBrandList(prodBrand);
    }

    /**
     * 新增产品品牌
     * 
     * @param prodBrand 产品品牌
     * @return 结果
     */
    @Override
    public int insertProdBrand(ProdBrand prodBrand)
    {
        prodBrand.setCreateTime(DateUtils.getNowDate());
        return prodBrandMapper.insertProdBrand(prodBrand);
    }

    /**
     * 修改产品品牌
     * 
     * @param prodBrand 产品品牌
     * @return 结果
     */
    @Override
    public int updateProdBrand(ProdBrand prodBrand)
    {
        prodBrand.setUpdateTime(DateUtils.getNowDate());
        return prodBrandMapper.updateProdBrand(prodBrand);
    }

    /**
     * 批量删除产品品牌
     * 
     * @param ids 需要删除的产品品牌主键
     * @return 结果
     */
    @Override
    public int deleteProdBrandByIds(String ids)
    {
        return prodBrandMapper.deleteProdBrandByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除产品品牌信息
     * 
     * @param id 产品品牌主键
     * @return 结果
     */
    @Override
    public int deleteProdBrandById(Long id)
    {
        return prodBrandMapper.deleteProdBrandById(id);
    }
}
