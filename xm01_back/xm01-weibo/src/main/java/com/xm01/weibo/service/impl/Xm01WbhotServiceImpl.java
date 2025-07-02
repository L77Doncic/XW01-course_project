package com.xm01.weibo.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xm01.weibo.mapper.Xm01WbhotMapper;
import com.xm01.weibo.domain.Xm01Wbhot;
import com.xm01.weibo.service.IXm01WbhotService;

/**
 * 微博热搜Service业务层处理
 * 
 * @author xm01
 * @date 2025-06-15
 */
@Service
public class Xm01WbhotServiceImpl implements IXm01WbhotService 
{
    @Autowired
    private Xm01WbhotMapper xm01WbhotMapper;

    /**
     * 查询微博热搜
     * 
     * @param id 微博热搜主键
     * @return 微博热搜
     */
    @Override
    public Xm01Wbhot selectXm01WbhotById(Long id)
    {
        return xm01WbhotMapper.selectXm01WbhotById(id);
    }

    /**
     * 查询微博热搜列表
     * 
     * @param xm01Wbhot 微博热搜
     * @return 微博热搜
     */
    @Override
    public List<Xm01Wbhot> selectXm01WbhotList(Xm01Wbhot xm01Wbhot)
    {
        return xm01WbhotMapper.selectXm01WbhotList(xm01Wbhot);
    }

    /**
     * 新增微博热搜
     * 
     * @param xm01Wbhot 微博热搜
     * @return 结果
     */
    @Override
    public int insertXm01Wbhot(Xm01Wbhot xm01Wbhot)
    {
        return xm01WbhotMapper.insertXm01Wbhot(xm01Wbhot);
    }

    /**
     * 修改微博热搜
     * 
     * @param xm01Wbhot 微博热搜
     * @return 结果
     */
    @Override
    public int updateXm01Wbhot(Xm01Wbhot xm01Wbhot)
    {
        return xm01WbhotMapper.updateXm01Wbhot(xm01Wbhot);
    }

    /**
     * 批量删除微博热搜
     * 
     * @param ids 需要删除的微博热搜主键
     * @return 结果
     */
    @Override
    public int deleteXm01WbhotByIds(Long[] ids)
    {
        return xm01WbhotMapper.deleteXm01WbhotByIds(ids);
    }

    /**
     * 删除微博热搜信息
     * 
     * @param id 微博热搜主键
     * @return 结果
     */
    @Override
    public int deleteXm01WbhotById(Long id)
    {
        return xm01WbhotMapper.deleteXm01WbhotById(id);
    }
    /**
     * 删除全部信息
     */
    @Override
    public int deleteXM01wbhotAll(){
        return xm01WbhotMapper.deleteXM01wbhotAll();
    }
}
