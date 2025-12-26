package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.FinancingHistory;

import java.util.List;

public interface CorporateInfoMapper
{
    /**
     * 查询企业融资历史列表
     *
     * @param financingHistory 融资历史信息
     * @return 融资历史集合
     */
    public List<FinancingHistory> selectFinancingHistoryList(FinancingHistory financingHistory);
}
