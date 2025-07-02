package com.xm01.statistic.mapper;

import java.util.List;
import com.xm01.statistic.domain.Xm01Statistic;

/**
 * 车流量Mapper接口
 * 
 * @author ruoyi
 * @date 2025-01-12
 */
public interface Xm01StatisticMapper 
{
    /**
     * 查询车流量
     * 
     * @param statisticId 车流量主键
     * @return 车流量
     */
    public Xm01Statistic selectXm01StatisticByStatisticId(Long statisticId);

    /**
     * 查询车流量列表
     * 
     * @param xm01Statistic 车流量
     * @return 车流量集合
     */
    public List<Xm01Statistic> selectXm01StatisticList(Xm01Statistic xm01Statistic);

    /**
     * 新增车流量
     * 
     * @param xm01Statistic 车流量
     * @return 结果
     */
    public int insertXm01Statistic(Xm01Statistic xm01Statistic);

    /**
     * 修改车流量
     * 
     * @param xm01Statistic 车流量
     * @return 结果
     */
    public int updateXm01Statistic(Xm01Statistic xm01Statistic);

    /**
     * 删除车流量
     * 
     * @param statisticId 车流量主键
     * @return 结果
     */
    public int deleteXm01StatisticByStatisticId(Long statisticId);

    /**
     * 批量删除车流量
     * 
     * @param statisticIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteXm01StatisticByStatisticIds(Long[] statisticIds);
}
