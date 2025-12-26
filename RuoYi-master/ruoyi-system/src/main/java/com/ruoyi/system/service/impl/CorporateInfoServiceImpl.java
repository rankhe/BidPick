package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.FinancingHistory;
import com.ruoyi.system.mapper.CorporateInfoMapper;
import com.ruoyi.system.service.ICorporateInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorporateInfoServiceImpl implements ICorporateInfoService
{

    @Autowired CorporateInfoMapper corporateInfoMapper;

    @Override public List<FinancingHistory> selectFinancingHistoryList(FinancingHistory financingHistory)
    {
        return corporateInfoMapper.selectFinancingHistoryList(financingHistory);
    }
}
