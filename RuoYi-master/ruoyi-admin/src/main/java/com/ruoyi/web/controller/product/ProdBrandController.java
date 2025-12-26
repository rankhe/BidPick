package com.ruoyi.web.controller.product;

import java.util.Calendar;
import java.util.List;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.CommonStatus;
import com.ruoyi.product.domain.ProdBrand;
import com.ruoyi.product.service.IProdBrandService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 产品品牌Controller
 * 
 * @author ruoyi
 * @date 2023-09-08
 */
@Controller
@RequestMapping("/product/brand")
public class ProdBrandController extends BaseController
{
    private String prefix = "product/brand";

    @Autowired
    private IProdBrandService prodBrandService;

    @RequiresPermissions("product:brand:view")
    @GetMapping()
    public String brand()
    {
        return prefix + "/brand";
    }

    /**
     * 查询产品品牌列表
     */
    @RequiresPermissions("product:brand:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ProdBrand prodBrand)
    {
        startPage();
        SysUser sysUser = getSysUser();
        if(prodBrand.getDeptId() == null)
        {
            prodBrand.setDeptId(sysUser.getDeptId());
        }
        List<ProdBrand> list = prodBrandService.selectProdBrandList(prodBrand);
        return getDataTable(list);
    }

    /**
     * 导出产品品牌列表
     */
    @RequiresPermissions("product:brand:export")
    @Log(title = "产品品牌", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ProdBrand prodBrand)
    {
        SysUser sysUser = getSysUser();
        if(prodBrand.getDeptId() == null)
        {
            prodBrand.setDeptId(sysUser.getDeptId());
        }
        List<ProdBrand> list = prodBrandService.selectProdBrandList(prodBrand);
        ExcelUtil<ProdBrand> util = new ExcelUtil<ProdBrand>(ProdBrand.class);
        return util.exportExcel(list, "产品品牌数据");
    }

    /**
     * 新增产品品牌
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存产品品牌
     */
    @RequiresPermissions("product:brand:add")
    @Log(title = "产品品牌", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ProdBrand prodBrand)
    {
        SysUser sysUser = getSysUser();
        if(prodBrand.getDeptId() == null)
        {
            prodBrand.setDeptId(sysUser.getDeptId());
        }
        if (sysUser != null && sysUser.getDeptId() != null)
        {
            prodBrand.setDeptId(sysUser.getDeptId());
        }
        prodBrand.setCreateTime(Calendar.getInstance().getTime());
        prodBrand.setCreator(getLoginName());
        prodBrand.setStatus(CommonStatus.OK.getCode().intValue());
        return toAjax(prodBrandService.insertProdBrand(prodBrand));
    }

    /**
     * 修改产品品牌
     */
    @RequiresPermissions("product:brand:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        ProdBrand prodBrand = prodBrandService.selectProdBrandById(id);
        mmap.put("prodBrand", prodBrand);
        return prefix + "/edit";
    }

    /**
     * 修改保存产品品牌
     */
    @RequiresPermissions("product:brand:edit")
    @Log(title = "产品品牌", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ProdBrand prodBrand)
    {
        return toAjax(prodBrandService.updateProdBrand(prodBrand));
    }

    /**
     * 删除产品品牌
     */
    @RequiresPermissions("product:brand:remove")
    @Log(title = "产品品牌", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(prodBrandService.deleteProdBrandByIds(ids));
    }
}
