package com.ruoyi.product.service;

import com.ruoyi.product.domain.ProdCategory;

import java.util.List;

public interface IProductCategoryService
{
    /**
     *
     * @return
     */
    List<ProdCategory> selectProductCategoryList(Integer pageNum,Integer pageSize);

    /**
     *
     * @param productCategory
     * @return
     */
    Long createProductCategory(ProdCategory productCategory);

}
