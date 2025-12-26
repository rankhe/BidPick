package com.ruoyi.product.mapper;

import com.ruoyi.product.domain.ProdCategory;
import org.apache.ibatis.annotations.Options;

import java.util.List;

public interface ProductCategoryMapper
{

    List<ProdCategory> selectProductCategoryList(Integer pageNum, Integer pageSize);

    @Options(useGeneratedKeys = true, keyProperty = "id")
//    @Insert("INSERT INTO product_category(name, index,status) VALUES(#{name}, #{index},#{status})")
    long insertProductCategory(ProdCategory productCategory);

    int updateProductCategory(ProdCategory productCategory);
}
