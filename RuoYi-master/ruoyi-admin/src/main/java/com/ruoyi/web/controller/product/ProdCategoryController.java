package com.ruoyi.web.controller.product;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.enums.CommonStatus;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.product.domain.ProdCategory;
import com.ruoyi.product.service.IProdCategoryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 产品分类Controller
 * 
 * @author ruoyi
 * @date 2023-09-01
 */
@Controller
@RequestMapping("/product/category")
public class ProdCategoryController extends BaseController
{
    private String prefix = "product/category";

    @Autowired
    private IProdCategoryService prodCategoryService;
    @Autowired
    private com.ruoyi.product.service.IProdBrandService prodBrandService;

    @RequiresPermissions("product:category:view")
    @GetMapping()
    public String category()
    {
        return prefix + "/category";
    }

    /**
     * 查询产品分类列表
     */
    @RequiresPermissions("product:category:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ProdCategory prodCategory)
    {
        startPage();
        prodCategory.setDeptId(getSysUser().getDeptId());
        if(prodCategory.getStatus() == null)
        {
            prodCategory.setStatus(CommonStatus.OK.getCode());
        }
        List<ProdCategory> list = prodCategoryService.selectProdCategoryList(prodCategory);
        return getDataTable(list);
    }

//    /**
//     * 查询产品分类列表
//     */
//    @RequiresPermissions("product:category:list")
//    @PostMapping("/listFirstLevl")
//    @ResponseBody
//    public TableDataInfo listFirstLevel(ProdCategory prodCategory)
//    {
//        startPage();
//        if(prodCategory.getStatus() == null)
//        {
//            prodCategory.setStatus(CommonStatus.OK.getCode());
//        }
//        prodCategory.setLevel(1);
//        List<ProdCategory> list = prodCategoryService.selectProdCategoryList(prodCategory);
//        return getDataTable(list);
//    }

    /**
     * 导出产品分类列表
     */
    @RequiresPermissions("product:category:export")
    @Log(title = "产品分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ProdCategory prodCategory)
    {
        prodCategory.setDeptId(getSysUser().getDeptId());
        List<ProdCategory> list = prodCategoryService.selectProdCategoryList(prodCategory);
        ExcelUtil<ProdCategory> util = new ExcelUtil<ProdCategory>(ProdCategory.class);
        return util.exportExcel(list, "产品分类数据");
    }

    /**
     * 新增产品分类
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        com.ruoyi.product.domain.ProdBrand condition = new com.ruoyi.product.domain.ProdBrand();
        condition.setDeptId(getSysUser().getDeptId());
        mmap.put("brands", prodBrandService.selectProdBrandList(condition));
        return prefix + "/add";
    }

    /**
     * 新增保存产品分类
     */
    @RequiresPermissions("product:category:add")
    @Log(title = "产品分类", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ProdCategory prodCategory)
    {
        prodCategory.setCreator(getLoginName());
        if(prodCategory.getLevel() == 1)
        {
            prodCategory.setParentId(0l);
        }
        prodCategory.setDeptId(getSysUser().getDeptId());
        com.ruoyi.common.core.domain.entity.SysUser sysUser = getSysUser();
        if (sysUser != null && sysUser.getDeptId() != null)
        {
            prodCategory.setDeptId(sysUser.getDeptId());
        }
        prodCategory.setCreateTime(Calendar.getInstance().getTime());
        return toAjax(prodCategoryService.insertProdCategory(prodCategory));
    }

    /**
     * 修改产品分类
     */
    @RequiresPermissions("product:category:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        ProdCategory prodCategory = prodCategoryService.selectProdCategoryById(id);
        ProdCategory condition = new ProdCategory();
        condition.setStatus(CommonStatus.OK.getCode());
        condition.setLevel(1);
        List<ProdCategory> prodCategories = prodCategoryService.selectProdCategoryList(condition);
        com.ruoyi.product.domain.ProdBrand brandCond = new com.ruoyi.product.domain.ProdBrand();
        brandCond.setDeptId(getSysUser().getDeptId());
        mmap.put("brands", prodBrandService.selectProdBrandList(brandCond));
        mmap.put("firstLevels",prodCategories);
        mmap.put("prodCategory", prodCategory);
        return prefix + "/edit";
    }

    /**
     * 修改保存产品分类
     */
    @RequiresPermissions("product:category:edit")
    @Log(title = "产品分类", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ProdCategory prodCategory)
    {
        prodCategory.setUpdator(getLoginName());
        if(prodCategory.getLevel() == 1)
        {
            prodCategory.setParentId(0l);
        }
        prodCategory.setUpdateTime(Calendar.getInstance().getTime());
        return toAjax(prodCategoryService.updateProdCategory(prodCategory));
    }

    /**
     * 删除产品分类
     */
    @RequiresPermissions("product:category:remove")
    @Log(title = "产品分类", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        String[] strings = Convert.toStrArray(ids);
        AtomicInteger integer = new AtomicInteger(0);
        Arrays.stream(strings).forEach(i -> {
            ProdCategory category = new ProdCategory();
            category.setId(Long.getLong(i));
            category.setStatus(-1l);
            prodCategoryService.updateProdCategory(category);
            integer.intValue();
        });
        return toAjax(integer.get());
    }
}
