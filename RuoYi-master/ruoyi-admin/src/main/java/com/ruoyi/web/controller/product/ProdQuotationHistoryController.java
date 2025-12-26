package com.ruoyi.web.controller.product;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.CommonStatus;
import com.ruoyi.product.domain.BatchInfo;
import com.ruoyi.product.domain.ProdCategory;
import com.ruoyi.product.domain.ProdInfo;
import com.ruoyi.product.domain.ProdQuotationHistory;
import com.ruoyi.product.service.IProdCategoryService;
import com.ruoyi.product.service.IProdInfoService;
import com.ruoyi.product.service.IProdQuotationHistoryService;
import com.ruoyi.product.service.IQuotationBlackUserService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.web.controller.product.vo.MyProdQuotationExportVO;
import com.ruoyi.web.controller.product.vo.ProdQuotationHistoryVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 产品报价信息Controller
 * 
 * @author ruoyi
 * @date 2023-09-01
 */
@Controller
@RequestMapping("/product/history")
public class ProdQuotationHistoryController extends BaseController
{
    private String prefix = "product/history";

    @Autowired
    private IProdQuotationHistoryService prodQuotationHistoryService;

    @Autowired
    private IQuotationBlackUserService quotationBlackUserService;

    @Autowired
    private IProdInfoService prodInfoService;
    @Autowired
    private com.ruoyi.product.service.IBatchProductService batchProductService;

    @Autowired
    private IProdCategoryService prodCategoryService;

    @Autowired
    private ISysUserService sysUserService;

    @RequiresPermissions("product:history:view")
    @GetMapping()
    public String history()
    {
        return prefix + "/history";
    }

