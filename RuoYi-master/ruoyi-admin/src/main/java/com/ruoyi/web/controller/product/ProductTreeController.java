package com.ruoyi.web.controller.product;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.product.domain.ProdBrand;
import com.ruoyi.product.domain.ProdCategory;
import com.ruoyi.product.domain.ProdInfo;
import com.ruoyi.product.service.IProdBrandService;
import com.ruoyi.product.service.IProdCategoryService;
import com.ruoyi.product.service.IProdInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/product/tree")
public class ProductTreeController extends BaseController
{
    @Autowired private IProdBrandService brandService;
    @Autowired private IProdCategoryService categoryService;
    @Autowired private IProdInfoService infoService;

    @GetMapping("/brand")
    public List<Map<String,Object>> brands()
    {
        ProdBrand cond = new ProdBrand();
        cond.setDeptId(getSysUser().getDeptId());
        List<ProdBrand> list = brandService.selectProdBrandList(cond);
        List<Map<String,Object>> nodes = new ArrayList<>();
        for (ProdBrand b : list)
        {
            Map<String,Object> n = new HashMap<>();
            n.put("id", b.getId());
            n.put("name", b.getName());
            n.put("type", "brand");
            nodes.add(n);
        }
        return nodes;
    }

    @GetMapping("/zTree")
    public List<Map<String,Object>> zTree()
    {
        List<Map<String,Object>> nodes = new ArrayList<>();
        ProdBrand bCond = new ProdBrand();
        bCond.setDeptId(getSysUser().getDeptId());
        List<ProdBrand> brands = brandService.selectProdBrandList(bCond);
        for (ProdBrand b : brands)
        {
            Map<String,Object> bn = new HashMap<>();
            bn.put("id", 100000 + (b.getId()==null?0:b.getId()));
            bn.put("pId", 0);
            bn.put("name", b.getName());
            bn.put("type", "brand");
            bn.put("realId", b.getId());
            nodes.add(bn);
            ProdCategory cCond = new ProdCategory();
            cCond.setBrandId(b.getId());
            cCond.setStatus(1L);
            cCond.setDeptId(getSysUser().getDeptId());
            List<ProdCategory> cats = categoryService.selectProdCategoryList(cCond);
            Map<Long,Long> idMap = new HashMap<>();
            for (ProdCategory c : cats)
            {
                long nid = 200000 + (c.getId()==null?0:c.getId());
                idMap.put(c.getId(), nid);
            }
            for (ProdCategory c : cats)
            {
                Map<String,Object> cn = new HashMap<>();
                long nid = idMap.get(c.getId());
                long pid;
                if (c.getParentId()!=null && c.getParentId()!=0)
                    pid = idMap.getOrDefault(c.getParentId(), 100000 + b.getId());
                else
                    pid = 100000 + b.getId();
                cn.put("id", nid);
                cn.put("pId", pid);
                cn.put("name", c.getName());
                cn.put("type", "category");
                cn.put("realId", c.getId());
                cn.put("brandId", b.getId());
                nodes.add(cn);
            }
        }
        return nodes;
    }
    @GetMapping("/category/{brandId}")
    public List<ProdCategory> categories(@PathVariable("brandId") Long brandId)
    {
        ProdCategory cond = new ProdCategory();
        cond.setBrandId(brandId);
        cond.setStatus(1L);
        cond.setDeptId(getSysUser().getDeptId());
        return categoryService.selectProdCategoryList(cond);
    }

    @GetMapping("/product/{categoryId}")
    public List<ProdInfo> products(@PathVariable("categoryId") Long categoryId)
    {
        ProdInfo cond = new ProdInfo();
        cond.setCategoryId(categoryId);
        cond.setStatus(1L);
        cond.setDeptId(getSysUser().getDeptId());
        return infoService.selectProdInfoList(cond);
    }
}
