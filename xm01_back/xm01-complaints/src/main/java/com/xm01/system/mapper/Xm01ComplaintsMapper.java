package com.xm01.system.mapper;

import java.util.List;
import com.xm01.system.domain.Xm01Complaints;

/**
 * 投诉管理Mapper接口
 * 
 * @author ruoyi
 * @date 2025-01-12
 */
public interface Xm01ComplaintsMapper 
{
    /**
     * 查询投诉管理
     * 
     * @param id 投诉管理主键
     * @return 投诉管理
     */
    public Xm01Complaints selectXm01ComplaintsById(Long id);

    /**
     * 查询投诉管理列表
     * 
     * @param xm01Complaints 投诉管理
     * @return 投诉管理集合
     */
    public List<Xm01Complaints> selectXm01ComplaintsList(Xm01Complaints xm01Complaints);

    /**
     * 新增投诉管理
     * 
     * @param xm01Complaints 投诉管理
     * @return 结果
     */
    public int insertXm01Complaints(Xm01Complaints xm01Complaints);

    /**
     * 修改投诉管理
     * 
     * @param xm01Complaints 投诉管理
     * @return 结果
     */
    public int updateXm01Complaints(Xm01Complaints xm01Complaints);

    /**
     * 删除投诉管理
     * 
     * @param id 投诉管理主键
     * @return 结果
     */
    public int deleteXm01ComplaintsById(Long id);

    /**
     * 批量删除投诉管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteXm01ComplaintsByIds(Long[] ids);
}
