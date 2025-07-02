package com.xm01.carcolor.service;

import java.util.List;
import com.xm01.carcolor.domain.Xm01Carcolor;

/**
 * carcolorService接口
 * 
 * @author ruoyi
 * @date 2025-01-13
 */
public interface IXm01CarcolorService 
{
    /**
     * 查询carcolor
     * 
     * @param id carcolor主键
     * @return carcolor
     */
    public Xm01Carcolor selectXm01CarcolorById(Long id);

    /**
     * 查询carcolor列表
     * 
     * @param xm01Carcolor carcolor
     * @return carcolor集合
     */
    public List<Xm01Carcolor> selectXm01CarcolorList(Xm01Carcolor xm01Carcolor);

    /**
     * 新增carcolor
     * 
     * @param xm01Carcolor carcolor
     * @return 结果
     */
    public int insertXm01Carcolor(Xm01Carcolor xm01Carcolor);

    /**
     * 修改carcolor
     * 
     * @param xm01Carcolor carcolor
     * @return 结果
     */
    public int updateXm01Carcolor(Xm01Carcolor xm01Carcolor);

    /**
     * 批量删除carcolor
     * 
     * @param ids 需要删除的carcolor主键集合
     * @return 结果
     */
    public int deleteXm01CarcolorByIds(Long[] ids);

    /**
     * 删除carcolor信息
     * 
     * @param id carcolor主键
     * @return 结果
     */
    public int deleteXm01CarcolorById(Long id);
}
