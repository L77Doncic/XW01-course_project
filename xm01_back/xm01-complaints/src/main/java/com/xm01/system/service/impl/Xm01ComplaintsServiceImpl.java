package com.xm01.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xm01.system.mapper.Xm01ComplaintsMapper;
import com.xm01.system.domain.Xm01Complaints;
import com.xm01.system.service.IXm01ComplaintsService;

/**
 * 投诉管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-12
 */
@Service
public class Xm01ComplaintsServiceImpl implements IXm01ComplaintsService 
{
    @Autowired
    private Xm01ComplaintsMapper xm01ComplaintsMapper;

    /**
     * 查询投诉管理
     * 
     * @param id 投诉管理主键
     * @return 投诉管理
     */
    @Override
    public Xm01Complaints selectXm01ComplaintsById(Long id)
    {
        return xm01ComplaintsMapper.selectXm01ComplaintsById(id);
    }

    /**
     * 查询投诉管理列表
     * 
     * @param xm01Complaints 投诉管理
     * @return 投诉管理
     */
    @Override
    public List<Xm01Complaints> selectXm01ComplaintsList(Xm01Complaints xm01Complaints)
    {
        return xm01ComplaintsMapper.selectXm01ComplaintsList(xm01Complaints);
    }

    /**
     * 新增投诉管理
     * 
     * @param xm01Complaints 投诉管理
     * @return 结果
     */
    @Override
    public int insertXm01Complaints(Xm01Complaints xm01Complaints)
    {
        return xm01ComplaintsMapper.insertXm01Complaints(xm01Complaints);
    }

    /**
     * 修改投诉管理
     * 
     * @param xm01Complaints 投诉管理
     * @return 结果
     */
    @Override
    public int updateXm01Complaints(Xm01Complaints xm01Complaints)
    {
        return xm01ComplaintsMapper.updateXm01Complaints(xm01Complaints);
    }

    /**
     * 批量删除投诉管理
     * 
     * @param ids 需要删除的投诉管理主键
     * @return 结果
     */
    @Override
    public int deleteXm01ComplaintsByIds(Long[] ids)
    {
        return xm01ComplaintsMapper.deleteXm01ComplaintsByIds(ids);
    }

    /**
     * 删除投诉管理信息
     * 
     * @param id 投诉管理主键
     * @return 结果
     */
    @Override
    public int deleteXm01ComplaintsById(Long id)
    {
        return xm01ComplaintsMapper.deleteXm01ComplaintsById(id);
    }
}
