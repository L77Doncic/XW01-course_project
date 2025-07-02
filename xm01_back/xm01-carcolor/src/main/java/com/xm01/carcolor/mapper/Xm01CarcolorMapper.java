package com.xm01.carcolor.mapper;

import java.util.List;
import com.xm01.carcolor.domain.Xm01Carcolor;

/**
 * carcolorMapper接口
 * 
 * @author ruoyi
 * @date 2025-01-13
 */
public interface Xm01CarcolorMapper 
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
     * 删除carcolor
     * 
     * @param id carcolor主键
     * @return 结果
     */
    public int deleteXm01CarcolorById(Long id);

    /**
     * 批量删除carcolor
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteXm01CarcolorByIds(Long[] ids);
}
