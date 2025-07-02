package com.xm01.weibo.mapper;

import java.util.List;
import com.xm01.weibo.domain.Xm01Wbhot;

/**
 * 微博热搜Mapper接口
 * 
 * @author xm01
 * @date 2025-06-15
 */
public interface Xm01WbhotMapper 
{
    /**
     * 查询微博热搜
     * 
     * @param id 微博热搜主键
     * @return 微博热搜
     */
    public Xm01Wbhot selectXm01WbhotById(Long id);

    /**
     * 查询微博热搜列表
     * 
     * @param xm01Wbhot 微博热搜
     * @return 微博热搜集合
     */
    public List<Xm01Wbhot> selectXm01WbhotList(Xm01Wbhot xm01Wbhot);

    /**
     * 新增微博热搜
     * 
     * @param xm01Wbhot 微博热搜
     * @return 结果
     */
    public int insertXm01Wbhot(Xm01Wbhot xm01Wbhot);

    /**
     * 修改微博热搜
     * 
     * @param xm01Wbhot 微博热搜
     * @return 结果
     */
    public int updateXm01Wbhot(Xm01Wbhot xm01Wbhot);

    /**
     * 删除微博热搜
     * 
     * @param id 微博热搜主键
     * @return 结果
     */
    public int deleteXm01WbhotById(Long id);

    /**
     * 批量删除微博热搜
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteXm01WbhotByIds(Long[] ids);

    /**
     * 全部删除
     * @return
     */
    public int deleteXM01wbhotAll();

}
