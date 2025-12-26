package com.ruoyi.product.service;

import com.ruoyi.product.domain.QuotationBlackUser;

import java.util.List;

/**
 * 报价黑名单信息Service接口
 * 
 * @author ruoyi
 * @date 2023-09-07
 */
public interface IQuotationBlackUserService
{
    /**
     * 查询报价黑名单信息
     * 
     * @param id 报价黑名单信息主键
     * @return 报价黑名单信息
     */
    public QuotationBlackUser selectQuotationBlackUserById(Long id);

    /**
     * 查询报价黑名单信息列表
     * 
     * @param quotationBlackUser 报价黑名单信息
     * @return 报价黑名单信息集合
     */
    public List<QuotationBlackUser> selectQuotationBlackUserList(QuotationBlackUser quotationBlackUser);

    /**
     * 新增报价黑名单信息
     * 
     * @param quotationBlackUser 报价黑名单信息
     * @return 结果
     */
    public int insertQuotationBlackUser(QuotationBlackUser quotationBlackUser);

    /**
     * 修改报价黑名单信息
     * 
     * @param quotationBlackUser 报价黑名单信息
     * @return 结果
     */
    public int updateQuotationBlackUser(QuotationBlackUser quotationBlackUser);

    /**
     * 批量删除报价黑名单信息
     * 
     * @param ids 需要删除的报价黑名单信息主键集合
     * @return 结果
     */
    public int deleteQuotationBlackUserByIds(String ids);

    /**
     * 删除报价黑名单信息信息
     * 
     * @param id 报价黑名单信息主键
     * @return 结果
     */
    public int deleteQuotationBlackUserById(Long id);

    /**
     * 检查当前用户是否不在某个供应商的黑名单
     * @param userId
     * @param deptId
     * @return
     */
    Boolean checkQuotationBlackUser(Long userId, Long deptId);
}
