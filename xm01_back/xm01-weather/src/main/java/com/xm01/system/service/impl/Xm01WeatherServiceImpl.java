package com.xm01.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xm01.system.mapper.Xm01WeatherMapper;
import com.xm01.system.domain.Xm01Weather;
import com.xm01.system.service.IXm01WeatherService;

/**
 * weatherService业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-11
 */
@Service
public class Xm01WeatherServiceImpl implements IXm01WeatherService 
{
    @Autowired
    private Xm01WeatherMapper xm01WeatherMapper;

    /**
     * 查询weather
     * 
     * @param id weather主键
     * @return weather
     */
    @Override
    public Xm01Weather selectXm01WeatherById(Long id)
    {
        return xm01WeatherMapper.selectXm01WeatherById(id);
    }

    /**
     * 查询weather列表
     * 
     * @param xm01Weather weather
     * @return weather
     */
    @Override
    public List<Xm01Weather> selectXm01WeatherList(Xm01Weather xm01Weather)
    {
        return xm01WeatherMapper.selectXm01WeatherList(xm01Weather);
    }

    /**
     * 新增weather
     * 
     * @param xm01Weather weather
     * @return 结果
     */
    @Override
    public int insertXm01Weather(Xm01Weather xm01Weather)
    {
        return xm01WeatherMapper.insertXm01Weather(xm01Weather);
    }

    /**
     * 修改weather
     * 
     * @param xm01Weather weather
     * @return 结果
     */
    @Override
    public int updateXm01Weather(Xm01Weather xm01Weather)
    {
        return xm01WeatherMapper.updateXm01Weather(xm01Weather);
    }

    /**
     * 批量删除weather
     * 
     * @param ids 需要删除的weather主键
     * @return 结果
     */
    @Override
    public int deleteXm01WeatherByIds(Long[] ids)
    {
        return xm01WeatherMapper.deleteXm01WeatherByIds(ids);
    }

    /**
     * 删除weather信息
     * 
     * @param id weather主键
     * @return 结果
     */
    @Override
    public int deleteXm01WeatherById(Long id)
    {
        return xm01WeatherMapper.deleteXm01WeatherById(id);
    }
}
