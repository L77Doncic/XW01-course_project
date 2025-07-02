package com.xm01.system.mapper;

import java.util.List;
import com.xm01.system.domain.Xm01Weather;

/**
 * weatherMapper接口
 * 
 * @author ruoyi
 * @date 2025-01-11
 */
public interface Xm01WeatherMapper 
{
    /**
     * 查询weather
     * 
     * @param id weather主键
     * @return weather
     */
    public Xm01Weather selectXm01WeatherById(Long id);

    /**
     * 查询weather列表
     * 
     * @param xm01Weather weather
     * @return weather集合
     */
    public List<Xm01Weather> selectXm01WeatherList(Xm01Weather xm01Weather);

    /**
     * 新增weather
     * 
     * @param xm01Weather weather
     * @return 结果
     */
    public int insertXm01Weather(Xm01Weather xm01Weather);

    /**
     * 修改weather
     * 
     * @param xm01Weather weather
     * @return 结果
     */
    public int updateXm01Weather(Xm01Weather xm01Weather);

    /**
     * 删除weather
     * 
     * @param id weather主键
     * @return 结果
     */
    public int deleteXm01WeatherById(Long id);

    /**
     * 批量删除weather
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteXm01WeatherByIds(Long[] ids);
}
