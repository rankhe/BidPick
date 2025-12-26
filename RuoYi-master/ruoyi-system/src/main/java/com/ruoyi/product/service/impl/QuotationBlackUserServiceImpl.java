package com.ruoyi.product.service.impl;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.enums.CommonStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.product.domain.QuotationBlackUser;
import com.ruoyi.product.service.IQuotationBlackUserService;
import com.ruoyi.product.mapper.QuotationBlackUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 报价黑名单信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-09-07
 */
@Service
public class QuotationBlackUserServiceImpl implements IQuotationBlackUserService
{
    @Autowired
    private QuotationBlackUserMapper quotationBlackUserMapper;

    /**
     * 查询报价黑名单信息
     * 
     * @param id 报价黑名单信息主键
     * @return 报价黑名单信息
     */
    @Override
    public QuotationBlackUser selectQuotationBlackUserById(Long id)
    {
        return quotationBlackUserMapper.selectQuotationBlackUserById(id);
    }

    /**
     * 查询报价黑名单信息列表
     * 
     * @param quotationBlackUser 报价黑名单信息
     * @return 报价黑名单信息
     */
    @Override
    public List<QuotationBlackUser> selectQuotationBlackUserList(QuotationBlackUser quotationBlackUser)
    {
        return quotationBlackUserMapper.selectQuotationBlackUserList(quotationBlackUser);
    }

    /**
     * 新增报价黑名单信息
     * 
     * @param quotationBlackUser 报价黑名单信息
     * @return 结果
     */
    @Override
    public int insertQuotationBlackUser(QuotationBlackUser quotationBlackUser)
    {
        quotationBlackUser.setCreateTime(DateUtils.getNowDate());
        return quotationBlackUserMapper.insertQuotationBlackUser(quotationBlackUser);
    }

    /**
     * 修改报价黑名单信息
     * 
     * @param quotationBlackUser 报价黑名单信息
     * @return 结果
     */
    @Override
    public int updateQuotationBlackUser(QuotationBlackUser quotationBlackUser)
    {
        quotationBlackUser.setUpdateTime(DateUtils.getNowDate());
        return quotationBlackUserMapper.updateQuotationBlackUser(quotationBlackUser);
    }

    /**
     * 批量删除报价黑名单信息
     * 
     * @param ids 需要删除的报价黑名单信息主键
     * @return 结果
     */
    @Override
    public int deleteQuotationBlackUserByIds(String ids)
    {
        AtomicInteger count = new AtomicInteger(0);
        Arrays.stream(Convert.toLongArray(ids)).forEach(id -> {
                    QuotationBlackUser quotationBlackUser = quotationBlackUserMapper.selectQuotationBlackUserById(id);
                    quotationBlackUser.setStatus(CommonStatus.DEFAULT.getCode().intValue());
                    this.updateQuotationBlackUser(quotationBlackUser);
                    count.incrementAndGet();
                }
        );
        return count.get();
    }

    /**
     * 删除报价黑名单信息信息
     * 
     * @param id 报价黑名单信息主键
     * @return 结果
     */
    @Override
    public int deleteQuotationBlackUserById(Long id)
    {
         QuotationBlackUser quotationBlackUser = quotationBlackUserMapper.selectQuotationBlackUserById(id);
         quotationBlackUser.setStatus(CommonStatus.DEFAULT.getCode().intValue());
         this.updateQuotationBlackUser(quotationBlackUser);        return quotationBlackUserMapper.deleteQuotationBlackUserById(id);
    }

    @Override
    public Boolean checkQuotationBlackUser(Long userId, Long deptId)
    {
        QuotationBlackUser quotationBlackUser = new QuotationBlackUser();
        quotationBlackUser.setDeptId(deptId);
        quotationBlackUser.setUserId(userId);
        quotationBlackUser.setStatus(CommonStatus.OK.getCode().intValue());
        List<QuotationBlackUser> quotationBlackUsers = quotationBlackUserMapper.selectQuotationBlackUserList(
                quotationBlackUser);
        return CollectionUtils.isEmpty(quotationBlackUsers);
    }
}
