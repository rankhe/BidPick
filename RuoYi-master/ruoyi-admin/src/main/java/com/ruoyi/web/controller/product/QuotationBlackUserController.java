package com.ruoyi.web.controller.product;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.CommonStatus;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.product.domain.QuotationBlackUser;
import com.ruoyi.product.service.IQuotationBlackUserService;
import com.ruoyi.system.service.ISysUserService;
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
 * 报价黑名单信息Controller
 * 
 * @author ruoyi
 * @date 2023-09-07
 */
@Controller
@RequestMapping("/product/blackUser")
public class QuotationBlackUserController extends BaseController
{
    private String prefix = "product/blackUser";

    @Autowired
    private IQuotationBlackUserService quotationBlackUserService;

    @Autowired
    private ISysUserService sysUserService;

    @RequiresPermissions("product:blackUser:view")
    @GetMapping()
    public String user()
    {
        return prefix + "/list";
    }

    /**
     * 查询报价黑名单信息列表
     */
    @RequiresPermissions("product:blackUser:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(QuotationBlackUser quotationBlackUser)
    {
        startPage();
        quotationBlackUser.setDeptId(getSysUser().getDeptId());
        quotationBlackUser.setStatus(CommonStatus.OK.getCode().intValue());
        List<QuotationBlackUser> list = quotationBlackUserService.selectQuotationBlackUserList(quotationBlackUser);
        return getDataTable(list);
    }

    /**
     * 导出报价黑名单信息列表
     */
    @RequiresPermissions("product:blackUser:export")
    @Log(title = "报价黑名单信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(QuotationBlackUser quotationBlackUser)
    {
        List<QuotationBlackUser> list = quotationBlackUserService.selectQuotationBlackUserList(quotationBlackUser);
        ExcelUtil<QuotationBlackUser> util = new ExcelUtil<QuotationBlackUser>(QuotationBlackUser.class);
        return util.exportExcel(list, "报价黑名单信息数据");
    }

    /**
     * 新增报价黑名单信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存报价黑名单信息
     */
    @RequiresPermissions("product:blackUser:add")
    @Log(title = "添加报价黑名单", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(QuotationBlackUser quotationBlackUser)
    {
        if(quotationBlackUser == null || (quotationBlackUser.getUserId()==null && StringUtils.isEmpty(quotationBlackUser.getMobile())))
        return error("缺少必要参数");
        SysUser user = null;

        if(StringUtils.isNotEmpty(quotationBlackUser.getMobile()))
        {
            user = sysUserService.selectUserByPhoneNumber(quotationBlackUser.getMobile());
        }
        if(user == null && quotationBlackUser.getUserId() != null)
        {
            user = sysUserService.selectUserById(quotationBlackUser.getUserId());
        }
        if(user == null)
        {
            return error("系统找不到对用的用户");
        }
        if(!quotationBlackUserService.checkQuotationBlackUser(user.getUserId(), getSysUser().getDeptId()))
        {
            return error("该用户已经在黑名单列表");
        }
        quotationBlackUser.setUserId(user.getUserId());
        quotationBlackUser.setDeptId(getSysUser().getDeptId());
        com.ruoyi.common.core.domain.entity.SysUser sysUser = getSysUser();
        if (sysUser != null && sysUser.getDeptId() != null)
        {
            quotationBlackUser.setDeptId(sysUser.getDeptId());
        }
        quotationBlackUser.setMobile(user.getPhonenumber());
        quotationBlackUser.setStatus(CommonStatus.OK.getCode().intValue());
        return toAjax(quotationBlackUserService.insertQuotationBlackUser(quotationBlackUser));
    }

    /**
     * 修改报价黑名单信息
     */
    @RequiresPermissions("product:blackUser:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        QuotationBlackUser quotationBlackUser = quotationBlackUserService.selectQuotationBlackUserById(id);
        mmap.put("quotationBlackUser", quotationBlackUser);
        return prefix + "/edit";
    }

    /**
     * 修改保存报价黑名单信息
     */
    @RequiresPermissions("product:blackUser:edit")
    @Log(title = "报价黑名单信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(QuotationBlackUser quotationBlackUser)
    {
        return toAjax(quotationBlackUserService.updateQuotationBlackUser(quotationBlackUser));
    }

    /**
     * 删除报价黑名单信息
     */
    @RequiresPermissions("product:blackUser:remove")
    @Log(title = "报价黑名单信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(quotationBlackUserService.deleteQuotationBlackUserByIds(ids));
    }
}
