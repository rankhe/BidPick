package com.ruoyi.web.controller.product;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.enums.CommonStatus;
import com.ruoyi.product.domain.BatchInfo;
import com.ruoyi.product.domain.BatchProduct;
import com.ruoyi.product.domain.ProdInfo;
import com.ruoyi.product.service.IBatchInfoService;
import com.ruoyi.product.service.IBatchProductService;
import com.ruoyi.product.service.IProdInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.web.controller.product.vo.BatchProductExportVO;

@Controller
@RequestMapping("/product/batch/items")
public class BatchProductController extends BaseController {
    private final String prefix = "product/batch";
    @Autowired private IBatchProductService batchProductService;
    @Autowired private IBatchInfoService batchInfoService;
    @Autowired private IProdInfoService prodInfoService;

    @RequiresPermissions("product:batch:items:view")
    @GetMapping("/{batchId}")
    public String items(@PathVariable("batchId") Long batchId, ModelMap mmap) {
        BatchInfo bi = batchInfoService.selectBatchInfoById(batchId);
        if (bi == null) {
            mmap.put("error", "批次不存在");
        } else {
            mmap.put("batch", bi);
            mmap.put("batchId", batchId);
        }
        return prefix + "/items";
    }

    @RequiresPermissions("product:batch:items:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BatchProduct batchProduct) {
        startPage();
        List<BatchProduct> items = batchProductService.selectBatchProductList(batchProduct);
        List<Map<String, Object>> rows = new ArrayList<>();
        if (!CollectionUtils.isEmpty(items)) {
            rows = items.stream().map(bp -> {
                ProdInfo p = prodInfoService.selectProdInfoById(bp.getProdId());
                Map<String, Object> m = new HashMap<>();
                m.put("id", bp.getId());
                m.put("batchId", bp.getBatchId());
                m.put("prodId", bp.getProdId());
                m.put("name", p != null ? p.getName() : "");
                m.put("brandName", p != null ? p.getBrandName() : "");
                m.put("categoryName", p != null ? p.getCategoryName() : "");
                m.put("publishCount", bp.getPublishCount());
                m.put("lockedCount", bp.getLockedCount());
                m.put("status", bp.getStatus());
                return m;
            }).collect(Collectors.toList());
        }
        return getDataTable(rows);
    }

    @RequiresPermissions("product:batch:items:export")
    @Log(title = "批次商品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(@RequestParam("batchId") Long batchId) {
        BatchProduct cond = new BatchProduct();
        cond.setBatchId(batchId);
        List<BatchProduct> items = batchProductService.selectBatchProductList(cond);
        List<BatchProductExportVO> exportRows = new ArrayList<>();
        if (!CollectionUtils.isEmpty(items)) {
            exportRows = items.stream().map(bp -> {
                ProdInfo p = prodInfoService.selectProdInfoById(bp.getProdId());
                BatchProductExportVO vo = new BatchProductExportVO();
                vo.setName(p != null ? p.getName() : "");
                vo.setBrandName(p != null ? p.getBrandName() : "");
                vo.setCategoryName(p != null ? p.getCategoryName() : "");
                vo.setPublishCount(bp.getPublishCount());
                vo.setLockedCount(bp.getLockedCount());
                String statusLabel;
                if (bp.getStatus() != null && Objects.equals(bp.getStatus(), CommonStatus.OK.getCode().intValue())) {
                    statusLabel = "正常";
                } else {
                    statusLabel = "停用";
                }
                vo.setStatusLabel(statusLabel);
                return vo;
            }).collect(Collectors.toList());
        }
        ExcelUtil<BatchProductExportVO> util = new ExcelUtil<>(BatchProductExportVO.class);
        return util.exportExcel(exportRows, "批次商品数据");
    }

    @RequiresPermissions("product:batch:items:add")
    @Log(title = "批次商品", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addItems(@RequestParam("batchId") Long batchId,
                               @RequestParam("items") String items) {
        BatchInfo bi = batchInfoService.selectBatchInfoById(batchId);
        if (bi == null) return error("批次不存在");
        if (items != null && items.length() > 0) {
            for (Object o : JSON.parseArray(items)) {
                JSONObject jo = (JSONObject) o;
                Long prodTemplateId = jo.getLong("prodTemplateId");
                Integer cnt = jo.getInteger("count");
                if (prodTemplateId == null || cnt == null || cnt < 0) continue;
                ProdInfo template = prodInfoService.selectProdInfoById(prodTemplateId);
                if (template == null) continue;
                BatchProduct exist = batchProductService.selectByBatchAndProd(batchId, prodTemplateId);
                if (exist != null) {
                    int newCount = (exist.getPublishCount() == null ? 0 : exist.getPublishCount()) + cnt;
                    exist.setPublishCount(newCount);
                    batchProductService.updateBatchProduct(exist);
                } else {
                    BatchProduct bp = new BatchProduct();
                    bp.setBatchId(batchId);
                    bp.setProdId(prodTemplateId);
                    bp.setPublishCount(cnt);
                    bp.setLockedCount(0);
                    bp.setStatus(CommonStatus.OK.getCode().intValue());
                    batchProductService.insertBatchProduct(bp);
                }
            }
        }
        return success();
    }

    @RequiresPermissions("product:batch:items:edit")
    @Log(title = "批次商品", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    @ResponseBody
    public AjaxResult updateItem(@RequestParam("id") Long id,
                                 @RequestParam("publishCount") Integer publishCount) {
        BatchProduct bp = batchProductService.selectBatchProductById(id);
        if (bp == null) return error("条目不存在");
        if (publishCount == null || publishCount < 0) return error("数量非法");
        if (bp.getLockedCount() != null && publishCount < bp.getLockedCount()) {
            return error("发布数量不得小于已锁定数量");
        }
        bp.setPublishCount(publishCount);
        return toAjax(batchProductService.updateBatchProduct(bp));
    }

    @RequiresPermissions("product:batch:items:remove")
    @Log(title = "批次商品", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(@RequestParam("ids") String ids) {
        String[] arr = ids.split(",");
        for (String s : arr) {
            Long id = Long.valueOf(s);
            BatchProduct bp = batchProductService.selectBatchProductById(id);
            if (bp == null) continue;
            if (bp.getLockedCount() != null && bp.getLockedCount() > 0) {
                return error("存在已锁定数量的条目，不能删除");
            }
        }
        int count = 0;
        for (String s : arr) {
            Long id = Long.valueOf(s);
            count += batchProductService.deleteBatchProductById(id);
        }
        return toAjax(count);
    }
}
