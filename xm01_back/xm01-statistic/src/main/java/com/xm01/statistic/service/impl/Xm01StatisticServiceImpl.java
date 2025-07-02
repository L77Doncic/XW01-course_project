package com.xm01.statistic.service.impl;

import java.util.List;
import com.xm01.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xm01.statistic.mapper.Xm01StatisticMapper;
import com.xm01.statistic.domain.Xm01Statistic;
import com.xm01.statistic.service.IXm01StatisticService;

/**
 * 车流量Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-12
 */
@Service
public class Xm01StatisticServiceImpl implements IXm01StatisticService 
{
    @Autowired
    private Xm01StatisticMapper xm01StatisticMapper;

    /**
     * 查询车流量
     * 
     * @param statisticId 车流量主键
     * @return 车流量
     */
    @Override
    public Xm01Statistic selectXm01StatisticByStatisticId(Long statisticId)
    {
        return xm01StatisticMapper.selectXm01StatisticByStatisticId(statisticId);
    }

    /**
     * 查询车流量列表
     * 
     * @param xm01Statistic 车流量
     * @return 车流量
     */
    @Override
    public List<Xm01Statistic> selectXm01StatisticList(Xm01Statistic xm01Statistic)
    {
        return xm01StatisticMapper.selectXm01StatisticList(xm01Statistic);
    }

    /**
     * 新增车流量
     * 
     * @param xm01Statistic 车流量
     * @return 结果
     */
    @Override
    public int insertXm01Statistic(Xm01Statistic xm01Statistic)
    {
        xm01Statistic.setCreateTime(DateUtils.getNowDate());
        return xm01StatisticMapper.insertXm01Statistic(xm01Statistic);
    }

    /**
     * 修改车流量
     * 
     * @param xm01Statistic 车流量
     * @return 结果
     */
    @Override
    public int updateXm01Statistic(Xm01Statistic xm01Statistic)
    {
        return xm01StatisticMapper.updateXm01Statistic(xm01Statistic);
    }

    /**
     * 批量删除车流量
     * 
     * @param statisticIds 需要删除的车流量主键
     * @return 结果
     */
    @Override
    public int deleteXm01StatisticByStatisticIds(Long[] statisticIds)
    {
        return xm01StatisticMapper.deleteXm01StatisticByStatisticIds(statisticIds);
    }

    /**
     * 删除车流量信息
     * 
     * @param statisticId 车流量主键
     * @return 结果
     */
    @Override
    public int deleteXm01StatisticByStatisticId(Long statisticId)
    {
        return xm01StatisticMapper.deleteXm01StatisticByStatisticId(statisticId);
    }
}
