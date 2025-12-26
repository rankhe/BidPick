package com.ruoyi.web.controller.product;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.enums.CommonStatus;
import com.ruoyi.product.domain.BatchInfo;
import com.ruoyi.product.service.IBatchInfoService;
import com.ruoyi.product.domain.ProdInfo;
import com.ruoyi.product.service.IProdInfoService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.product.service.IQuotationBlackUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
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
 * 批次信息Controller
 * 
 * @author ruoyi
 * @date 2023-09-01
 */
@Controller
@RequestMapping("/product/batch")
public class BatchInfoController extends BaseController
{
    private String prefix = "product/batch";

    @Autowired
    private IBatchInfoService batchInfoService;

    @Autowired
    private IProdInfoService prodInfoService;
    @Autowired
    private com.ruoyi.product.service.IBatchProductService batchProductService;

    @Autowired
    private IQuotationBlackUserService quotationBlackUserService;

    @RequiresPermissions("product:batch:view")
    @GetMapping()
    public String info()
    {
        return prefix + "/batch";
    }

    @GetMapping("/quotes/{id}")
    @org.apache.shiro.authz.annotation.RequiresPermissions("product:history:list")
    public String quotes(@PathVariable("id") Long id, ModelMap mmap)
    {
        BatchInfo batchInfo = batchInfoService.selectBatchInfoById(id);
        mmap.put("batchInfo", batchInfo);
        mmap.put("batchId", id);
        return prefix + "/quotes";
    }
    /**
     * 查询批次信息列表
     */
    @RequiresPermissions("product:batch:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BatchInfo batchInfo)
    {
        startPage();
        batchInfo.setStatus(CommonStatus.OK.getCode());
        if (batchInfo.getOwnerId() == null && !getSysUser().isAdmin())
        {
            batchInfo.setOwnerId(getUserId());
        }
        List<BatchInfo> list = batchInfoService.selectBatchInfoList(batchInfo);
        return getDataTable(list);
    }

    /**
     * 查询指定发布者发布的批次
     */
//    @RequiresPermissions("product:batch:list")
    @PostMapping("/list/{publish}")
    @ResponseBody
    public List<BatchInfo> list(@PathVariable(value = "publish") Long publish)
    {
        startPage();
        logger.info("publish[{}]",publish);
        if(!quotationBlackUserService.checkQuotationBlackUser(getUserId(), publish))
        {
            return java.util.Collections.emptyList();
        }
        BatchInfo batchInfo = new  BatchInfo();
        batchInfo.setOwnerId(publish);
        List<BatchInfo> list = batchInfoService.selectBatchInfoList(batchInfo);
        if(!CollectionUtils.isEmpty(list))
        {
            list = list.stream().filter(i -> i.getEndTime().compareTo(LocalDateTime.now())>0)
                    .collect(Collectors.toList());
        }
        return list;
    }
    /**
     * 导出批次信息列表
     */
    @RequiresPermissions("product:batch:export")
    @Log(title = "批次信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BatchInfo batchInfo)
    {
        List<BatchInfo> list = batchInfoService.selectBatchInfoList(batchInfo);
        ExcelUtil<BatchInfo> util = new ExcelUtil<BatchInfo>(BatchInfo.class);
        return util.exportExcel(list, "批次信息数据");
    }

    /**
     * 新增批次信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存批次信息
     */
    @RequiresPermissions("product:batch:add")
    @Log(title = "批次信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BatchInfo batchInfo)
    {
        batchInfo.setOwnerId(getUserId());
        batchInfo.setQuotation(0l);
        batchInfo.setCreateBy(getLoginName());
        batchInfo.setCreateTime(Calendar.getInstance().getTime());
        return toAjax(batchInfoService.insertBatchInfo(batchInfo));
    }

    @PostMapping("/publish")
    @ResponseBody
    public AjaxResult publish(BatchInfo batchInfo, String items)
    {
        batchInfo.setOwnerId(getUserId());
        batchInfo.setQuotation(0l);
        batchInfo.setCreateBy(getLoginName());
        batchInfo.setCreateTime(Calendar.getInstance().getTime());
        int r = batchInfoService.insertBatchInfo(batchInfo);
        if (r <= 0) return error("批次创建失败");
        Long batchId = batchInfo.getId();
        if (items != null && items.length() > 0)
        {
            for (Object o : JSON.parseArray(items))
            {
                JSONObject jo = (JSONObject) o;
                Long prodTemplateId = jo.getLong("prodTemplateId");
                Integer cnt = jo.getInteger("count");
                if (prodTemplateId == null || cnt == null) continue;
                ProdInfo template = prodInfoService.selectProdInfoById(prodTemplateId);
                if (template == null) continue;
                com.ruoyi.product.domain.BatchProduct bp = new com.ruoyi.product.domain.BatchProduct();
                bp.setBatchId(batchId);
                bp.setProdId(prodTemplateId);
                bp.setPublishCount(cnt);
                bp.setLockedCount(0);
                bp.setStatus(CommonStatus.OK.getCode().intValue());
                batchProductService.insertBatchProduct(bp);
            }
        }
        return success();
    }

    @PostMapping("/{id}/items")
    @ResponseBody
    public AjaxResult appendItems(@PathVariable("id") Long id, String items)
    {
        BatchInfo bi = batchInfoService.selectBatchInfoById(id);
        if (bi == null) return error("批次不存在");
        if (items != null && items.length() > 0)
        {
            for (Object o : JSON.parseArray(items))
            {
                JSONObject jo = (JSONObject) o;
                Long prodTemplateId = jo.getLong("prodTemplateId");
                Integer cnt = jo.getInteger("count");
                if (prodTemplateId == null || cnt == null) continue;
                ProdInfo template = prodInfoService.selectProdInfoById(prodTemplateId);
                if (template == null) continue;
                com.ruoyi.product.domain.BatchProduct bp = new com.ruoyi.product.domain.BatchProduct();
                bp.setBatchId(bi.getId());
                bp.setProdId(prodTemplateId);
                bp.setPublishCount(cnt);
                bp.setLockedCount(0);
                bp.setStatus(CommonStatus.OK.getCode().intValue());
                batchProductService.insertBatchProduct(bp);
            }
        }
        return success();
    }

    /**
     * 修改批次信息
     */
    @RequiresPermissions("product:batch:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        BatchInfo batchInfo = batchInfoService.selectBatchInfoById(id);
        mmap.put("batchInfo", batchInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存批次信息
     */
    @RequiresPermissions("product:batch:edit")
    @Log(title = "批次信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BatchInfo batchInfo)
    {
        batchInfo.setUpdateBy(getLoginName());
        return toAjax(batchInfoService.updateBatchInfo(batchInfo));
    }

    /**
     * 删除批次信息
     */
    @RequiresPermissions("product:batch:remove")
    @Log(title = "批次信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(batchInfoService.deleteBatchInfoByIds(ids));
    }
}
