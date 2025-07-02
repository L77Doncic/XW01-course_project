package com.xm01.cardata.mapper;

import java.util.List;
import com.xm01.cardata.domain.Xm01Cardata;

/**
 * 违法信息Mapper接口
 * 
 * @author ruoyi
 * @date 2025-01-13
 */
public interface Xm01CardataMapper 
{
    /**
     * 查询违法信息
     * 
     * @param id 违法信息主键
     * @return 违法信息
     */
    public Xm01Cardata selectXm01CardataById(Long id);

    /**
     * 查询违法信息列表
     * 
     * @param xm01Cardata 违法信息
     * @return 违法信息集合
     */
    public List<Xm01Cardata> selectXm01CardataList(Xm01Cardata xm01Cardata);

    /**
     * 新增违法信息
     * 
     * @param xm01Cardata 违法信息
     * @return 结果
     */
    public int insertXm01Cardata(Xm01Cardata xm01Cardata);

    /**
     * 修改违法信息
     * 
     * @param xm01Cardata 违法信息
     * @return 结果
     */
    public int updateXm01Cardata(Xm01Cardata xm01Cardata);

    /**
     * 删除违法信息
     * 
     * @param id 违法信息主键
     * @return 结果
     */
    public int deleteXm01CardataById(Long id);

    /**
     * 批量删除违法信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteXm01CardataByIds(Long[] ids);
}