    /**
     * 查询产收到的报价
     */
    @RequiresPermissions("product:history:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ProdQuotationHistory prodQuotationHistory)
    {
        startPage();
        prodQuotationHistory.setDeptId(getSysUser().getDeptId());
        List<ProdQuotationHistory> list = prodQuotationHistoryService.selectProdQuotationHistoryList(prodQuotationHistory);
        List<ProdQuotationHistoryVO> result = list.stream().map(i -> {
            ProdQuotationHistoryVO vo = new ProdQuotationHistoryVO();
            BeanUtils.copyProperties(i, vo);
            ProdInfo prodInfo = prodInfoService.selectProdInfoById(i.getProdId());
            if(prodInfo != null)
            {
                final Long categoryId = prodInfo.getCategoryId();
                final ProdCategory prodCategory = prodCategoryService.selectProdCategoryById(categoryId);
                if(prodCategory != null)
                {
                    vo.setProdCategory(prodCategory.getName());
                }
            }
            return vo;
        }).collect(Collectors.toList());
        return getDataTable(result);
    }

    /**
     * 导出产品报价信息列表
     */
    @RequiresPermissions("product:history:export")
    @Log(title = "产品报价信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ProdQuotationHistory prodQuotationHistory, @RequestParam(value = "onlyAwarded", required = false) Integer onlyAwarded)
    {
        // prodQuotationHistory.setDeptId(getSysUser().getDeptId());
        List<ProdQuotationHistory> list = prodQuotationHistoryService.selectProdQuotationHistoryList(prodQuotationHistory);
        if (onlyAwarded != null && onlyAwarded == 1) {
            list = list.stream().filter(i -> i.getAwardStatus() != null && i.getAwardStatus() == 1).collect(Collectors.toList());
        }
        ExcelUtil<ProdQuotationHistory> util = new ExcelUtil<ProdQuotationHistory>(ProdQuotationHistory.class);
        return util.exportExcel(list, "产品报价信息数据");
    }



    /**
     * 新增产品报价信息
     */
    @GetMapping("/add")
    public String add(ModelMap modelMap)
    {
        List<SysUser> sysUsers = sysUserService.selectUserByRoleId(100l);
        modelMap.put("publishes",sysUsers);
        modelMap.put("phoneNumber",getSysUser().getPhonenumber());
        return prefix + "/add";
    }

    /**
     * 新增保存产品报价信息
     */
    @RequiresPermissions("product:history:add")
    @Log(title = "产品报价信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ProdQuotationHistory prodQuotationHistory)
    {
        if(!quotationBlackUserService.checkQuotationBlackUser(getUserId(), prodQuotationHistory.getDeptId()))
        {
            return error("商家把你加入了黑名单无法报价");
        }
        if (prodQuotationHistory.getProdId() != null && prodQuotationHistory.getBatchId() != null)
        {
            com.ruoyi.product.domain.BatchProduct bp = batchProductService.selectByBatchAndProd(prodQuotationHistory.getBatchId(), prodQuotationHistory.getProdId());
            if (bp == null)
            {
                return error("批次未发布该产品");
            }
            if (prodQuotationHistory.getCount() != null)
            {
                if (prodQuotationHistory.getCount() > bp.getPublishCount())
                {
                    return error("报价数量不能超过发布数量");
                }
            }
            prodQuotationHistory.setBatchProductId(bp.getId());
        }
        prodQuotationHistory.setQuoterUserId(getUserId());
        com.ruoyi.common.core.domain.entity.SysUser sysUser = getSysUser();
        if (sysUser != null && sysUser.getDeptId() != null)
        {
            prodQuotationHistory.setDeptId(sysUser.getDeptId());
        }
        prodQuotationHistory.setCreateTime(Calendar.getInstance().getTime());
        prodQuotationHistory.setName(prodQuotationHistory.getName().split("-")[0]);
        prodQuotationHistory.setCreateBy(getLoginName());
        prodQuotationHistory.setStatus(CommonStatus.OK.getCode());
        return toAjax(prodQuotationHistoryService.insertProdQuotationHistory(prodQuotationHistory));
    }

    /**
     * 修改产品报价信息
     */
    @RequiresPermissions("product:history:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        ProdQuotationHistory prodQuotationHistory = prodQuotationHistoryService.selectProdQuotationHistoryById(id);
        mmap.put("prodQuotationHistory", prodQuotationHistory);
        return prefix + "/edit";
    }

    /**
     * 修改保存产品报价信息
     */
    @RequiresPermissions("product:history:edit")
    @Log(title = "产品报价信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ProdQuotationHistory prodQuotationHistory)
    {
        return toAjax(prodQuotationHistoryService.updateProdQuotationHistory(prodQuotationHistory));
    }

    /**
     * 删除产品报价信息
     */
    @RequiresPermissions("product:history:remove")
    @Log(title = "产品报价信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(prodQuotationHistoryService.deleteProdQuotationHistoryByIds(ids));
    }


    /**
     * 更新报价状态
     */
    @RequiresPermissions("product:history:remove")
    @Log(title = "更新报价状态", businessType = BusinessType.UPDATE)
    @PostMapping( "/updateStatus")
    @ResponseBody
    public AjaxResult updateStatus(Long id)
    {
        logger.info("updateStatus[{}]",id);
        ProdQuotationHistory prodQuotationHistory = prodQuotationHistoryService.selectProdQuotationHistoryById(id);
        prodQuotationHistory.setStatus(2l);
        return toAjax(prodQuotationHistoryService.updateProdQuotationHistory(prodQuotationHistory));
    }

    @PostMapping("/award")
    @ResponseBody
    public AjaxResult award(Long id)
    {
        ProdQuotationHistory q = prodQuotationHistoryService.selectProdQuotationHistoryById(id);
        if (q == null) return error("报价不存在");
        com.ruoyi.product.domain.BatchProduct bp = batchProductService.selectByBatchAndProd(q.getBatchId(), q.getProdId());
        if (bp == null) return error("批次未发布该产品");
        int awardedSum = prodQuotationHistoryService.sumAwardedCount(bp.getId());
        int request = q.getCount() == null ? 0 : q.getCount().intValue();
        int publishCount = bp.getPublishCount() == null ? 0 : bp.getPublishCount();
        if (awardedSum + request > publishCount)
        {
            return error("中标数量总和不能超过发布数量");
        }
        batchProductService.increaseLockedCount(bp.getId(), request);
        q.setAwardStatus(1);
        q.setAwardTime(Calendar.getInstance().getTime());
        prodQuotationHistoryService.updateProdQuotationHistory(q);
        return success();
    }

    @RequiresPermissions("product:history:quoter:view")
    @GetMapping("/quoter")
    public String quoter()
    {
        return prefix + "/quoter";
    }

    /**
     * 查询我的报价信息
     */
    @RequiresPermissions("product:history:quoter/list")
    @PostMapping("/quoter/list")
    @ResponseBody
    public TableDataInfo quoterlist(ProdQuotationHistory prodQuotationHistory)
    {
        startPage();
        prodQuotationHistory.setQuoterUserId(getUserId());
        List<ProdQuotationHistory> list = prodQuotationHistoryService.selectProdQuotationHistoryList(prodQuotationHistory);
        List<ProdQuotationHistoryVO> result = list.stream().map(i->{
            ProdQuotationHistoryVO vo  =new  ProdQuotationHistoryVO();
            BeanUtils.copyProperties(i,vo);
            ProdInfo prodInfo = prodInfoService.selectProdInfoById(i.getProdId());
            if(prodInfo!= null)
            {
                final Long categoryId = prodInfo.getCategoryId();
                final ProdCategory prodCategory = prodCategoryService.selectProdCategoryById(categoryId);
                if(prodCategory!=null)
                {
                    vo.setProdCategory(prodCategory.getName());
                }
            }
            return vo;
        }).collect(Collectors.toList());
        return getDataTable(result);
    }

    /**
     * 导出我的报价信息
     */
    @RequiresPermissions("product:history:quoter:export")
    @Log(title = "产品报价信息", businessType = BusinessType.EXPORT)
    @PostMapping("/quoter/export")
    @ResponseBody
    public AjaxResult quoterExport(ProdQuotationHistory prodQuotationHistory)
    {
        prodQuotationHistory.setQuoterUserId(getUserId());
        List<ProdQuotationHistory> list = prodQuotationHistoryService.selectProdQuotationHistoryList(prodQuotationHistory);

        List<MyProdQuotationExportVO> exportList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(list))
        {
            exportList = list.stream().map(i -> {
                MyProdQuotationExportVO vo = new MyProdQuotationExportVO();
                BeanUtils.copyProperties(i, vo);
                return vo;
            }).collect(Collectors.toList());
        }
        ExcelUtil<MyProdQuotationExportVO> util = new ExcelUtil<MyProdQuotationExportVO>(MyProdQuotationExportVO.class);
        return util.exportExcel(exportList, "我的报价历史数据");
    }

    /**
     * 修改我的产品报价
     */
    @RequiresPermissions("product:history:quoter:edit")
    @GetMapping("/quoter/edit/{id}")
    public String editQuoter(@PathVariable("id") Long id, ModelMap mmap)
    {
        ProdQuotationHistory prodQuotationHistory = prodQuotationHistoryService.selectProdQuotationHistoryById(id);
        mmap.put("prodQuotationHistory", prodQuotationHistory);
        return prefix + "/editQuoter";
    }

//    @RequiresPermissions("product:history:remove")
    @Log(title = "校验报价的唯一性", businessType = BusinessType.OTHER)
    @PostMapping( "/checkProdHistoryUnique")
    @ResponseBody
    public boolean checkProdHistoryUnique(ProdQuotationHistory history)
    {
        if(history.getProdId()== -1)
        {
            return true;
        }
        ProdQuotationHistory prodQuotationHistory = prodQuotationHistoryService.checkProdHistoryUnique(history);
        return prodQuotationHistory == null;
    }

    @Log(title = "校验报价人是否在黑名单", businessType = BusinessType.OTHER)
    @PostMapping( "/checkQuotationBlackUser")
    @ResponseBody
    public boolean checkQuotationBlackUser(Long userId)
    {
        if(userId == null || userId == -1)
        {
            return true;
        }
        return quotationBlackUserService.checkQuotationBlackUser(getUserId(),userId);
    }
}
