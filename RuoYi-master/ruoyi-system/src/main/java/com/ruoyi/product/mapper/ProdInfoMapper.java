package com.ruoyi.product.mapper;

import com.ruoyi.product.domain.ProdInfo;

import java.util.List;

/**
 * 产品信息Mapper接口
 * 
 * @author ruoyi
 * @date 2023-09-01
 */
public interface ProdInfoMapper 
{
    /**
     * 查询产品信息
     * 
     * @param id 产品信息主键
     * @return 产品信息
     */
    public ProdInfo selectProdInfoById(Long id);

    /**
     * 查询产品信息列表
     * 
     * @param prodInfo 产品信息
     * @return 产品信息集合
     */
    public List<ProdInfo> selectProdInfoList(ProdInfo prodInfo);

    /**
     * 新增产品信息
     * 
     * @param prodInfo 产品信息
     * @return 结果
     */
    public int insertProdInfo(ProdInfo prodInfo);

    /**
     * 修改产品信息
     * 
     * @param prodInfo 产品信息
     * @return 结果
     */
    public int updateProdInfo(ProdInfo prodInfo);

    /**
     * 删除产品信息
     * 
     * @param id 产品信息主键
     * @return 结果
     */
    public int deleteProdInfoById(Long id);

    /**
     * 批量删除产品信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProdInfoByIds(String[] ids);

}
