package com.xm01.cardata.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xm01.cardata.mapper.Xm01CardataMapper;
import com.xm01.cardata.domain.Xm01Cardata;
import com.xm01.cardata.service.IXm01CardataService;

/**
 * 违法信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-13
 */
@Service
public class Xm01CardataServiceImpl implements IXm01CardataService 
{
    @Autowired
    private Xm01CardataMapper xm01CardataMapper;

    /**
     * 查询违法信息
     * 
     * @param id 违法信息主键
     * @return 违法信息
     */
    @Override
    public Xm01Cardata selectXm01CardataById(Long id)
    {
        return xm01CardataMapper.selectXm01CardataById(id);
    }

    /**
     * 查询违法信息列表
     * 
     * @param xm01Cardata 违法信息
     * @return 违法信息
     */
    @Override
    public List<Xm01Cardata> selectXm01CardataList(Xm01Cardata xm01Cardata)
    {
        return xm01CardataMapper.selectXm01CardataList(xm01Cardata);
    }

    /**
     * 新增违法信息
     * 
     * @param xm01Cardata 违法信息
     * @return 结果
     */
    @Override
    public int insertXm01Cardata(Xm01Cardata xm01Cardata)
    {
        return xm01CardataMapper.insertXm01Cardata(xm01Cardata);
    }

    /**
     * 修改违法信息
     * 
     * @param xm01Cardata 违法信息
     * @return 结果
     */
    @Override
    public int updateXm01Cardata(Xm01Cardata xm01Cardata)
    {
        return xm01CardataMapper.updateXm01Cardata(xm01Cardata);
    }

    /**
     * 批量删除违法信息
     * 
     * @param ids 需要删除的违法信息主键
     * @return 结果
     */
    @Override
    public int deleteXm01CardataByIds(Long[] ids)
    {
        return xm01CardataMapper.deleteXm01CardataByIds(ids);
    }

    /**
     * 删除违法信息信息
     * 
     * @param id 违法信息主键
     * @return 结果
     */
    @Override
    public int deleteXm01CardataById(Long id)
    {
        return xm01CardataMapper.deleteXm01CardataById(id);
    }
}
