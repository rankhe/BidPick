package com.ruoyi.system.domain;


import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

// 融资历史
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FinancingHistory extends BaseEntity
{

    private Long id;
    // 公司Id
    private Long cid;
    // 融资时间
    private  Date  date;
    // 金额
    private String money;
    // 轮次
    private String round;
    //新闻Url
    private String  newsUrl;
    //公司名
    private String companyName;
    // 披露时间
    private Date pubTime;
    // 新闻标题
    private String newsTitle;
    // 投资企业
    private String investorName;
    // 投资比例
    private String share;
    // 估值
    private String value;

    private Date updateTime;
}
