package com.xm01.carcolor.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xm01.carcolor.mapper.Xm01CarcolorMapper;
import com.xm01.carcolor.domain.Xm01Carcolor;
import com.xm01.carcolor.service.IXm01CarcolorService;

/**
 * carcolorService业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-13
 */
@Service
public class Xm01CarcolorServiceImpl implements IXm01CarcolorService 
{
    @Autowired
    private Xm01CarcolorMapper xm01CarcolorMapper;

    /**
     * 查询carcolor
     * 
     * @param id carcolor主键
     * @return carcolor
     */
    @Override
    public Xm01Carcolor selectXm01CarcolorById(Long id)
    {
        return xm01CarcolorMapper.selectXm01CarcolorById(id);
    }

    /**
     * 查询carcolor列表
     * 
     * @param xm01Carcolor carcolor
     * @return carcolor
     */
    @Override
    public List<Xm01Carcolor> selectXm01CarcolorList(Xm01Carcolor xm01Carcolor)
    {
        return xm01CarcolorMapper.selectXm01CarcolorList(xm01Carcolor);
    }

    /**
     * 新增carcolor
     * 
     * @param xm01Carcolor carcolor
     * @return 结果
     */
    @Override
    public int insertXm01Carcolor(Xm01Carcolor xm01Carcolor)
    {
        return xm01CarcolorMapper.insertXm01Carcolor(xm01Carcolor);
    }

    /**
     * 修改carcolor
     * 
     * @param xm01Carcolor carcolor
     * @return 结果
     */
    @Override
    public int updateXm01Carcolor(Xm01Carcolor xm01Carcolor)
    {
        return xm01CarcolorMapper.updateXm01Carcolor(xm01Carcolor);
    }

    /**
     * 批量删除carcolor
     * 
     * @param ids 需要删除的carcolor主键
     * @return 结果
     */
    @Override
    public int deleteXm01CarcolorByIds(Long[] ids)
    {
        return xm01CarcolorMapper.deleteXm01CarcolorByIds(ids);
    }

    /**
     * 删除carcolor信息
     * 
     * @param id carcolor主键
     * @return 结果
     */
    @Override
    public int deleteXm01CarcolorById(Long id)
    {
        return xm01CarcolorMapper.deleteXm01CarcolorById(id);
    }
}
