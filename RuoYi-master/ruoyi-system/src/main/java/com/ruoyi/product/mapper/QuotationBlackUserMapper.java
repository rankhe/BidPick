package com.ruoyi.product.mapper;

import com.ruoyi.product.domain.QuotationBlackUser;

import java.util.List;

/**
 * 报价黑名单信息Mapper接口
 * 
 * @author ruoyi
 * @date 2023-09-07
 */
public interface QuotationBlackUserMapper
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
     * 删除报价黑名单信息
     * 
     * @param id 报价黑名单信息主键
     * @return 结果
     */
    public int deleteQuotationBlackUserById(Long id);

    /**
     * 批量删除报价黑名单信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQuotationBlackUserByIds(String[] ids);
}
