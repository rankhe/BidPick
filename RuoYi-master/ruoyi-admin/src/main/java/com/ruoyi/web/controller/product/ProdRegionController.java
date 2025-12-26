package com.ruoyi.web.controller.product;

import java.util.List;
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
import com.ruoyi.product.domain.ProdRegion;
import com.ruoyi.product.service.IProdRegionService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 地区区域Controller
 * 
 * @author ruoyi
 * @date 2024-07-29
 */
@Controller
@RequestMapping("/product/region")
public class ProdRegionController extends BaseController
{
    private String prefix = "product/region";

    @Autowired
    private IProdRegionService prodRegionService;

    @RequiresPermissions("product:region:view")
    @GetMapping()
    public String region()
    {
        return prefix + "/region";
    }

    /**
     * 查询地区区域列表
     */
    @RequiresPermissions("product:region:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ProdRegion prodRegion)
    {
        startPage();
        List<ProdRegion> list = prodRegionService.selectProdRegionList(prodRegion);
        return getDataTable(list);
    }

    /**
     * 导出地区区域列表
     */
    @RequiresPermissions("product:region:export")
    @Log(title = "地区区域", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ProdRegion prodRegion)
    {
        List<ProdRegion> list = prodRegionService.selectProdRegionList(prodRegion);
        ExcelUtil<ProdRegion> util = new ExcelUtil<ProdRegion>(ProdRegion.class);
        return util.exportExcel(list, "地区区域数据");
    }

    /**
     * 新增地区区域
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存地区区域
     */
    @RequiresPermissions("product:region:add")
    @Log(title = "地区区域", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ProdRegion prodRegion)
    {
        return toAjax(prodRegionService.insertProdRegion(prodRegion));
    }

    /**
     * 修改地区区域
     */
    @RequiresPermissions("product:region:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        ProdRegion prodRegion = prodRegionService.selectProdRegionById(id);
        mmap.put("prodRegion", prodRegion);
        return prefix + "/edit";
    }

    /**
     * 修改保存地区区域
     */
    @RequiresPermissions("product:region:edit")
    @Log(title = "地区区域", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ProdRegion prodRegion)
    {
        return toAjax(prodRegionService.updateProdRegion(prodRegion));
    }

    /**
     * 删除地区区域
     */
    @RequiresPermissions("product:region:remove")
    @Log(title = "地区区域", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(prodRegionService.deleteProdRegionByIds(ids));
    }
}
